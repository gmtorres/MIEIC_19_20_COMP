
public class IRBuilder {
	
	IRNode root = null;
	
	boolean constant_folding = false;
	
	public IRBuilder(SimpleNode sn) {
		root = new IRNode(null);
		root.build(sn);
		this.simplifyBooleans(root);
		this.setPop(root);
		root.setRegisters();
		
		if(this.constant_folding) {
			this.constant_folding(root);
		}
		
	}
	
	public void dump() {
		this.root.dump(" ");
	}
	
	private void simplifyBooleans(IRNode node) {
		
		if(node.getInst().equals("not")) { //se encontrar um not
			IRNode child = node.children[0];
			if(child.getInst().equals("not")) { //se o filho for um not, anular o not
				node.setInst(child.children[0].getInst());
				node.children = child.children[0].children;
				for(int i = 0; i < node.children.length ;i++) {
					node.children[i].parent = node;
				}
				this.simplifyBooleans(node);
				return;
			}else if(child.getInst().equals("&&")) { //se for
				node.setInst("||");
				node.children = child.children;
				for(int i = 0; i < node.children.length ;i++) {
					node.children[i].parent = node;
				}
				for(int i = 0; i < node.children.length;i++) {
					child = node.children[i];
					this.propagateNot(child,i);
				}
			}else if(child.getInst().equals("||")) { //se for
				node.setInst("&&");
				node.children = child.children;
				for(int i = 0; i < node.children.length ;i++) {
					node.children[i].parent = node;
				}
				for(int i = 0; i < node.children.length;i++) {
					child = node.children[i];
					this.propagateNot(child,i);
				}
			}
		}
		
		
		for(int i = 0; i < node.children.length;i++) {
			IRNode child = node.children[i];
			this.simplifyBooleans(child);
		}
		
		if((node.getInst().equals("&&") && node.parent.getInst().equals("&&"))
			|| (node.getInst().equals("||") && node.parent.getInst().equals("||"))) {
			IRNode parent = node.parent;
			for(int i = 1; i < node.children.length;i++) {
				IRNode child = node.children[i];
				parent.addChild(node.children[i]);
				node.children[i].parent = parent;
			}
			for(int i = 0; i < parent.children.length;i++) {
				if(parent.children[i] == node) {
					parent.children[i] = node.children[0];
					parent.children[i].parent = parent;
					break;
				}
			}
		}
		
	}
	
	private void propagateNot(IRNode node, int i) {
		System.out.println(node.getInst() + " " + i);
		
		if(node.getInst().equals("&&"))
			node.setInst("||");
		else if(node.getInst().equals("||"))
			node.setInst("&&");
		else if(node.getInst().equals("not")){
			node.setInst(node.children[0].getInst());
			node.children = node.children[0].children;
			//this.propagateNot(node,i);
			return;
		}else if(node.getInst().equals("<") ||
				node.getInst().equals("ldc") ||
				node.getInst().equals("lda") ||
				node.getInst().equals("ldg") ||
				node.getInst().equals("ldl") ||
				node.getInst().equals("ldp") ||
				node.getInst().equals("invoke_static") ||
				node.getInst().equals("invoke_virtual")){
			IRNode newNode = new IRNode(node.parent);
			IRNode parent = node.parent;
			newNode.setInst("not");
			parent.children[i] = newNode;
			node.parent = newNode;
			newNode.addChild(node);
			return;
		}
		
		for(int a = 0; a < node.children.length;a++) {
			IRNode child = node.children[a];
			this.propagateNot(child,a);
		}
	}
	
	private void setPop(IRNode node) {
		
		for(int i = 0; i < node.children.length;i++) {
			IRNode child = node.children[i];
			this.setPop(child);
			String inst = child.getInst(); 
			if((inst.equals("invoke_virtual") || inst.equals("invoke_static")) && !child.type.equals("void")) {
				this.addPop(child,i);
			}
			if(inst.equals("+") ||
					inst.equals("-") ||
					inst.equals("*") ||
					inst.equals("/") ||
					inst.equals("&&") ||
					inst.equals("||") ||
					inst.equals("<")||
					inst.equals("new_object") ) {
				this.addPop(child,i);
			}
		}
	}
	
	public void addPop(IRNode r, int i) {
		  IRNode parent = r.getParent();
		  if(parent == null)
			  return;
		  String parentInst = parent.getInst();
		  if(!parentInst.equals("st") && 
				  !parentInst.equals("+") && 
				  !parentInst.equals("-") && 
				  !parentInst.equals("/") && 
				  !parentInst.equals("*") && 
				  !parentInst.equals("&&") && 
				  !parentInst.equals("||") && 
				  !parentInst.equals("not") && 
				  !parentInst.equals("<") && 
				  !parentInst.equals("param") &&
				  !parentInst.equals("funcParams") &&
				  !parentInst.equals("st") &&
				  !parentInst.equals("stg") &&
				  !parentInst.equals("sta") &&
				  !parentInst.equals("return")&&
				  !( parentInst.equals("if") && i == 0) &&
				  !( parentInst.equals("while") && i == 0) &&
				  !parentInst.equals("invoke_static") &&
				  !parentInst.equals("invoke_virtual") &&
				  !parentInst.equals("length") ) {
			  //this.println("pop");
			  IRNode n = new IRNode(parent);
			  n.setInst("pop");
			  System.out.println("ola");
			  parent.addChild(n, i+1);
		  }
	}
	
	private void constant_folding(IRNode sn) {
		
		for(IRNode child : sn.children) {
			this.constant_folding(child);
		}
		int opcode = 0;
		switch(sn.getInst()) {
		case "+":
			opcode = 1;
			break;
		case "-":
			opcode = 2;
			break;
		case "*":
			opcode = 3;
			break;
		case "/":
			opcode = 4;
			break;
		case "<":
			opcode = 5;
			break;
		case "&&":
			opcode = 6;
			break;
		}
		
		if(opcode > 0) {
			IRNode lhn = sn.children[0];
			IRNode rhn = sn.children[1];
			if(lhn.getInst().equals("ldc") && rhn.getInst().equals("ldc")) {
				int val1 = Integer.parseInt(lhn.children[0].getInst());
				int val2 = Integer.parseInt(rhn.children[0].getInst());
				int res = val1;
				if(opcode == 1)
					res+=val2;
				else if(opcode == 2)
					res-=val2;
				else if(opcode == 3)
					res*=val2;
				else if(opcode == 4)
					res/=val2;
				else if(opcode == 5)
					if(val1 < val2)
						res = 1;
					else
						res = 0;
				else if(opcode == 6)
					res&=val2;
				else
					return;
				
				if(opcode == 5 || opcode == 6)
					sn.type = "boolean";
				
				sn.setInst("ldc");
				sn.resetChildren();
				IRNode child = new IRNode(sn);
				sn.addChild(child);
				child.setInst(String.valueOf(res));
				
			}
		}
		
		
	}
	
	
}
