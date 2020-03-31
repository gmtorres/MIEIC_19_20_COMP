
import java.util.HashMap;

public class SimbolTable {
	
	HashMap<String,Simbol> simbols;
	SimbolTable parent;
	
	SimbolTable(){
		simbols = new HashMap<String,Simbol>();
		parent = null;
	}
	
	void setParent(SimbolTable p) {
		this.parent = p;
	}
	
	boolean isSimbolHere(String id) {
		return simbols.get(id) != null;
	}
	
	boolean addSimbol(String tp , String id) {
		if(isSimbolHere(id))
			return false;
		simbols.put(id,new Simbol(tp,id,false));
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
	
	void printTable() {
		for (HashMap.Entry<String, Simbol> entry : simbols.entrySet()) {
		    String key = entry.getKey();
		    Simbol value = entry.getValue();

		    System.out.println ("Simbol: " + value.toString());
		}
		
	}
	
}	
