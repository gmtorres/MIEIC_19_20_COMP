
import java.util.HashMap;

public class DescriptorTable {
	HashMap<String,Descriptor> descriptors;
	
	DescriptorTable(){
		descriptors = new HashMap<String,Descriptor>();
	}
	
	boolean isSimbolHere(String id) {
		return descriptors.get(id) != null;
	}
	
	Descriptor getDescriptor(String id) {
		return descriptors.get(id);
	}
	
	boolean addDescriptor(String name , SimbolTable st) {
		if(isSimbolHere(name))
			return false;
		descriptors.put(name,new Descriptor(name,st));
		return true;
	}
	boolean addDescriptor(String id,String name , SimbolTable st) {
		if(isSimbolHere(name))
			return false;
		descriptors.put(id,new Descriptor(name,st));
		return true;
	}
	
	
	boolean addDescriptor(String name , SimbolTable st, Descriptor cont) {
		if(isSimbolHere(name))
			return false;
		descriptors.put(name,new Descriptor(name,st,cont));
		return true;
	}
	
	void printTable() {
    	System.out.println("Descriptors Table");
		for (HashMap.Entry<String, Descriptor> entry : descriptors.entrySet()) {
		    Descriptor d = entry.getValue();

		    System.out.println ("Descriptor: " + d.toString());
		}
		
	}
	
}
