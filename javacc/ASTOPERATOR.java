/* Generated By:JJTree: Do not edit this line. ASTOPERATOR.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTOPERATOR extends SimpleNode {
  public ASTOPERATOR(int id) {
    super(id);
  }

  public ASTOPERATOR(Jmm p, int id) {
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
	  
	  SimpleNode lhn  = (SimpleNode) this.children[0];
	  SimpleNode rhn  = (SimpleNode) this.children[1];
	  if(lhn.toString().equals("IDENTIFIER")) {
		  Simbol s = this.simbolTable.getSimbol(lhn.name);
		  if(s == null){
			  System.out.println("Simbol " + lhn.name + " is not known.");
			  return false;
		  }else if(s.isInitialized == false){
			  if(s.condInitialized) {
				  System.out.println("Simbol " + lhn.name + " may not have been initiated.");
			  }else
				  System.out.println("Simbol " + lhn.name + " has not been initiated.");
			  return false;
		  }else {
			  lhn.type = s.getType().getName();
		  }
	  }
	  if(rhn.toString().equals("IDENTIFIER")) {
		  Simbol s = this.simbolTable.getSimbol(rhn.name);
		  if(s == null){
			  System.out.println("Simbol " + rhn.name + " is not known.");
			  return false;
		  }else if(s.isInitialized == false){
			  if(s.condInitialized) {
				  System.out.println("Simbol " + lhn.name + " may not have been initiated.");
			  }else
				  System.out.println("Simbol " + lhn.name + " has not been initiated.");
			  return false;
		  }else {
			  rhn.type = s.getType().getName();
		  }
	  }
	  
	  if(result == false)
		  return false;
	  
	  //System.out.println(lhn.type + "  " + lhn.toString() + "  " + lhn.name);
	  //System.out.println(rhn.type + "  " + rhn.toString() + "  " + rhn.name);
	  if(!lhn.type.equals(rhn.type)) {
		  System.out.println("Types incompatible " + lhn.type + " and " + rhn.type);
		  result = false;
	  }
	  
	  if(this.name.equals("&&")) {
		  if(!lhn.type.equals("boolean")) { System.out.println(lhn.name + " must be boolean."); result = false; }
		  else if(!lhn.type.equals("boolean")) { System.out.println(rhn.name + " must be boolean."); result = false; }
		  else this.type = "boolean";
	  }else {
		  if(!lhn.type.equals("int")) { System.out.println(lhn.name + " must be int."); result = false; }
		  else if(!lhn.type.equals("int")) { System.out.println(rhn.name + " must be int."); result = false; }
		  else
			  if(this.name.equals("<")) this.type = "boolean";
			  else this.type = "int";
	  }
	  return result;
  }

}
/* JavaCC - OriginalChecksum=ea0b61aa43566c51566716de5ad918ef (do not edit this line) */