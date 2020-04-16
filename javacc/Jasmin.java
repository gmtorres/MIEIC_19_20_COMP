
public class Jasmin {
    String jasmin = "";

	  SimbolTable simbolTable;
	  String fn;
	  
	  public Jasmin(SimbolTable st) {
		  this.simbolTable=st;
	  }
	 private String getType(String type) {
		    switch (type) {
		    case "void":
		        return "V";
		        
		    case "int":
		        return "I";
		        
		    case "boolean":
		      return "Z";
		      
		    case "int[]":
		      return "[I";

		    default:
		        return "";
		    }
		  }
	 
	  public String printJasmin(SimpleNode root) {
		    String func = "";
//
	          //    System.out.println(root.parent + " " + root.toString() + "\n");

	          if (root.parent != null && root.toString().equals("IDENTIFIER")) {
	        	  String parent = root.parent.toString();
	              if (parent.equals("FUNCTION")) {
	            	  System.out.println("FUN: " + root.name + "\n");
	              }
	              else if (parent.equals("ARGUMENT")) {
	            	  System.out.println(root.name + "ARG\n");
	              }
	              }else if (root.toString().equals("ARGUMENT")) {
	            	  jasmin += "("; 
	              }
	          System.out.println(jasmin);

	          if (root.children != null) {
	              for (Node child : root.children) {
	                SimpleNode sN = (SimpleNode) child;
	                jasmin += printJasmin(sN);
	              }
	          }else {System.out.println("NO CHILDS \n");}
	          if (root.toString().equals("ARGUMENT")){
	        	  jasmin +=  ")" + type(root.type);
	          }
              
	          /*if (root.toString().equals("PARAMS")) {
	        	  jasmin += ")type";
	            */

		    return jasmin;
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

	  
}
