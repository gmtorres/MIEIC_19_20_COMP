
import java.util.HashMap;

public class SimbolTable {
	
	HashMap<String,Simbol> simbols;
	SimbolTable parent;
	private boolean global = false;
	private boolean param = false;
	private boolean local = false;
	
	SimbolTable(){
		simbols = new HashMap<String,Simbol>();
		parent = null;
	}
	
	public void setGlobal(boolean b) {
		this.global = b;
	}
	
	public boolean getGlobal() {
		return this.global;
	}
	
	public void setParam(boolean b) {
		this.param = b;
	}
	
	public boolean getParam() {
		return this.param;
	}
	
	public void setLocal(boolean b) {
		this.local = b;
	}
	
	public boolean getLocal() {
		return this.local;
	}
	
	public String getScope() {
		if(this.getLocal()) return "local";
		else if(this.getParam()) return "param";
		else if(this.getGlobal()) return "global";
		return "";
	}
	
	void setParent(SimbolTable p) {
		this.parent = p;
	}
	
	boolean isSimbolHere(String id) {
		return simbols.get(id) != null;
	}
	
	boolean addSimbol(Descriptor tp , String id) {
		if(isSimbolHere(id))
			return false;
		simbols.put(id,new Simbol(tp,id,false));
		return true;
	}
	boolean addSimbol(Descriptor tp , String id, boolean init) {
		if(isSimbolHere(id))
			return false;
		simbols.put(id,new Simbol(tp,id,init));
		return true;
	}
	
	boolean isSimbolKnown(String id) {
		if(isSimbolHere(id))
			return true;
		else {
			if(this.parent == null)
				return false;
			else return this.parent.isSimbolKnown(id);
		}
	}
	
	Simbol getSimbol(String id) {
		if(isSimbolHere(id))
			return this.simbols.get(id);
		else {
			if(this.parent == null)
				return null;
			else return this.parent.getSimbol(id);
		}
	}
	
	public String getScope (String id) {
		if(isSimbolHere(id)) {
			if(this.getLocal()) return "local";
			else if(this.getParam()) return "param";
			else if(this.getGlobal()) return "global";
			return "";
		}else {
			if(this.parent == null)
				return "";
			else return this.parent.getScope(id);
		}
	}
	
	void printTable() {
		System.out.println("SimbolTable");
		for (HashMap.Entry<String, Simbol> entry : simbols.entrySet()) {
		    String key = entry.getKey();
		    Simbol value = entry.getValue();

		    System.out.println ("Simbol: " + value.toString());
		}
		
	}
	
}	
