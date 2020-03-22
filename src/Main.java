import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class Main {
	
	public static int max_errors = 10;

    public static void main(String args[]) throws ParseException {   

        	Calculator myJmm = new Calculator(System.in);     
	        SimpleNode root = myJmm.Program();
	        root.dump("");

    }

	
}