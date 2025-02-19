/* Generated By:JJTree: Do not edit this line. ASTNEW_INT_ARR.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNEW_INT_ARR extends SimpleNode {
  public ASTNEW_INT_ARR(int id) {
    super(id);
  }

  public ASTNEW_INT_ARR(Jmm p, int id) {
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
	  
	  if(result == false)
		  return false;
	  
	  SimpleNode lhn  = (SimpleNode) this.children[0];
	  
	  if(!lhn.type.equals("int")) {
		  System.out.println("Error on line " + lhn.lineNo + ", column " + lhn.columnNo + ": Size of array must be a int");
		  this.decrementMaxErros();
		  return false;
	  }
	  if(((SimpleNode)lhn.children[0]).toString().equals("INTEGERLITERAL")) {
		  this.val = ((SimpleNode)lhn.children[0]).val;
	  }
	  
	  
	  this.type = "int[]";
	  
	  return result;
  }

}
/* JavaCC - OriginalChecksum=ce4cf0d753328eefc3373506e1924bc3 (do not edit this line) */
