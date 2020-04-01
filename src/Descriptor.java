
public class Descriptor {
	
	String name;
	SimbolTable simbolTable;
	Descriptor content = null;
	
	Descriptor(String nm, SimbolTable st){
		this.name = nm;
		this.simbolTable = st;
	}
	
	Descriptor(String nm, SimbolTable st, Descriptor d){
		this.name = nm;
		this.simbolTable = st;
		this.content = d;
	}
	
}
