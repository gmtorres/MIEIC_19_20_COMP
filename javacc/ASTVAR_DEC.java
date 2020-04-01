/* Generated By:JJTree: Do not edit this line. ASTVAR_DEC.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTVAR_DEC extends SimpleNode {
  public ASTVAR_DEC(int id) {
    super(id);
  }

  public ASTVAR_DEC(Jmm p, int id) {
    super(p, id);
  }
  
  public boolean createTable() {
	  
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
	  
	  if(this.simbolTable.addSimbol(this.type,this.name) == false) {
		  System.out.println("Duplicate simbol " + this.name);
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

}
/* JavaCC - OriginalChecksum=32167ccc8b629d15f1d42b50fb630bc4 (do not edit this line) */
