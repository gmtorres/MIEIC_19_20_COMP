import java.util.HashMap;

public class Function {

    String return_type;
    String name;
    String object;
    SimbolTable vars;
    boolean isStatic;
    
    Function(String retType,String obj, String name, SimbolTable vars) {
        this.return_type = retType;
        this.name = name;
        this.vars = vars;
        this.object = obj;
        this.isStatic = false;
    }

    Function(String retType,String obj, String name, SimbolTable vars, boolean staticFlag) {
        this.return_type = retType;
        this.name = name;
        this.vars = vars;
        this.object = obj;
        this.isStatic = staticFlag;
    }

    public String toString() {
    	return return_type + "   " + object + "   " +   name; 
    }

    public String getType() {
    	return return_type;
    }

    public String getName() {
    	return name;
    }

}