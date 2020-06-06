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
	
	static boolean hasConstructor = false;
	
	static boolean setRegisters = true;
	
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
	Simbol simbol = null;
	
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
	
	public void resetChildren() {
		this.children = new IRNode[0];
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
		  
	      IRNode c[] = new IRNode[children.length + 1];
	      //System.arraycopy(children, 0, c, 0, children.length);
	      System.arraycopy(children, 0, c, 0, i);
	      System.arraycopy(children, i, c, i+1, children.length-(i));
	      c[i] = n;
	      for(int a = 0; a < c.length;a++) {
	    	  //System.out.println(c[a].getInst());
	      }
	      children = c;

	  }
	  
	  public void removeLast() {
		  if(this.children == null) return;
		  int n = this.children.length-1;
		  if(n < 0) return;
		  IRNode c[] = new IRNode[n];
		  System.arraycopy(children, 0, c, 0, n);
	      children = c;
	  }
	  
	  public boolean isLast() {
		  IRNode node = this;
		  IRNode parent = this.getParent();
		  while(parent.getInst().equals("not")) {
			  node = parent;
			  parent = node.getParent();
		  }
		  return parent.getChildren()[parent.getChildren().length - 1] == node;
	  }
	  public boolean isLastInAnd() {
		  IRNode node = this;
		  IRNode parent = node.getParent();
		  while(parent.getInst().equals("not")) {
			  node = parent;
			  parent = node.getParent();
		  }
		  while(parent.getInst().equals("&&")) {
			  if(parent.getChildren()[parent.getChildren().length - 1] != node)
				  return false;
			  node = parent;
			  parent = node.getParent();
		  }
		  
		  return parent.getChildren()[parent.getChildren().length - 1] == node;
	  }
	  public boolean checkAnd() {
		  IRNode parent = this.getParent();
		  while(parent.getInst().equals("not")) {
			  parent = parent.getParent();
		  }
		  return parent.getInst().equals("&&");
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
		
		if(sn.jjtGetNumChildren() == 0) {
			IRNode child = new IRNode(this);
			child.setInst("java/lang/Object");
			this.addChild(child);
			IRNode fields = new IRNode(this);
			fields.setInst("fields");
			this.addChild(fields);
			return;
		}
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
			child.setInst("java/lang/Object");
			this.addChild(child);
		}
		
		IRNode fields = new IRNode(this);
		fields.setInst("fields");
		this.addChild(fields);
		
		IRNode.hasConstructor = SimpleNode.hasConstructor;
		
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
		//System.out.println()
		if(((SimpleNode)sn.jjtGetChild(0)).toString().equals("CONSTRUCTOR")) {
			IRNode type = new IRNode(this);
			type.setInst("void");
			method_spec.addChild(type);
		}else {
			IRNode type = new IRNode(this);
			type.setInst(sn.type);
			method_spec.addChild(type);
		}
		
		IRNode pub = new IRNode(this);
		pub.setInst("public");
		access_spec.addChild(pub);
		
		if(sn.is_static) {
			IRNode stat = new IRNode(this);
			stat.setInst("static");
			access_spec.addChild(stat);
		}else { // se não static, tem o "this"
			IRNode child = new IRNode(this);
			arguments_specs.addChild(child);
			if(setRegisters) child.local_var = 0;
			Simbol s = sn.simbolTable.getSimbol("this");
			child.simbol = s;
			if(setRegisters) s.local_var = child.local_var;
			child.inst = s.getName();
		}
		if(((SimpleNode)sn.jjtGetChild(0)).toString().equals("CONSTRUCTOR")) {
			IRNode name = new IRNode(this);
			name.setInst("<init>");
			method_spec.addChild(name);
			IRNode superC = new IRNode(statements);
			statements.addChild(superC);
			superC.setInst("super");
			IRNode obj = new IRNode(superC);
			superC.addChild(obj);
			obj.setInst(this.parent.children[1].getInst());
		}else {
			IRNode name = new IRNode(this);
			name.setInst(sn.name);
			method_spec.addChild(name);
		}
		
		
		SimpleNode root = sn;
		if(((SimpleNode)root.jjtGetChild(0)).toString().equals("CONSTRUCTOR"))
			root = (SimpleNode)sn.jjtGetChild(0);
		
		if(root.jjtGetNumChildren() == 0)
			return;
		int n = root.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)root.jjtGetChild(0);

		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)root.jjtGetChild(i);
			
			IRNode child = new IRNode(this);
			
			if(node.toString().equals("ARGUMENT")) {	
				arguments_specs.addChild(child); 
				if(setRegisters) child.local_var = arguments_specs.children.length - 1;
				Simbol s = root.simbolTable.getSimbol(node.name);
				child.simbol = s;
				if(setRegisters) s.local_var = child.local_var;
				child.buildArgument(node);
			}else if(node.toString().equals("METHOD_BODY")) {
				//child.buildMethodBody( node ); 
				for(int a = 0; a < node.jjtGetNumChildren(); a++) {
					SimpleNode node2 = (SimpleNode)node.jjtGetChild(a);
					
					if(node2.toString().equals("VAR_DEC")) {
						IRNode child2 = new IRNode(locals);
						locals.addChild(child2);
						if(setRegisters) child2.local_var =arguments_specs.children.length + locals.children.length - 1	;
						child2.buildVarDec(node2);
					}else {
						IRNode child2 = new IRNode(statements);
						statements.addChild(child2);
						child2.getBuild(node2);
					}
				}
			}
			else {
				statements.addChild(child);
				child.build( node ); 
			}
		}
		//System.out.println(arguments_specs.children.length + "  " + locals.children.length);
		this.locals_stack = arguments_specs.children.length + locals.children.length;
		//if(!sn.is_static) 
			//this.locals_stack +=1;
	}
	
	public void buildArgument(SimpleNode sn) {
		this.setInst(sn.type);
	}
	
	public void buildVarDec(SimpleNode sn) {
		this.setInst(sn.name);
		if(setRegisters) sn.simbolTable.getSimbol(sn.name).local_var = this.local_var;
		this.simbol = sn.simbolTable.getSimbol(sn.name);
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
	
	public void buildFor(SimpleNode sn) {
		SimpleNode ass = (SimpleNode)(sn.jjtGetChild(0)).jjtGetChild(0); //ASSIGN
		this.getBuild(ass);
		IRNode newWhile = new IRNode(this.parent);
		newWhile.setInst("while");
		parent.addChild(newWhile);
		
		SimpleNode cond = (SimpleNode)(sn.jjtGetChild(1)).jjtGetChild(0); //CONDITION
		IRNode var = new IRNode(newWhile);
		newWhile.addChild(var);
		var.getBuild(cond);
		
		SimpleNode incNode = (SimpleNode)sn.jjtGetChild(2);
		
		SimpleNode rhn = (SimpleNode)sn.jjtGetChild(3);
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
			IRNode child2 = new IRNode(newWhile);
			newWhile.addChild(child2);
			child2.getBuild(node2);
		}
		IRNode inc = new IRNode(newWhile);
		newWhile.addChild(inc);
		inc.getBuild(incNode);
	}
	
	public void buildAssign(SimpleNode sn) {
		if(sn.jjtGetNumChildren() == 0)
			return;
		
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode var = new IRNode(this);
		Simbol s = sn.simbolTable.getSimbol(((SimpleNode)lhn.jjtGetChild(0)).name);
		if(setRegisters) var.local_var = s.local_var;
		var.simbol = s;
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
		this.type = "int";
	}
	public void buildFloatLiteral(SimpleNode sn) {
		this.setInst("ldc");
		IRNode child = new IRNode(this);
		child.setInst(String.valueOf(sn.valF));
		this.addChild(child);
		this.type = "float";
	}
	public void buildBool(SimpleNode sn) {
		this.setInst("ldc");
		IRNode child = new IRNode(this);
		child.setInst(String.valueOf(sn.val));
		this.addChild(child);
		this.type = "boolean";
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
		if(setRegisters) child.local_var = s.local_var;
		child.simbol = s;
		this.type = s.getType().name;
		this.addChild(child);
	}
	
	public void buildThis(SimpleNode sn) {
		this.setInst("ldl");
		IRNode child = new IRNode(this);
		child.setInst("this");
		child.local_var = 0;
		child.simbol = sn.simbolTable.getSimbol("this");
		this.type = sn.descriptors.getDescriptor("this").name;
		this.addChild(child);
	}
	
	public void buildString(SimpleNode sn) {
		this.setInst("lds");
		IRNode child = new IRNode(this);
		child.setInst(sn.name);
		this.addChild(child);
		this.type = sn.type;
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
		if(lhn.toString().equals("NEW_IDENTIFIER")) {
			objs = sn.descriptors.getDescriptor(((SimpleNode) lhn.children[0]).name).getAllTypes();
			this.setInst("invoke_virtual");
		}
		else if(lhn.toString().equals("FUNCTION")) {
			objs = sn.descriptors.getDescriptor(lhn.type).getAllTypes();
			this.setInst("invoke_virtual");
		}
		else if(lhn.name.equals("this")) {
			objs = sn.descriptors.getDescriptor("this").getAllTypes();
			this.setInst("invoke_virtual");
		}else{
			if(sn.simbolTable.isSimbolKnown(lhn.name)) {
				objs = sn.simbolTable.getSimbol(lhn.name).getAssignType().getAllTypes();
				this.setInst("invoke_virtual");
			}else {
				Descriptor d = sn.descriptors.getDescriptor(lhn.name);
				if(d != null)
					objs = d.getAllTypes();
				else
					objs.add(lhn.name);
				this.setInst("invoke_static");
			}
		}
		String params = "";
		for(int i = 0; i < rhn.jjtGetNumChildren();i++) {
			SimpleNode rhnc = (SimpleNode) rhn.children[i];
			//System.out.println(((SimpleNode)rhnc.children[0]).type);
			params+=((SimpleNode)rhnc.children[0]).type+",";
		}
		
		ArrayList<Function> f = new ArrayList<Function>();
		ArrayList<String> o = new ArrayList<String>();
		for(int i = 0; i < objs.size();i++)
			if(sn.functionTable.isFunctionHere(objs.get(i),sn.name,params)) {
				f.add(sn.functionTable.getFunction(objs.get(i),sn.name,params));
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
			IRNode load = new IRNode(this);
			this.addChild(load);
			if(lhn.toString().equals("NEW_IDENTIFIER")) {
				load.getBuild(lhn);
			}
			else { 

				if(lhn.toString().equals("FUNCTION")) {
					load.getBuild(lhn);
				}else {
					IRNode var = new IRNode(load);
					load.addChild(var);
					var.setInst(lhn.name);
					if(lhn.name.equals("this")) {
						load.setInst("ldl");
						var.local_var = 0;
						var.simbol = sn.simbolTable.getSimbol("this");
						load.type = "this";
					}else {
						Simbol s = sn.simbolTable.getSimbol(lhn.name);
						String scope = sn.simbolTable.getScope(lhn.name);
						Integer local = s.local_var;
						if(scope.equals("global")) {
							load.setInst("ldg");
						}else {
							load.setInst("ldl");
						}
						var.local_var = local;
						var.simbol = s;
						load.type = s.getType().name;
					}
				}
			}
		}
		
		child.setInst(o.get(i));
		
		IRNode child2 = new IRNode(this);
		this.addChild(child2);
		child2.setInst(sn.name);
		
		List<Descriptor> args = invoked.getDescriptors();
		String retType = invoked.getType();
		this.type = retType;
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
			//System.out.println(node.toString());
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
		case "FLOATLITERAL":
			buildFloatLiteral(sn);
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
		case "FOR":
			buildFor(sn);
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
		case "THIS":
			buildThis(sn);
			break;
		case "STRING":
			buildString(sn);
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
	
	private void cleanStack() {
		for(int i = this.children.length-1; i >= 0 ;i--) {
			if(this.children[i].reg != null)
				this.reg_allocated.push(this.children[i].reg);
		}
	}
	private void resetStack() {
		this.reg_allocated = new Stack<Integer>();
		for(int i = maxReg; i > 0; i--) {
			reg_allocated.push(i);
		}
	}
	
	public void setRegisters() {
		
		if(this.inst.equals("method")) {
			this.max_op_stack = 0;
		}else if(this.inst.equals("sta")) {
			this.children[0].reg = this.reg_allocated.pop();
		}else if(this.inst.equals("stg")) {
			this.children[0].reg = this.reg_allocated.pop();
		}else if(this.inst.equals("new_object")) {
			this.reg = this.reg_allocated.pop();
			this.children[0].reg = this.reg_allocated.pop();
		}
		
		int n = this.children.length;
		for(int i = 0; i < n; i++) {
			this.children[i].setRegisters();
		}
		
		if(this.inst.equals("method")) {
			this.op_stack = this.max_op_stack;
			this.resetStack();
		}
		else if(this.inst.equals("super")) {
			this.max_op_stack = max(this.max_op_stack,this.maxReg - this.reg_allocated.size()+3);
		}
		else if(this.inst.equals("ldl")
			|| this.inst.equals("ldp")
			|| this.inst.equals("ldg")
			|| this.inst.equals("ldc")
			|| this.inst.equals("lds")) {
			this.num_reg = 1;
			this.reg = this.reg_allocated.pop();
			this.max_op_stack = max(this.max_op_stack,this.maxReg - this.reg_allocated.size());
		}
		else if(this.inst.equals("lda")) {
			int lhn = this.children[0].num_reg;
			int rhn = this.children[1].num_reg;
			if(lhn == rhn) this.num_reg = lhn + 1;
			else this.num_reg = (rhn > lhn) ? rhn : lhn;
			this.reg_allocated.push(this.children[1].reg);
			this.reg_allocated.push(this.children[0].reg);
			this.reg = this.reg_allocated.pop();
		}
		else if(this.inst.equals("new_object")) {
			//this.children[0].reg = this.reg_allocated.pop();
			//this.children[0].reg = this.reg_allocated.pop();
			//this.reg = this.reg_allocated.pop();
			this.max_op_stack = max(this.max_op_stack,this.maxReg - this.reg_allocated.size());
			IRNode rhn = this.children[1];
			for(int i = rhn.children.length-1; i >= 0 ;i--) {
				this.reg_allocated.push(rhn.children[i].reg);
			}
			this.reg_allocated.push(this.children[0].reg);
			//System.out.println(this.reg_allocated.peek());
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
		}
		else if(this.inst.equals("st") || this.inst.equals("stg")) {
			int rhn = this.children[1].num_reg;
			this.reg_allocated.push(this.children[1].reg);
			if(this.inst.equals("stg"))
				this.reg_allocated.push(this.children[0].reg);
		}
		else if(this.inst.equals("sta")) {
			int rhn = this.children[2].num_reg;
			this.reg_allocated.push(this.children[2].reg);
			this.reg_allocated.push(this.children[1].reg);
			this.reg_allocated.push(this.children[0].reg);
		}
		else if(this.inst.equals("<") || this.inst.equals(">")
				|| this.inst.equals("&&") || this.inst.equals("||")) {
			Integer reg1 = this.children[1].reg;
			Integer reg0 = this.children[0].reg;
			if(reg1 != null ) this.reg_allocated.push(this.children[1].reg);
			if(reg0 != null ) this.reg_allocated.push(this.children[0].reg);
		}
		else if(this.inst.equals("new_int_arr")) {
			int rhn = this.children[0].num_reg;
			this.reg = this.children[0].reg;
		}
		else if(this.inst.equals("invoke_static")) {
			for(int i = this.children.length-1; i >=4 ;i--) {
				if(this.children[i].reg != null) this.reg_allocated.push(this.children[i].reg);
			}
			if(!this.children[1].children[0].getInst().equals("void")) // se o return type é diferente de void
				this.reg = this.reg_allocated.pop();
		}
		else if(this.inst.equals("invoke_virtual")) {
			for(int i = this.children.length-1; i >=5 ;i--) {
				if(this.children[i].reg != null) this.reg_allocated.push(this.children[i].reg);
			}
			this.reg_allocated.push(this.children[3].reg);
			if(!this.children[1].children[0].getInst().equals("void")) // se o return type é diferente de void
				this.reg = this.reg_allocated.pop();
		}
		else if(this.inst.equals("return")) {
			int rhn = this.children[0].num_reg;
			this.reg_allocated.push(this.children[0].reg);
		}else if(this.inst.equals("not")) { //TODO NOT
			Integer reg = this.children[0].reg;
			if(reg != null)
				this.reg_allocated.push(reg);
		}else if(this.inst.equals("pop")) { //TODO NOT
			IRNode parent = this.parent;
			int i = 0;
			for(; i < parent.getChildren().length;i++) {
				if(parent.children[i] == this)
					break;
			}
			i--;
			this.reg_allocated.push(parent.children[i].reg);
		}
		
	}
	
	  String getClassName() {
	        return this.className;
	    }
	  void setClassName(String className) {
	      this.className = className;
	  }
	
	
}
