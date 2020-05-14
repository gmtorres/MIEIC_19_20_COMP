import java.util.HashMap;
import java.util.List;

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

    boolean isFunctionHere(String object ,String name, int no) {
		return functions.get(object +"."+ name+"@"+no) != null;
    }
    boolean isFunctionHere(String object ,String name, String params) {
		return functions.get(object +"."+ name+"@"+params) != null;
    }
    /*
    boolean addFunction(String return_type, String object, String name, SimbolTable st) {
        //TODO: Questao das 2 funcoes com mesmo nome
        Function f = new Function(return_type, object, name, st);
        functions.put(object +"."+ name, f);
        return true;
    }*/
    
    String getParamsString(List<Descriptor> list) {
    	String str="";
    	for(int i = 0;i<list.size();i++)
    		str+=list.get(i).name+",";
    	return str;
    }
    
    boolean addFunction(String return_type, String object, String name, SimbolTable st, boolean stFlag, List<Descriptor> list) {
        //TODO: Questao das 2 funcoes com mesmo nome
        Function f = new Function(return_type, object, name, st, stFlag, list);
        if(this.isFunctionHere(object, name, list.size()))
        	return false;
        //functions.put(object +"."+ name + "@"+list.size(), f);
        System.out.println(object +"."+ name + "@"+getParamsString(list));
        functions.put(object +"."+ name + "@"+getParamsString(list), f);
        return true;
    }
/*
    boolean addFunction(String return_type, String object, String name, SimbolTable st, boolean stFlag) {
        //TODO: Questao das 2 funcoes com mesmo nome
        Function f = new Function(return_type, object, name, st, stFlag);
        functions.put(object +"."+ name, f);
        return true;
    }
*/
    Function getFunction(String object ,String name, int no) {
        if (isFunctionHere(object,name,no)) {
            return this.functions.get(object +"."+ name+"@"+no);
        }
        else return null;
    }
    Function getFunction(String object ,String name, String params) {
        if (isFunctionHere(object,name,params)) {
            return this.functions.get(object +"."+ name+"@"+params);
        }
        else return null;
    }

    void printTable() {
    	System.out.println("Function Table");
		for (HashMap.Entry<String, Function> entry : functions.entrySet()) {
		    Function f = entry.getValue();

		    System.out.println ("Function: " + f.toString());
		}
		
	}
}