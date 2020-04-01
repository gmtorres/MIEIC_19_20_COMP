import java.util.HashMap;

public class FunctionTable {

    HashMap<String,Function> functions;
    String parentClass;

    FunctionTable() {
        this.functions = new HashMap<String,Function>();
        this.parentClass = "";
    }

    void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    boolean isFunctionHere(String name) {
		return functions.get(name) != null;
    }
    
    boolean addFunction(String return_type, String name, SimbolTable st) {
        //TODO: Questao das 2 funcoes com mesmo nome
        Function f = new Function(return_type, name, st);
        functions.put(name, f);
        return true;
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