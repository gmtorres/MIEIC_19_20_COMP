/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 6.1 */
/* JavaCCOptions:MULTI=false,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */


public
class SimpleNode implements Node {

  protected Node parent;
  protected Node[] children;
  protected int id;
  protected Object value;
  protected Jmm parser;

  // added
  public Integer val = null;
  public boolean is_static = false;
  public String type = null;
  public String name = null;
  
  public int lineNo = 0;
  
  public SimbolTable simbolTable = null;
  public FunctionTable functionTable = null;
  public DescriptorTable descriptors = null;
  public boolean has_scope = false;
  
  //public DescriptorTable descriptorsTable = null;
    

  public SimpleNode(int i) {
    id = i;
  }

  public SimpleNode(Jmm p, int i) {
    this(i);
    parser = p;
  }

  public void jjtOpen() {
  }

  public void jjtClose() {
  }

  public void jjtSetParent(Node n) { parent = n; }
  public Node jjtGetParent() { return parent; }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

  public Node jjtGetChild(int i) {
    return children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }

  public void jjtSetValue(Object value) { this.value = value; }
  public Object jjtGetValue() { return value; }

  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() {
    return JmmTreeConstants.jjtNodeName[id];
  }
  public String toString(String prefix) { return prefix + toString(); }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) {
	  String str = toString(prefix);
	  //str+="  " + lineNo + "  ";
	if(this.type != null) str+= "    " + this.type;
	if(this.name != null) str +="    " + name;
	if(this.val != null) str += "    " + val;
	System.out.print(str + "\n");
	if (children != null) {
	  for (int i = 0; i < children.length; ++i) {
	    SimpleNode n = (SimpleNode)children[i];
	    if (n != null) {
	      n.dump(prefix + " ");
	    }
	  }
	}
  }

  public int getId() {
    return id;
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
	  
	  return result;
	  
  }
  
  public void printTables() {
	  
	  if(this.has_scope) {
		  String str = "I have scope:   " + toString();
		  if(this.parent != null) str+= "    my parent is:    " + ( (SimpleNode)this.parent ).toString();
		  System.out.println(str);
		  this.simbolTable.printTable();
	  }
	  
	  if(this.children != null) {
		  for(Node node : this.children) {
			  ((SimpleNode) node).printTables();
		  }
	  }
	  
  }
 
	public SimbolTable getSimbolTable() {
		return simbolTable;
	}
   
  
}

/* JavaCC - OriginalChecksum=d33fdb2b8063d5de3474649324d5d160 (do not edit this line) */
