/* Generated By:JJTree: Do not edit this line. ASTFLOATLITERAL.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTFLOATLITERAL extends SimpleNode {
  public ASTFLOATLITERAL(int id) {
    super(id);
  }

  public ASTFLOATLITERAL(Jmm p, int id) {
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
	  this.type = "float";
	  return result;
  }
}
/* JavaCC - OriginalChecksum=23e9c1a01a0ecb502a0e7701e28449ce (do not edit this line) */
