import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.File;

public class Main {
	

    public static void main(String args[]) throws ParseException, SemanticException {   
    		
    		String[] parts = args[0].split("\\.");
    		if(!parts[1].equals("jmm")) {
    			System.out.println("Can only parse jmm files");
    			System.exit(0);
    		}

		
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
	        //root.dump("");

	        if(myJmm.failed == true) {
	        	throw new ParseException();
	        }
	        SimpleNode.max_semantic_errors = 10;
	        try {
	        	if(root.createTable() == false) {
		        	throw new SemanticException("There are semantic errors while creating table.");
		        }
		        if(root.doSemanticAnalysis(new StringBuilder("")) == false) {
		        	throw new SemanticException("There are semantic errors while analysing.");
		        }
	        }catch(SemanticException e) {
	        	System.out.println(e);
	        	throw e;
	        }

	        //root.printTables();
	        //root.descriptors.printTable();

	        IRBuilder ir = new IRBuilder(root);
	        System.out.println("\n--------------*****************-----------------	\n\n");
	        ir.dump();
	        
	        System.out.println("\n--------------*****************-----------------	\n");
	        
	        PrintStream file;
	        try{
	        	//file = new PrintStream(new File(parts[0] + ".j"));
	        	file = new PrintStream(new File("jasmin.j"));
	        }catch(Exception e) {
	        	System.out.println(e);
	        	return;
	        }
	        
	        Jasmin j = new Jasmin(ir.root,file,true);


    }

	
}