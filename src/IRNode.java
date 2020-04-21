import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


public class IRNode {
	
	// max of reg available
	//TODO:: if reg are not enough use local vars
	static final Integer maxReg = 301;
	//stack of reg allocated
	static Stack<Integer> reg_allocated = new Stack<Integer>();
	//max satck needed for function
	static Integer max_op_stack = 0;
	
	static {
		for(int i = maxReg; i > 0; i--) {
			reg_allocated.push(i);
		}
	}
	
	IRNode parent;
	IRNode [] children = new IRNode[0];
	
	//min number of registry to reach that node, available on operations
	int num_reg = 0;
	
	//allocated registry
	Integer reg = null;
	
	//local var index
	Integer local_var = null;
	
	//stack of locals, available on method
	Integer locals_stack = null;
	
	//stack of operands, available on method
	Integer op_stack = null;
	
	//can be useful for the code generation
	String type = null;
	
    String className;

	//intruction
	private String inst = "";
	
	
	public IRNode(IRNode p) {
		parent = p;
	}
	
	public IRNode getParent() {
		return this.parent;
	}
	
	public IRNode[] getChildren() {
		return this.children;
	}
	
	public void addChild(IRNode n) {
		 if (children == null) {
			 children = new IRNode[1];
		 }else {
			 IRNode c[] = new IRNode[this.children.length+1];
		     System.arraycopy(children, 0, c, 0, children.length);
		     children = c;
		 }
		 int length = children.length;
		 children[length-1] = n;
	}
	
	  public void addChild(IRNode n, int i) {
	    if (children == null) {
	      children = new IRNode[i + 1];
	    } else if (i >= children.length) {
	      IRNode c[] = new IRNode[i + 1];
	      System.arraycopy(children, 0, c, 0, children.length);
	      children = c;
	    }
	    children[i] = n;
	  }
	  
	  public void removeLast() {
		  if(this.children == null) return;
		  int n = this.children.length-1;
		  if(n < 0) return;
		  IRNode c[] = new IRNode[n];
		  System.arraycopy(children, 0, c, 0, n);
	      children = c;
	  }
	
	  public void setInst(String inst) {
		  this.inst = inst;
	  }
	  
	public String getInst() {
		return this.inst;
	}
	
	public String getIRType() {
		return this.type;
	}
	
	public void setIRType(String type) {
		this.type = type;
	}
	
	public void build(SimpleNode sn) {
		//System.out.println(sn.toString());
		
		
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			if(node.toString().equals("IMPORT")) {
				continue;
			}
				
			
			IRNode child = new IRNode(this);
			this.addChild(child);
			
			if(node.toString().equals("CLASS_DECL")) {
				child.setInst("class");
				child.buildClass(node);
			}
			else
				child.build(node); 
		}
	}
	
	public void buildClass(SimpleNode sn) {
		
		IRNode obj = new IRNode(this);
		obj.setInst(sn.name);
		this.addChild(obj);
		
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);

		int i = 0;
		if(lhn.toString().equals("EXTENDS")) {
			IRNode child = new IRNode(this);
			child.setInst(lhn.name);
			this.addChild(child);
			i = 1;
		}else {
			IRNode child = new IRNode(this);
			child.setInst("Object");
			this.addChild(child);
		}
		
		IRNode fields = new IRNode(this);
		fields.setInst("fields");
		this.addChild(fields);
		
		for(; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			IRNode child = new IRNode(this);
			
			if(node.toString().equals("METHOD")) {
				this.addChild(child);
				child.setInst("method");
				child.buildMethod(node);
			}else if(node.toString().equals("VAR_DEC")) {
				fields.addChild(child);
				child.buildVarDec(node);
			}
			else
				child.build( node ); 
			
		}
	}
	
	
	public void buildMethod(SimpleNode sn) {
		
		IRNode access_spec = new IRNode(this);
		access_spec.setInst("access_spec");
		this.addChild(access_spec);
		
		IRNode method_spec = new IRNode(this);
		method_spec.setInst("method_spec");
		this.addChild(method_spec);
		
		IRNode arguments_specs = new IRNode(this);
		arguments_specs.setInst("arguments_specs");
		this.addChild(arguments_specs);
		
		IRNode locals = new IRNode(this);
		locals.setInst("locals");
		this.addChild(locals);
		
		IRNode statements = new IRNode(this);
		statements.setInst("statements");
		this.addChild(statements);
		
		IRNode type = new IRNode(this);
		type.setInst(sn.type);
		method_spec.addChild(type);
		
		IRNode pub = new IRNode(this);
		pub.setInst("public");
		access_spec.addChild(pub);
		
		if(sn.is_static) {
			IRNode stat = new IRNode(this);
			stat.setInst("static");
			access_spec.addChild(stat);
		}
		
		IRNode name = new IRNode(this);
		name.setInst(sn.name);
		method_spec.addChild(name);
		
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);

		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			IRNode child = new IRNode(this);
			
			if(node.toString().equals("ARGUMENT")) {	
				arguments_specs.addChild(child); 
				child.local_var =arguments_specs.children.length;
				sn.simbolTable.getSimbol(node.name).local_var = child.local_var;
				child.buildArgument(node);
			}else if(node.toString().equals("METHOD_BODY")) {
				//child.buildMethodBody( node ); 
				for(int a = 0; a < node.jjtGetNumChildren(); a++) {
					SimpleNode node2 = (SimpleNode)node.jjtGetChild(a);
					
					if(node2.toString().equals("VAR_DEC")) {
						IRNode child2 = new IRNode(locals);
						locals.addChild(child2);
						child2.local_var =arguments_specs.children.length + locals.children.length;
						child2.buildVarDec(node2);
					}else {
						IRNode child2 = new IRNode(statements);
						statements.addChild(child2);
						child2.getBuild(node2);
					}
				}
			}
			else {
				statements.addChild(child, i);
				child.build( node ); 
			}
		}
		//System.out.println(arguments_specs.children.length + "  " + locals.children.length);
		this.locals_stack = arguments_specs.children.length + locals.children.length;
	}
	
	public void buildArgument(SimpleNode sn) {
		this.setInst(sn.type);
	}
	
	public void buildVarDec(SimpleNode sn) {
		this.setInst(sn.name);
		sn.simbolTable.getSimbol(sn.name).local_var = this.local_var;
		this.setIRType(sn.type);
	}
	
	public void buildWhile(SimpleNode sn) {
		this.setInst("while");
		
		if(sn.jjtGetNumChildren() <2)
			return;
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)(sn.jjtGetChild(0)).jjtGetChild(0); //EXPRESSION
		IRNode var = new IRNode(this);
		this.addChild(var);
		var.getBuild(lhn);
	
		SimpleNode rhn = (SimpleNode)sn.jjtGetChild(1);
		SimpleNode node = rhn;
		
		if (rhn.toString().equals("BODY")) {
			SimpleNode rhnChild = (SimpleNode)rhn.jjtGetChild(0);
			if (rhnChild.toString().equals("CODE_BLOCK")) {
				node = rhnChild;
			}
		}
		else
			return;
		
		for(int a = 0; a < node.jjtGetNumChildren(); a++) {
			SimpleNode node2 = (SimpleNode)node.jjtGetChild(a);
			IRNode child2 = new IRNode(this);
			this.addChild(child2);
			child2.getBuild(node2);
		}
	}
	
	public void buildAssign(SimpleNode sn) {
		if(sn.jjtGetNumChildren() == 0)
			return;
		
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode var = new IRNode(this);
		Simbol s = sn.simbolTable.getSimbol(((SimpleNode)lhn.jjtGetChild(0)).name);
		var.local_var = s.local_var;
		if(lhn.jjtGetNumChildren() == 1) {
			String scope = sn.simbolTable.getScope(((SimpleNode)lhn.jjtGetChild(0)).name);
			if(scope.equals("global"))
				this.inst = "stg";
			else
				this.inst = "st";
			this.type = s.getType().name;
			var.setInst(((SimpleNode)lhn.jjtGetChild(0)).name);
			this.addChild(var);
		}else if(lhn.jjtGetNumChildren() == 2) {
			this.inst = "sta";
			this.type = s.getType().content.name;
			var.setInst(((SimpleNode)lhn.jjtGetChild(0)).name);
			this.addChild(var);
			SimpleNode index_exp = (SimpleNode)lhn.jjtGetChild(1);
			IRNode exp = new IRNode(this);
			this.addChild(exp);
			exp.getBuild(index_exp);

		}else
			return;
		
		SimpleNode rhn = (SimpleNode)sn.jjtGetChild(1);
		IRNode exp = new IRNode(this);
		this.addChild(exp);
		exp.getBuild(rhn);

	}
	
	public void buildExpression(SimpleNode sn) {
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		IRNode parent = this.parent;
		parent.removeLast();
		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			IRNode child = new IRNode(parent);
			parent.addChild(child);
			
			child.getBuild(node);
		}
	}
	
	public void buildOperator(SimpleNode sn) {
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		this.setInst(sn.name);
		
		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);	
			IRNode child = new IRNode(this);
			this.addChild(child);
			child.getBuild(node);
		}
	}
	
	public void buildIntegerLiteral(SimpleNode sn) {
		this.setInst("ldc");
		IRNode child = new IRNode(this);
		child.setInst(String.valueOf(sn.val));
		this.addChild(child);
	}
	public void buildBool(SimpleNode sn) {
		this.setInst("ldc");
		IRNode child = new IRNode(this);
		child.setInst(String.valueOf(sn.val));
		this.addChild(child);
	}
	
	public void buildIdentifier(SimpleNode sn) {
		String load = sn.simbolTable.getScope(sn.name);

		switch(load) {
		case "local":
			this.setInst("ldl");
			break;
		case "param":
			this.setInst("ldp");
			break;
		case "global":
			this.setInst("ldg");
			break;
		}
		IRNode child = new IRNode(this);
		child.setInst(sn.name);
		Simbol s = sn.simbolTable.getSimbol(sn.name);
		child.local_var = s.local_var;
		this.type = s.getType().name;
		this.addChild(child);
	}
	
	public void buildIf(SimpleNode sn) {
		
		this.setInst("if");

		if(sn.jjtGetNumChildren() != 3)
			return;
		
		SimpleNode lhn = (SimpleNode)(sn.jjtGetChild(0)).jjtGetChild(0);
		IRNode child = new IRNode(this);
		this.addChild(child);
		child.getBuild(lhn);
		
		IRNode if_statements = new IRNode(this);
		if_statements.setInst("if_statements");
		this.addChild(if_statements);
		
		IRNode else_statements = new IRNode(this);
		else_statements.setInst("else_statements");
		this.addChild(else_statements);
				
		
		SimpleNode ifbody = (SimpleNode)sn.jjtGetChild(1);
		SimpleNode node = ifbody;
		
		if (ifbody.toString().equals("IF_BODY")) {
			SimpleNode ifbodyChild = (SimpleNode)ifbody.jjtGetChild(0);
			if (ifbodyChild.toString().equals("CODE_BLOCK")) {
				node = ifbodyChild;
			}
		}
		else {
			return;
		}
		
		for(int a = 0; a < node.jjtGetNumChildren(); a++) {
			SimpleNode node2 = (SimpleNode)node.jjtGetChild(a);
			IRNode child2 = new IRNode(if_statements);
			if_statements.addChild(child2);
			child2.getBuild(node2);
		}
		
		SimpleNode elsebody = (SimpleNode)sn.jjtGetChild(2);
		SimpleNode node3 = elsebody;
		
		if (elsebody.toString().equals("ELSE_BODY")) {
			SimpleNode elsebodyChild = (SimpleNode)elsebody.jjtGetChild(0);
			if (elsebodyChild.toString().equals("CODE_BLOCK")) {
				node3 = elsebodyChild;
			}
		}
		else {
			return;
		}
		
		for(int t = 0; t < node3.jjtGetNumChildren(); t++) {
			SimpleNode node4 = (SimpleNode)node3.jjtGetChild(t);
			IRNode child3 = new IRNode(else_statements);
			else_statements.addChild(child3);
			child3.getBuild(node4);
		}
	}
	
	public void buildReturn(SimpleNode sn) {
		this.setInst("return");
		
		if(sn.jjtGetNumChildren() == 0)
			return;
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode child = new IRNode(this);
		this.addChild(child);
		child.getBuild(lhn);
		
	}
	public void buildNewIntArr(SimpleNode sn) {
		this.setInst("new_int_arr");
		
		if(sn.jjtGetNumChildren() == 0)
			return;	
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode child = new IRNode(this);
		this.addChild(child);
		child.getBuild(lhn);
		
	}
	
	public void buildNewIdentifier(SimpleNode sn) {
		this.setInst("new_object");
		if(sn.jjtGetNumChildren() == 0)
			return;	
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode type = new IRNode(this);
		type.setInst(lhn.name);
		this.addChild(type);
		IRNode params = new IRNode(this);
		params.setInst("params");
		this.addChild(params);
		for(int i = 1; i < sn.jjtGetNumChildren(); i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			IRNode child2 = new IRNode(params);
			params.addChild(child2);
			child2.getBuild(node);
		}
	}
	
	public void buildLength(SimpleNode sn) {
		this.setInst("length");
		
		if(sn.jjtGetNumChildren() == 0)
			return;	
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode child = new IRNode(this);
		this.addChild(child);
		child.getBuild(lhn);
		
	}
	public void buildNot(SimpleNode sn) {
		this.setInst("not");
		
		if(sn.jjtGetNumChildren() == 0)
			return;	
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode child = new IRNode(this);
		this.addChild(child);
		child.getBuild(lhn);
		
	}
	
	public void buildArr_access(SimpleNode sn) {
		this.setInst("lda");
		
		if(sn.jjtGetNumChildren() == 0)
			return;	
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode childl = new IRNode(this);
		this.addChild(childl);
		childl.getBuild(lhn);
		SimpleNode rhn = (SimpleNode)sn.jjtGetChild(1);
		IRNode childr = new IRNode(this);
		this.addChild(childr);
		childr.getBuild(rhn);
		
	}
	
	
	
	public void buildFunction(SimpleNode sn) {
		this.setInst("invoke");
		
		IRNode funcParams = new IRNode(this);
		funcParams.setInst("funcParams");
		this.addChild(funcParams);
		
		IRNode funcReturn = new IRNode(this);
		funcReturn.setInst("funcReturn");
		this.addChild(funcReturn);
		
		if(sn.jjtGetNumChildren() == 0)
			return;	
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		SimpleNode rhn = (SimpleNode)sn.jjtGetChild(1);
		
		IRNode child = new IRNode(this);
		this.addChild(child);

		ArrayList<String> objs = new ArrayList<String>();
		if(lhn.name.equals("this")) {
			objs = sn.descriptors.getDescriptor("this").getAllTypes();
			this.setInst("invoke_virtual");
		}else{
			if(sn.simbolTable.isSimbolHere(lhn.name)) {
				objs = sn.simbolTable.getSimbol(lhn.name).getAssignType().getAllTypes();
				this.setInst("invoke_virtual");
			}else {
				Descriptor d = sn.descriptors.getDescriptor(lhn.name);
				objs = d.getAllTypes();
				this.setInst("invoke_static");
			}
		}
		ArrayList<Function> f = new ArrayList<Function>();
		ArrayList<String> o = new ArrayList<String>();
		for(int i = 0; i < objs.size();i++)
			if(sn.functionTable.isFunctionHere(objs.get(i),sn.name)) {
				f.add(sn.functionTable.getFunction(objs.get(i),sn.name));
				o.add(objs.get(i));
			}
		
		int i = 0;
		Function invoked;
		
		for(;i<f.size();i++) {
			List<Descriptor> listDesc = f.get(i).getDescriptors();
			if (listDesc.size() != rhn.jjtGetNumChildren()) {
				continue;
			}else {
				int ii = 0;
				for (; ii < rhn.jjtGetNumChildren(); ii++) {
					SimpleNode rhnc = (SimpleNode) rhn.children[ii];
					if(!(listDesc.get(ii).getName().equals(((SimpleNode)rhnc.children[0]).type)))
						break;
				}
				if(ii != rhn.jjtGetNumChildren())
					continue;
				else
					break;
			}
		}
		invoked = f.get(i);
		
		if(this.getInst().equals("invoke_virtual")) {
			IRNode var = new IRNode(this);
			this.addChild(var);
			var.setInst(lhn.name);
		}
		
		child.setInst(o.get(i));
		
		IRNode child2 = new IRNode(this);
		this.addChild(child2);
		child2.setInst(sn.name);
		
		List<Descriptor> args = invoked.getDescriptors();
		String retType = invoked.getType();
		
		IRNode retNode = new IRNode(this);
		funcReturn.addChild(retNode);
		retNode.setInst(retType);
		for (i = 0; i < args.size(); i++) {
			IRNode funcParamType = new IRNode(this);
			funcParams.addChild(funcParamType);
			funcParamType.setInst(args.get(i).getName());
		}
		for(i = 0; i < rhn.jjtGetNumChildren(); ++i) {
			SimpleNode node = (SimpleNode)rhn.jjtGetChild(i);
			System.out.println(node.toString());
			IRNode param = new IRNode(this);
			this.addChild(param);
			param.getBuild(node);
		}

	}
	
	
	public void getBuild(SimpleNode sn) {
		//System.out.println(sn.toString());
		switch(sn.toString()) {
		case "ASSIGN_VAR":
			buildAssign(sn);
			break;
		case "EXPRESSION":
			buildExpression(sn);
			break;
		case "INTEGERLITERAL":
			buildIntegerLiteral(sn);
			break;
		case "IDENTIFIER":
			buildIdentifier(sn);
			break;
		case "OPERATOR":
			buildOperator(sn);
			break;
		case "WHILE":
			buildWhile(sn);
			break;
		case "IF_STATEMENT":
			buildIf(sn);
			break;
		case "RETURN_EXPRESSION":
			buildReturn(sn);
			break;
		case "NEW_INT_ARR":
			buildNewIntArr(sn);
			break;
		case "NEW_IDENTIFIER":
			buildNewIdentifier(sn);
			break;
		case "LENGTH":
			buildLength(sn);
			break;
		case "FUNCTION":
			buildFunction(sn);
			break;
		case "NOT_EXPRESSION":
			buildNot(sn);
			break;
		case "ARRAY_ACESS":
			buildArr_access(sn);
			break;
		case "BOOL":
			buildBool(sn);
			break;
		default:
			//System.out.println(sn.toString());
			int n = sn.jjtGetNumChildren();
			IRNode parent = this.parent;
			parent.removeLast();
			for(int i = 0; i < n;i++) {
				SimpleNode node = (SimpleNode)sn.jjtGetChild(i);	
				IRNode child = new IRNode(parent);
				parent.addChild(child);
				child.getBuild(node);
			}

		}
	}

	
	public void dump(String prefix) {
		String str = prefix;
		if(this.getInst() != null) str +=this.getInst() + "  ";
		if(this.local_var != null) str += "(" + this.local_var + ")  ";
		if(this.reg != null) str += "reg_alloc: " + this.reg + "  ";
		if(this.op_stack != null) str += "stack: " + this.op_stack + "  ";
		if(this.locals_stack != null) str += "locals: " + this.locals_stack + "  ";
		System.out.print(str + "\n");
	  if (children != null) {
	    for (int i = 0; i < children.length; ++i) {
	      IRNode n = children[i];
	      if (n != null) {
	       n.dump(prefix + " ");
		  }
	    }
	  }
	}
	
	private int max(int a, int b) {
		return (a >= b) ? a : b;
	}
	
	public void setRegisters() {
		
		if(this.inst.equals("method")) {
			this.max_op_stack = 0;
		}
		
		int n = this.children.length;
		for(int i = 0; i < n; i++) {
			this.children[i].setRegisters();
		}
		
		if(this.inst.equals("method")) {
			this.op_stack = this.max_op_stack;
		}
		else if(this.inst.equals("ldl")
			|| this.inst.equals("ldp")
			|| this.inst.equals("ldg")
			|| this.inst.equals("ldc")) {
			this.num_reg = 1;
			this.reg = this.reg_allocated.pop();
			this.max_op_stack = max(this.max_op_stack,this.maxReg - this.reg_allocated.size());
		}else if(this.inst.equals("lda")) {
			int lhn = this.children[0].num_reg;
			int rhn = this.children[1].num_reg;
			if(lhn == rhn) this.num_reg = lhn + 1;
			else this.num_reg = (rhn > lhn) ? rhn : lhn;
			this.reg_allocated.push(this.children[1].reg);
			this.reg_allocated.push(this.children[0].reg);
			this.reg = this.reg_allocated.pop();
			}
		else if(this.inst.equals("+")
			|| this.inst.equals("-")
			|| this.inst.equals("/")
			|| this.inst.equals("*")) {
			int lhn = this.children[0].num_reg;
			int rhn = this.children[1].num_reg;
			if(lhn == rhn) this.num_reg = lhn + 1;
			else this.num_reg = (rhn > lhn) ? rhn : lhn;

			this.reg_allocated.push(this.children[1].reg);
			this.reg_allocated.push(this.children[0].reg);
			this.reg = this.reg_allocated.pop();
		}else if(this.inst.equals("<")
			|| this.inst.equals("&&")) {
			Integer reg1 = this.children[1].reg;
			Integer reg0 = this.children[0].reg;
			if(reg1 != null ) this.reg_allocated.push(this.children[1].reg);
			if(reg0 != null ) this.reg_allocated.push(this.children[0].reg);
		}
		else if(this.inst.equals("not")) {
			Integer reg = this.children[0].reg;
			if(reg != null)
				this.reg_allocated.push(reg);
		}
		else if(this.inst.equals("st") || this.inst.equals("stg")) {
			
			int rhn = this.children[1].num_reg;
			this.reg_allocated.push(this.children[1].reg);
			//System.out.println(rhn);
		}else if(this.inst.equals("sta")) {
			int rhn = this.children[2].num_reg;
			this.reg_allocated.push(this.children[2].reg);
			this.reg_allocated.push(this.children[1].reg);
			//System.out.println(rhn);
		}
		else if(this.inst.equals("new_int_arr")) {
			int rhn = this.children[0].num_reg;
			this.reg = this.children[0].reg;
			//System.out.println(rhn);
		}else if(this.inst.equals("return")) {
			int rhn = this.children[0].num_reg;
			this.reg_allocated.push(this.children[0].reg);
			//System.out.println(rhn);
		}else if(this.inst.equals("invoke")) {
			for(int i = this.children.length-1; i >=4 ;i--) {
				System.out.println("push of " + this.children[i].reg);
				this.reg_allocated.push(this.children[i].reg);
			}
			//if(!this.children[0].children[0].getInst().equals("void")) // se o return type é diferente de void
				this.reg = this.reg_allocated.pop();
		}
		
	}
	
	  String getClassName() {
	        return this.className;
	    }
	  void setClassName(String className) {
	      this.className = className;
	  }
	
	
}
