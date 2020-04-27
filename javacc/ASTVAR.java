/* Generated By:JJTree: Do not edit this line. ASTVAR.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTVAR extends SimpleNode {
  public ASTVAR(int id) {
    super(id);
  }

  public ASTVAR(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean createTable() {
	  
	  if(this.parent != null) {
		 this.functionTable = ((SimpleNode)this.parent).functionTable;
		 this.descriptors = ((SimpleNode)this.parent).descriptors;
	  }
	  
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
	  
	  SimpleNode lhn  = (SimpleNode) this.children[0]; // IDENTIFIER to assing
	  if( lhn.toString().equals("IDENTIFIER") && 
			  this.simbolTable.isSimbolKnown(lhn.name) == false) {
		  System.out.println("Error on line " + lhn.lineNo + ": Simbol " + lhn.name + " is not known.");
		  result = false;
	  }
	   
	  if(this.children != null) {
		  for(Node node : this.children) {
			  boolean r = ((SimpleNode) node).createTable();
			  result = result && r;
		  }
	  }

	  return result;
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
	  if(lhn.toString().equals("IDENTIFIER")) {
		  //System.out.println(lhn.name);
		  if(this.simbolTable.isSimbolKnown(lhn.name) == false){
			  System.out.println("Error on line " + this.lineNo + ": Simbol " + lhn.name + " is not known.");
			  return false;
		  }else {
			  lhn.type = this.simbolTable.getSimbol(lhn.name).getType().getName();
		  }
		  this.type = lhn.type;
	  }else return false;
	  
	  if(this.jjtGetNumChildren() == 2) {
		  SimpleNode rhn  = (SimpleNode) this.children[1];
		  if(!rhn.type.equals("int")) {
			  System.out.println("Error on line " + rhn.lineNo + ": Index must be int");
			  return false;
		  }
		  
		  Integer size = this.simbolTable.getSimbol(lhn.name).size;
		  if(((SimpleNode)rhn.children[0]).toString().equals("INTEGERLITERAL")) {
			  int index = ((SimpleNode)rhn.children[0]).val;
			  if(size != null && index >= size) {
				  System.out.println("Warning on line " + rhn.lineNo + ": Size is greater than array size.");
			  }
		  }
		  
		  Descriptor d = this.descriptors.getDescriptor(lhn.type);
		  
		  if(d == null) return false;
		  if(d.content == null) {
			  //this.type = d.getName(); //error
			  System.out.println("Error on line " + lhn.lineNo + ": Simbol " + lhn.name + " must be an array.");
			  return false;
		  }
		  else this.type = d.content.getName();
	  }
	  
	  //System.out.println("Type: " + this.type);
	  
	  return result;
  }
  
  
  
  
  
}
/* JavaCC - OriginalChecksum=3d5ba3dbf45f9bb899864a825571601d (do not edit this line) */
