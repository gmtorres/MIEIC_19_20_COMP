import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.List;
import java.util.ArrayList;


public class Main {
	

    public static void main(String args[]) throws ParseException, SemanticException, RegisterAllocationException {   
    		
		/*String[] parts = args[0].split("\\.");
		if(!parts[1].equals("jmm")) {
			System.out.println("Can only parse jmm files");
			System.exit(0);
		}*/
    	String fileName = null;
    	Integer registers = null;
    	List<String> opt = new ArrayList<String>();
    	
		for(int i = 0; i < args.length;i++) {
			switch (args[i].charAt(0)) {
			case '-':
				switch(args[i].charAt(1)) {
				case 'r':
					registers = Integer.parseInt(args[i].substring(3,args[i].length()));
					break;
				default:
					opt.add(args[i].substring(1,args[i].length()));
					break;
				}
				break;
			default:
				if(fileName != null)
					return;
				fileName = args[i];
				break;
			}
		}
		
		if(fileName == null)
			return;

		System.out.println(fileName);
		System.out.println(opt);
		System.out.println(registers);
        //System.out.println("TESTE:");
        InputStream initialStream = null;
        try {
            initialStream = new FileInputStream(new File(fileName));
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

        IRBuilder ir = new IRBuilder(root,registers,opt);
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
       
        Jasmin j = new Jasmin(ir.root,file,false,opt);


    }

	
}