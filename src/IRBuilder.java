
public class IRBuilder {
	
	IRNode root = null;
	
	boolean constant_folding = false;
	
	public IRBuilder(SimpleNode sn) {
		root = new IRNode(null);
		root.build(sn);
		this.setPop(root);
		root.setRegisters();
		
		if(this.constant_folding) {
			this.constant_folding(root);
		}
		
	}
	
	public void dump() {
		this.root.dump(" ");
	}
	
	public void setPop(IRNode node) {
		
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
	
	public void constant_folding(IRNode sn) {
		
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
