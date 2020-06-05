
public class Simbol {

	private Descriptor type;
	private Descriptor assignType = null;
    private String identifier;
    public boolean isInitialized = false;
    public boolean ifInitialized = false;
    public boolean elseInitialized = false;
    public boolean condInitialized = false;
    public int init = 0;
    
    public Integer local_var = null;
    public Integer size = null;
    public Integer value = null;
    
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
    
    public void setAssignType(Descriptor d) {
    	this.assignType = d;
    }
    
    public Descriptor getAssignType() {
    	return assignType;
    }
    
    public String getName() {
    	return identifier;
    }
    
}
