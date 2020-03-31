
public class Simbol {

	private String type; 
    private String identifier;
    private boolean isInitialized;
    
    Simbol(String tp , String id , boolean init){
    	this.type = tp;
    	this.identifier = id;
    	this.isInitialized = init;
    }
    
    public String toString() {
    	return type + " :  " + identifier; 
    }
    
    public String getType() {
    	return type;
    }
    
}
