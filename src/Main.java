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
	        	System.out.println("There are semantic errors.");
	        }

	        root.printTables();
	        
	        


    }

	
}