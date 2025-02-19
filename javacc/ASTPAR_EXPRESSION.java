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
	  
	  SimpleNode method = (SimpleNode)this.jjtGetParent();
	  while(!method.toString().equals("METHOD")) {
		  method = (SimpleNode)method.jjtGetParent();
	  }
	  
	  SimpleNode lhn  = (SimpleNode) this.children[0];
	  if(lhn.toString().equals("IDENTIFIER")) {
		  if(this.simbolTable.isSimbolKnown(lhn.name) == false){
			  System.out.println("Error on line " + this.lineNo + ", column " + this.columnNo + ": Simbol " + lhn.name + " is not known.");
			  this.decrementMaxErros();
			  return false;
		  }else {
			  Simbol s = this.simbolTable.getSimbol(lhn.name);
			  if(s == null){
				  System.out.println("Error on line " + this.lineNo + ", column " + this.columnNo + ": Simbol " + lhn.name + " is not known.");
				  this.decrementMaxErros();
				  return false;
			  }else if(s.isInitialized == false){
				  if(s.condInitialized || this.simbolTable.getScope(lhn.name).equals("global")) {
					  System.out.println("Warning on line " + this.lineNo + ", column " + this.columnNo + ": Simbol " + lhn.name + " may not have been initiated.");
				  }else {
					  System.out.println("Error on line " + this.lineNo + ", column " + this.columnNo + ": Simbol " + lhn.name + " has not been initiated.");
					  this.decrementMaxErros();
					  return false;
				  }
			  }
			  String scope = this.simbolTable.getScope(lhn.name);
			  if(scope.equals("global")) {
				  if(method.is_static) {
					  System.out.println("Error on line " + lhn.lineNo + ", column " + lhn.columnNo + ": Non static variable \"" + lhn.name +"\" cannot be refereced from a static context");
					  this.decrementMaxErros();
					  return false;
				  }
			  }
			  lhn.type = s.getType().getName();
		  }
	  }
	  
	  this.type = lhn.type;
	  
	  return result;
  }

}
/* JavaCC - OriginalChecksum=7b409adf99ba57e0bbba4c1138748919 (do not edit this line) */
