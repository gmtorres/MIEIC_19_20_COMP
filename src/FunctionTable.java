import java.util.HashMap;

public class FunctionTable {

    HashMap<String,Function> functions;

    FunctionTable() {
        functions = new HashMap<String,Function>();
    }

    boolean isFunctionHere(String name) {
		return functions.get(name) != null;
    }
    
    void addFunction(Function f) {
        functions.put(f.getName(), f);
    }

    Function getFunction(String name) {
        if (isFunctionHere(name)) {
            return this.functions.get(name);
        }
        else return null;
    }

    void printTable() {
		for (HashMap.Entry<String, Function> entry : functions.entrySet()) {
		    Function f = entry.getValue();

		    System.out.println ("Function: " + f.toString());
		}
		
	}
}