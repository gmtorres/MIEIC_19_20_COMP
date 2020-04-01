import java.util.HashMap;

public class Function {

    String return_type;
    String name;
    SimbolTable vars;
    
    Function(String retType, String name, SimbolTable vars) {
        this.return_type = retType;
        this.name = name;
        this.vars = vars;
    }

    public String toString() {
    	return return_type + name; 
    }

    public String getType() {
    	return return_type;
    }

    public String getName() {
    	return name;
    }

}