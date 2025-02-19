import java.util.ArrayList;
import java.util.List;

public class Descriptor {
	
	String name;
	SimbolTable simbolTable;
	Descriptor content = null;
	Descriptor extend = null;
	List<List<Descriptor>> params = new ArrayList<>();
	
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
	
	public ArrayList<String> getAllTypes(){
		if(extend == null) {
			ArrayList<String> l = new ArrayList<String>();
			l.add(this.name);
			return l;
		}
		ArrayList<String> l = extend.getAllTypes();
		l.add(0,this.name);
		return l;
	}
	
	public List<List<Descriptor>> getParams(){
		return this.params;
	}
	
	public boolean addParams(List<Descriptor> p) {
		for(List<Descriptor> d : this.params) {
			if(d.size() != p.size())
				continue;
			int i = 0;
			for(; i < d.size() && i < p.size() ;i++) {
				if(!d.get(i).name.equals(p.get(i).name)) {
					break;
				}
			}
			if(i == p.size() && i == d.size())
				return false;
		}
		this.params.add(p);
		return true;
	}
	
	
}
