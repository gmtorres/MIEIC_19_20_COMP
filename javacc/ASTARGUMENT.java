/* Generated By:JJTree: Do not edit this line. ASTARGUMENT.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTARGUMENT extends SimpleNode {
  public ASTARGUMENT(int id) {
    super(id);
  }

  public ASTARGUMENT(Jmm p, int id) {
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
	  
	  Descriptor d = this.descriptors.getDescriptor(this.type);
	  
	  if(d == null) {
		  System.out.println("Error on line "+ this.lineNo + ", column " + this.columnNo + ": Could not find type " + this.type);
		  result = false; 
	  }else {
		  if(this.simbolTable.addSimbol(d,this.name,true) == false) {
			  System.out.println("Error on line " + this.lineNo + ", column " + this.columnNo + ": Duplicate simbol " + this.name);
			  result = false; 
		  }
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
/* JavaCC - OriginalChecksum=ff6552a7db43527a5d92da4c20d64e5e (do not edit this line) */
