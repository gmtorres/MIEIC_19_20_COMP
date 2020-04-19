import java.lang.reflect.Array; 

public class Jasmin {

	  IRNode root;
	  
	  public Jasmin(IRNode r) {
		  this.root=r;
		  printClass(this.root);
	  }
	  
	  
	  private String retType(String type) {
		    switch (type) {
		    case "void":
		        return "V";
		        
		    case "int":
		        return "I";
		        
		    case "boolean":
		      return "B";
		      
		    default:
		        return "";
		    }
	  }
	  
	  
	  private void printJasmin(IRNode r) {
		  String toPrint = "";
		  
		  switch(r.getInst()) {
		  	case "method":
		  		printMethod(r);
		  		break;
		  	//TER CASE VARIAVEL .FIELD
		  }	
		  
		 
	  }
	  
	  private void printClass(IRNode root) {
		  IRNode r = root.getChildren()[0];
		  String toPrint = ".class public ";
		  toPrint += r.getChildren()[0].getInst();
		  System.out.println(toPrint);
		  
		  toPrint = ".super ";
		  if (r.getChildren()[1].getInst().equals("Object")) {
			  toPrint += "java/lang/Object";
		  }
		  else {
			  toPrint += r.getChildren()[1].getInst();
		  }
		  System.out.println(toPrint);
		  System.out.print("\n");
		  
		  for(int i = 2; i < r.getChildren().length; i++) {
			  printJasmin(r.getChildren()[i]);
		  }
		  
	  }
	  
	  private void printMethod(IRNode r) {
		  String toPrint = ".method ";
		  toPrint += ((r.getChildren()[0]).getChildren()[0]).getInst();
		  toPrint += " ";
		  toPrint += ((r.getChildren()[1]).getChildren()[1]).getInst();
		  toPrint += "(";
		  
		  if((r.getChildren()[2]).getChildren() != null) {
			  for (int i= 0; i < ((r.getChildren()[2]).getChildren().length); i++) {
				  toPrint += retType(r.getChildren()[2].getChildren()[i].getInst());
				  if(i != r.getChildren()[2].getChildren().length - 1)
					  toPrint +=", ";
			  }
		  }
		  
		  toPrint += ")";
		  toPrint += retType(((r.getChildren()[1]).getChildren()[0]).getInst());
		  
		  System.out.println(toPrint);
		  
		  toPrint = "\t.limit stack 10"; //TODO: implementar na IR
		  System.out.println(toPrint);
		  
		  toPrint = "\t.limit locals ";
		  
		  if ((r.getChildren()[3]).getChildren() != null) {
			  toPrint += (r.getChildren()[3]).getChildren().length;
		  }
		  else toPrint += 0;
		  
		  System.out.println(toPrint);
		  
		    
	  }
	  
	 /*
	  public String printJasmin(IRNode node) {
		    String func = "";

	          if (root.toString().equals("METHOD")) {
	        	 jasmin += ".method " + root.name + "(" + argsFun(root)+ ")"+ type(root.type)  +  "\n";
	          }
	          
              if(root.toString().equals("EXPRESSION")) {
            	  if(root.parent.toString().equals("RETURN_EXPRESSION")) {
            		  System.out.println("RETURN EX\n");
            	  jasmin += retType(root.type)  + "return" + "\n\n";
              }}

	              if (root.children != null) {
		              for (Node child : root.children) {
		                SimpleNode sN = (SimpleNode) child;
		                printJasmin(sN);
		              }
		          }
		    return "JASMIN: " + jasmin;
}
	  public String argsFun(SimpleNode root) {
		  for (int i = 0; i < root.children.length;i++) {
			  if(root.jjtGetChild(i).toString().equals("ARGUMENT")) {
				  SimpleNode sN = (SimpleNode) root.jjtGetChild(i);
				  return type(sN.type);
			  }
	          }
    	  return "\n";

	          }
        
	  private String type(String type) {
		    switch (type) {
		    case "boolean":
		      return "Z";

		    case "void":
		      return "V";

		    case "int":
		      return "I";

		    case "string":
		      return "S";

		    default:
		      return "L" + type + ";";
		    }
		  }

	  */
}
