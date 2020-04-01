/* Generated By:JJTree: Do not edit this line. ASTPAR_EXPRESSION.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTPAR_EXPRESSION extends SimpleNode {
  public ASTPAR_EXPRESSION(int id) {
    super(id);
  }

  public ASTPAR_EXPRESSION(Jmm p, int id) {
    super(p, id);
  }
  
public boolean doSemanticAnalysis() {
	  
	  if(this.has_scope == false && this.parent != null) {
			  this.simbolTable = ((SimpleNode)this.parent).simbolTable;
	  }
	  else if(this.has_scope == true){
		  this.simbolTable = new SimbolTable();
		  if(this.parent != null){
			  this.simbolTable.setParent( ( (SimpleNode)this.parent ).simbolTable);
		  }
	  }
	  
	  boolean result = true;
	   
	  if(this.children != null) {
		  for(Node node : this.children) {
			  boolean r = ((SimpleNode) node).doSemanticAnalysis();
			  result = result && r;
		  }
	  }
	  
	  this.type = ((SimpleNode) this.children[0]).type;

	  return result;
  }

}
/* JavaCC - OriginalChecksum=7b409adf99ba57e0bbba4c1138748919 (do not edit this line) */
