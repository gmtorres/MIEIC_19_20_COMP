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
	        SimpleNode root = myJmm.Program();
	        root.dump("");
	        


    }

	
}