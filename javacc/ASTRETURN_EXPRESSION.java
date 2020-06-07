public class ASTRETURN_EXPRESSION extends SimpleNode {
  public ASTRETURN_EXPRESSION(int id) {
    super(id);
  }

  public ASTRETURN_EXPRESSION(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean doSemanticAnalysis(StringBuilder info) throws SemanticException { 
	  boolean result = true;

		if(this.children != null) {
			for(Node node : this.children) {
				boolean r = ((SimpleNode) node).doSemanticAnalysis(info);
				result = result && r;
			}
		}

		if (result == false) {
			return result;
		}
		
		
		SimpleNode grandParent = (SimpleNode)(this.parent.jjtGetParent());
		
	    SimpleNode lhn  = (SimpleNode) this.children[0];
	  
	    if ((this.descriptors.getDescriptor(lhn.type).name).equals(this.descriptors.getDescriptor(grandParent.type).name)) {
	    	result = true;
	    }
	    else {
	    	System.out.println("Error on line " + lhn.lineNo + ", column " + lhn.columnNo +": Return type " + lhn.type + " doesn't match the function " + grandParent.name + " return type " + grandParent.type);
	    	result = false;
		    this.decrementMaxErros();
	    }

	    return result;
  }

}
/* JavaCC - OriginalChecksum=8728c66c32884a220b2c6bb9d85c4a53 (do not edit this line) */
