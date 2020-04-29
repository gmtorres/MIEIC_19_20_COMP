/* Generated By:JJTree: Do not edit this line. ASTELSE_BODY.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTELSE_BODY extends SimpleNode {
  public ASTELSE_BODY(int id) {
    super(id);
  }

  public ASTELSE_BODY(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean doSemanticAnalysis(StringBuilder info) throws SemanticException {
	  
	  boolean result = true;
	  StringBuilder info_temp = new StringBuilder("ELSE:");
	  if(this.children != null) {
		  for(Node node : this.children) {
			  boolean r = ((SimpleNode) node).doSemanticAnalysis(info_temp);
			  result = result && r;
		  }
	  }
	  
	  String [] assignVars = info_temp.toString().split(" ");
	  info.append(" // ");
	  for(int i = 1; i < assignVars.length; i++) {

		  info.append(" " + assignVars[i]);
		  Simbol s = this.simbolTable.getSimbol(assignVars[i]);
		  //s.elseInitialized = true;
		  s.isInitialized = false;
		  s.condInitialized = true;
	  }
	  
	  return result;
	  
  }

}
/* JavaCC - OriginalChecksum=735bbef29457db2e2da2cfc846e774dd (do not edit this line) */
