
public class IRBuilder {
	
	IRNode root = null;
	
	public IRBuilder(SimpleNode sn) {
		root = new IRNode(null);
		root.build(sn);
		root.setRegisters();
	}
	
	public void dump() {
		this.root.dump(" ");
	}
	
	public void createJasmin() {
		Jasmin j = new Jasmin(this.root);
	}
	
	
	
}
