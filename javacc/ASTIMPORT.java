import java.util.ArrayList;
import java.util.List;


/* Generated By:JJTree: Do not edit this line. ASTIMPORT.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIMPORT extends SimpleNode {
  public ASTIMPORT(int id) {
    super(id);
  }

  public ASTIMPORT(Jmm p, int id) {
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
	  
	  
	  if(this.jjtGetNumChildren() == 1) {
		  	this.descriptors.addDescriptor( ((SimpleNode) this.children[0]).name, this.simbolTable );
	  }else if(this.jjtGetNumChildren() == 2) {
		  SimpleNode rhs = (SimpleNode) this.children[1];
		  SimpleNode params;
		  if(rhs.toString().equals("METHOD_PROT"))
			  params = (SimpleNode)rhs.children[0];
		  else params = rhs;

		  List<Descriptor> listDesc = new ArrayList<>();

		  int childrenNo = params.jjtGetNumChildren();

		  for (int i = 0; i < childrenNo; i++) {
			  Descriptor d = this.descriptors.getDescriptor(((SimpleNode) params.children[i]).type);
			  listDesc.add(d);
			  if(d == null) {
				  System.out.println("Could not find type " + this.type);
				  result = false; 
			  }else
				  this.simbolTable.addSimbol(d, String.valueOf("a" + i));
		  }
		  if(rhs.toString().equals("METHOD_PROT"))
			  this.functionTable.addFunction(rhs.type, ((SimpleNode) this.children[0]).name, rhs.name, this.simbolTable, this.is_static, listDesc);
		  else {
			  this.descriptors.addDescriptor( ((SimpleNode) this.children[0]).name, this.simbolTable );
			  Descriptor d = this.descriptors.getDescriptor(((SimpleNode) this.children[0]).name);
			  d.setParams(listDesc);
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
/* JavaCC - OriginalChecksum=e2397454924d9f234df48d132afaab47 (do not edit this line) */
