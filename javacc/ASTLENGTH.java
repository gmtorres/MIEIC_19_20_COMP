/* Generated By:JJTree: Do not edit this line. ASTLENGTH.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTLENGTH extends SimpleNode {
  public ASTLENGTH(int id) {
    super(id);
  }

  public ASTLENGTH(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean doSemanticAnalysis(StringBuilder info) {
	  
	  
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
	  if(!lhn.toString().equals("IDENTIFIER") || (lhn.type != null && (lhn.type.equals("int") || lhn.type.equals("boolean") ))) {
		  System.out.println("Cannot do size of this simbol");
		  return false;
	  }
	  
	  this.type = "int";
	  
	  return result;
  }
  
}
/* JavaCC - OriginalChecksum=5ffa76d0f73fad2c7617263b56c3facf (do not edit this line) */
