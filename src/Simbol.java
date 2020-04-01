
public class Simbol {

	private Descriptor type; 
    private String identifier;
    private boolean isInitialized;
    
    Simbol(Descriptor tp , String id , boolean init){
    	this.type = tp;
    	this.identifier = id;
    	this.isInitialized = init;
    }
    
    public String toString() {
    	return type.toString() + " :  " + identifier; 
    }
    
    public Descriptor getType() {
    	return type;
    }
    
}
