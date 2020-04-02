
public class Descriptor {
	
	String name;
	SimbolTable simbolTable;
	Descriptor content = null;
	Descriptor extend = null;
	
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
		if(extend != null)
			return name +  " ext " + extend.toString();
		return name;
	}
	
	public boolean doesExtends(String ext) {
		if(extend == null)
			return false;
		if(extend.name.equals(ext))
			return true;
		else return extend.doesExtends(ext);
	}
	
	public String getName() {
		return name;
	}
	
}
