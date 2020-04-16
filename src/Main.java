import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class Main {
	

    public static void main(String args[]) throws ParseException {   

		
            System.out.println("TESTE:");
	        InputStream initialStream = null;
	        try {
	            initialStream = new FileInputStream(new File(args[0]));
	        } catch(Exception e){
	            System.out.println("Could not found file");
	            System.exit(0);
	        }

	        Jmm myJmm = new Jmm(initialStream);   

        	myJmm.failed = false;
        	myJmm.max_errors = 10;

        	ASTProgram root = myJmm.Program();
	        root.dump("");
	        
	        if(myJmm.failed == true) {
	        	throw new ParseException();
	        }

	        if(root.createTable() == false) {
	        	System.out.println("There are semantic errors while creating table.");
	        }
	        
        	System.out.println("\n\n");
        	System.out.println("\n--SEMANTICS--\n");
	        
	        if(root.doSemanticAnalysis(new StringBuilder("")) == false) {
	        	System.out.println("There are semantic errors while analysing.");
	        }

        	System.out.println("\n--SYMBOL TABLE--\n");
	        root.printTables();
        	System.out.println("\n");

        	System.out.println("\n--DESCRIPTORS--\n");
	        root.descriptors.printTable();
        	System.out.println("\n");
        	
            root.writeJasminFile(args[0]);
            
            Jasmin jasmin = new Jasmin(root.getSimbolTable());
        	System.out.println(jasmin.printJasmin(root));
            
    }

	
}