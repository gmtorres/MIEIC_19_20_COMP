
public class IRBuilder {
	
	IRNode root = null;
	
	public IRBuilder(SimpleNode sn) {
		root = new IRNode(null);
		root.build(sn);
	}
	
	public void dump() {
		this.root.dump(" ");
	}
	
	
	
}
