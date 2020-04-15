
public class IRBuilder {
	
	IRNode root = null;
	
	public IRBuilder(SimpleNode sn) {
		root = new IRNode();
		root.build(sn);
	}
	
	public void dump() {
		this.root.dump(" ");
	}
	
	
	
}
