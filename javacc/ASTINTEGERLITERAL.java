/* Generated By:JJTree: Do not edit this line. ASTINTEGERLITERAL.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTINTEGERLITERAL extends SimpleNode {
  public ASTINTEGERLITERAL(int id) {
    super(id);
  }

  public ASTINTEGERLITERAL(Jmm p, int id) {
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
	  this.type = "int";
	  return result;
  }

}
/* JavaCC - OriginalChecksum=8b847b3aaf62bfc9dee66c9d67951a3b (do not edit this line) */
