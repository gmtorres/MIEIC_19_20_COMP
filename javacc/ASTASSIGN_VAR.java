/* Generated By:JJTree: Do not edit this line. ASTASSIGN_VAR.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTASSIGN_VAR extends SimpleNode {
  public ASTASSIGN_VAR(int id) {
    super(id);
  }

  public ASTASSIGN_VAR(Jmm p, int id) {
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
	  
	  SimpleNode lhn  = (SimpleNode) this.children[0];
	  SimpleNode rhn  = (SimpleNode) this.children[1];
	  this.lineNo = rhn.lineNo;
	  
	  if(!lhn.type.equals(rhn.type)) {
		  Descriptor d = this.descriptors.getDescriptor(rhn.type);
		  if(d == null || !d.doesExtends(lhn.type)) {
			  if(lhn.type.equals("int") && rhn.type.equals("float")) {
				  System.out.println("Warning on line " + this.lineNo + ", column " + this.columnNo + ": Incmpatible types, possible data loss from :  " + lhn.type + "  to  " + rhn.type);
				  this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name).setAssignType(this.descriptors.getDescriptor("int"));
			  }else if(lhn.type.equals("float") && rhn.type.equals("int")) {
				  this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name).setAssignType(this.descriptors.getDescriptor("float"));
			  }
			  else{
				  System.out.println("Error on line " + this.lineNo + ", column " + this.columnNo + ": Assigning imcompatible type:  " + lhn.type + "  and  " + rhn.type);
				  this.decrementMaxErros();
				  return false;
			  }
		  }
		  else
			  this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name).setAssignType(d);
	  }else {
		  Descriptor d = this.descriptors.getDescriptor(rhn.type);
		  this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name).setAssignType(d);
	  }
	  
	  String context = info.toString().split(" ")[0];
	  if(context.equals("IF:") || context.equals("ELSE:")) {
		  Simbol s = this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name);
		  if(s.isInitialized == false) {
			  info.append(" " + ((SimpleNode) lhn.children[0]).name);
			  s.isInitialized = true;
		  }
	  }else
		  this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name).isInitialized = true;
	  
	  if(this.descriptors.getDescriptor(lhn.type).content != null) {
		  if(((SimpleNode) rhn.children[0]).val != null)
			  this.simbolTable.getSimbol(((SimpleNode) lhn.children[0]).name).size = ((SimpleNode) rhn.children[0]).val;
	  }
	  
	  return result;
  }


}
/* JavaCC - OriginalChecksum=77313bfccbff1d88a81394b17431258c (do not edit this line) */
