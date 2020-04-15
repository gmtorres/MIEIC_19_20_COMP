
public class IRNode {
	
	IRNode parent;
	IRNode [] children;
	
	private String inst;
	
	
	public IRNode(IRNode p) {
		parent = p;
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
	
	public void build(SimpleNode sn) {
		//System.out.println(sn.toString());
		
		
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			if(node.toString().equals("IMPORT"))
				continue;
			
			IRNode child = new IRNode(this);
			this.addChild(child, i);
			
			if(node.toString().equals("CLASS_DECL")) {
				child.setInst("class");
				child.buildClass(node);
			}
			else
				child.build( node ); 
		}
	}
	
	public void buildClass(SimpleNode sn) {
		
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		
		int i = 0;
		
		if(lhn.toString().equals("EXTENDS")) {
			IRNode child = new IRNode(this);
			child.setInst(lhn.name);
			this.addChild(child, i++);
		}else {
			IRNode child = new IRNode(this);
			child.setInst("Object");
			this.addChild(child, i++);
		}
		
		for(; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			IRNode child = new IRNode(this);
			this.addChild(child, i);
			
			if(node.toString().equals("METHOD")) {
				child.setInst("method");
				child.buildMethod(node);
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
				child.buildArgument(node);
			}else if(node.toString().equals("METHOD_BODY")) {
				//child.buildMethodBody( node ); 
				for(int a = 0; a < node.jjtGetNumChildren(); a++) {
					SimpleNode node2 = (SimpleNode)node.jjtGetChild(a);
					
					if(node2.toString().equals("VAR_DEC")) {
						IRNode child2 = new IRNode(locals);
						locals.addChild(child2);
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
	}
	
	public void buildArgument(SimpleNode sn) {
		this.setInst(sn.type);
	}
	
	public void buildVarDec(SimpleNode sn) {
		this.setInst(sn.name);
		//put offset in simboltable
	}
	
	public void buildAssign(SimpleNode sn) {
		if(sn.jjtGetNumChildren() == 0)
			return;
		
		int n = sn.jjtGetNumChildren();
		
		SimpleNode lhn = (SimpleNode)sn.jjtGetChild(0);
		IRNode var = new IRNode(this);
		if(lhn.jjtGetNumChildren() == 1) {
			this.inst = "st";
			var.setInst(((SimpleNode)lhn.jjtGetChild(0)).name);
			this.addChild(var);
		}else if(lhn.jjtGetNumChildren() == 2) {
			this.inst = "sta";
			var.setInst(((SimpleNode)lhn.jjtGetChild(0)).name);
			this.addChild(var);
			SimpleNode index_exp = (SimpleNode)lhn.jjtGetChild(1);
			for(int a = 0; a < index_exp.jjtGetNumChildren(); a++) {
				SimpleNode node = (SimpleNode)index_exp.jjtGetChild(a);
				
				IRNode child = new IRNode(this);
				this.addChild(child);
				
				child.getBuild(node);
			}
		}else
			return;
		
		SimpleNode rhn = (SimpleNode)sn.jjtGetChild(1);
		for(int a = 0; a < rhn.jjtGetNumChildren(); a++) {
			SimpleNode node = (SimpleNode)rhn.jjtGetChild(a);
			
			IRNode child = new IRNode(this);
			this.addChild(child);
			
			child.getBuild(node);
		}
	}
	
	public void buildExpression(SimpleNode sn) {
		if(sn.jjtGetNumChildren() == 0)
			return;
		int n = sn.jjtGetNumChildren();
		
		for(int i = 0; i < n; i++) {
			SimpleNode node = (SimpleNode)sn.jjtGetChild(i);
			
			IRNode child = new IRNode(this);
			this.addChild(child);
			
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
		this.setInst(String.valueOf(sn.val));
	}
	
	public void buildIdentifier(SimpleNode sn) {
		String load = sn.simbolTable.getScope(sn.name);
		//this.setInst(sn.simbolTable.getScope(sn.name) + "  " +sn.name);
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
		this.addChild(child);
	}
	
	public void getBuild(SimpleNode sn) {
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
		default:
			int n = sn.jjtGetNumChildren();
			IRNode actual = this;
			this.parent.removeLast();
			for(int i = 0; i < n;i++) {
				SimpleNode node = (SimpleNode)sn.jjtGetChild(i);	
				IRNode child = new IRNode(actual.parent);
				actual.parent.addChild(child);
				child.getBuild(node);
			}
		}
	}
	
	
	
	
	
	public void dump(String prefix) {
		String str = prefix;
		if(this.getInst() != null) str +=this.getInst();
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
	
	
}
