
public class IRBuilder {
	
	IRNode root = null;
	
	public IRBuilder(SimpleNode sn) {
		root = new IRNode(null);
		root.build(sn);
		this.setPop(root);
		root.setRegisters();
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
				  !parentInst.equals("invoke_virtual") ) {
			  //this.println("pop");
			  IRNode n = new IRNode(parent);
			  n.setInst("pop");
			  System.out.println("ola");
			  parent.addChild(n, i+1);
		  }
	}
	
}
