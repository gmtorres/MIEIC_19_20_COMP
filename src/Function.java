import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Function {

    String return_type;
    String name;
    String object;
    SimbolTable vars;
    boolean isStatic;
    List<Descriptor> descriptors;
    
    Function(String retType,String obj, String name, SimbolTable vars) {
        this.return_type = retType;
        this.name = name;
        this.vars = vars;
        this.object = obj;
        this.isStatic = false;
        this.descriptors = new ArrayList<>();
    }

    Function(String retType,String obj, String name, SimbolTable vars, boolean staticFlag) {
        this.return_type = retType;
        this.name = name;
        this.vars = vars;
        this.object = obj;
        this.isStatic = staticFlag;
        this.descriptors = new ArrayList<>();
    }

    Function(String retType,String obj, String name, SimbolTable vars, boolean staticFlag, List<Descriptor> desciptorsList) {
        this.return_type = retType;
        this.name = name;
        this.vars = vars;
        this.object = obj;
        this.isStatic = staticFlag;
        this.descriptors = desciptorsList;
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

    public List<Descriptor> getDescriptors() {
    	return descriptors;
    }

}