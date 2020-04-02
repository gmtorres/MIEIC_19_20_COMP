/* Generated By:JJTree: Do not edit this line. ASTNOT_EXPRESSION.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNOT_EXPRESSION extends SimpleNode {
  public ASTNOT_EXPRESSION(int id) {
    super(id);
  }

  public ASTNOT_EXPRESSION(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean doSemanticAnalysis() {
	  
	  
	  boolean result = true;
	  
	  if(this.children != null) {
		  for(Node node : this.children) {
			  boolean r = ((SimpleNode) node).doSemanticAnalysis();
			  result = result && r;
		  }
	  }
	  
	  if(result == false)
		  return false;
	  
	  SimpleNode lhn  = (SimpleNode) this.children[0];
	  if(lhn.toString().equals("IDENTIFIER")) {
		  if(this.simbolTable.isSimbolKnown(lhn.name) == false){
			  System.out.println("Simbol " + lhn.name + " is not known.");
			  return false;
		  }else {
			  lhn.type = this.simbolTable.getSimbol(lhn.name).getType().getName();
		  }
	  }
	  
	  this.type = lhn.type;
	  
	  if(!this.type.equals("boolean")) {
		  System.out.println("Was expecting a boolean");
		  this.type = null;
		  result = false;
	  }
	  
	  return result;
  }

}
/* JavaCC - OriginalChecksum=4824ff70676d93ce946c932c055e30e6 (do not edit this line) */
