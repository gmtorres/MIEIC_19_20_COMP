/* Generated By:JJTree: Do not edit this line. ASTIF_BODY.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIF_BODY extends SimpleNode {
  public ASTIF_BODY(int id) {
    super(id);
  }

  public ASTIF_BODY(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean doSemanticAnalysis(StringBuilder info) {
	  
	  boolean result = true;
	  StringBuilder info_temp = new StringBuilder("IF:");
	  if(this.children != null) {
		  for(Node node : this.children) {
			  boolean r = ((SimpleNode) node).doSemanticAnalysis(info_temp);
			  result = result && r;
		  }
	  }

	  String [] assignVars = info_temp.toString().split(" ");
	  for(int i = 1; i < assignVars.length; i++) {

		  info.append(" " + assignVars[i]);
		  Simbol s = this.simbolTable.getSimbol(assignVars[i]);
		  s.ifInitialized = true;
		  s.isInitialized = false;
		  s.condInitialized = true;
	  }
	  
	  return result;
	  
  }
  
}
/* JavaCC - OriginalChecksum=a5ef46a4c30676ceb5a90ad18716ead7 (do not edit this line) */
