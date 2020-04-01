
public class Descriptor {
	
	String name;
	SimbolTable simbolTable;
	Descriptor content = null;
	
	public Descriptor(String nm, SimbolTable st){
		this.name = nm;
		this.simbolTable = st;
	}
	
	public Descriptor(String nm, SimbolTable st, Descriptor d){
		this.name = nm;
		this.simbolTable = st;
		this.content = d;
	}
	
	public String toString() {
		if(content != null)
			return name +  " -> " + content.toString();
		return name;
	}
	
}
