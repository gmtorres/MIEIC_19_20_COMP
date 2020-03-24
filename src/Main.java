import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class Main {
	

    public static void main(String args[]) {   

		
            System.out.println("TESTE:");
	        InputStream initialStream = null;
	        try {
	            initialStream = new FileInputStream(new File(args[0]));
	        } catch(Exception e){
	            System.out.println("Could not found file");
	            System.exit(0);
	        }
	        
	        try {
		        Jmm myJmm = new Jmm(initialStream);   
		        {
		        	myJmm.failed = false;
		        	myJmm.max_errors = 10;
		        }
		        SimpleNode root = myJmm.Program();
		        root.dump("");
		        
		        if(myJmm.failed == true) {
		        	throw new ParseException();
		        }
		        
	        }catch(ParseException e) {
	        	throw new RuntimeException("Error during parsing");
	        }
	        
	        
	        


    }

	
}