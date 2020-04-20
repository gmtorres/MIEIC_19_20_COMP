import java.lang.reflect.Array; 
import java.io.PrintStream;

public class Jasmin {

	  IRNode root;
	  
	  PrintStream os;
	  
	  public Jasmin(IRNode r,PrintStream ps) {
		  this.root=r;
		  this.os = ps;
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
		      
		    case "int[]":
		    	return "[I";
		      
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
		  	case "invoke":
		  		printInvoke(r);
		  		break;
		  	//TER CASE VARIAVEL .FIELD
		  	case "st":
		  		printStore(r);
		  		break;
		  	case "ldc":
		  	case "ldl":
		  	case "ldp":
		  	/*case "ldg":*/
		  		printLoad(r);
		  		break;
		  	case "+":
		  	case "*":
		  	case "/":
		  	case "-":
		  		printOperation(r);
		  		break;
		  	case "return":
		  		printReturn(r);
		  		break;
		  }	
		  
		 
	  }
	  
	  
	  private void printOperation(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  
		  if(node.getInst().equals("+")) {
			  os.println("iadd");
		  }
		  if(node.getInst().equals("*")) {
			  os.println("imul");
		  }
		  if(node.getInst().equals("/")) {
			  os.println("idiv");
		  }
		  if(node.getInst().equals("-")) {
			  os.println("isub");
		  }
		  
	  }
	  
	  private void printLoad(IRNode node) {
		  if(node.getInst().equals("ldc")) {
			  os.println("bipush " + node.children[0].getInst());
		  }
		  if(node.getInst().equals("ldl")) {
			  os.println("iload " + node.children[0].local_var);
		  }
		  if(node.getInst().equals("ldp")) {
			  os.println("iload " + node.children[0].local_var);
		  }
	  }
	  
	  private void printStore(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  IRNode lhn = node.children[0];
		  os.println("istore " + lhn.local_var);
		  
	  }
	  
	  private void printReturn(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  if(node.getChildren().length == 0)
			  os.println("return");
		  else
			  os.println("ireturn");
		  
	  }
	  
	  private void printClass(IRNode root) {
		  IRNode r = root.getChildren()[0];
		  String toPrint = ".class public ";
		  toPrint += r.getChildren()[0].getInst();
		  os.println(toPrint);
		  
		  toPrint = ".super ";
		  if (r.getChildren()[1].getInst().equals("Object")) {
			  toPrint += "java/lang/Object";
		  }
		  else {
			  toPrint += r.getChildren()[1].getInst();
		  }
		  os.println(toPrint);
		  os.print("\n");
		  
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
		  

		  for (int i= 0; i < ((r.getChildren()[2]).getChildren().length); i++) {
			  toPrint += retType(r.getChildren()[2].getChildren()[i].getInst());
			  if(i != r.getChildren()[2].getChildren().length - 1)
				  toPrint +=", ";
		  }

		  
		  toPrint += ")";
		  toPrint += retType(((r.getChildren()[1]).getChildren()[0]).getInst());
		  
		  os.println(toPrint);
		  
		  toPrint = "\t.limit stack " + r.op_stack; //TODO: implementar na IR
		  os.println(toPrint);
		  
		  toPrint = "\t.limit locals ";
		  
		  toPrint += r.locals_stack;


		  os.println(toPrint);
		  os.print("\n");
		  
		  for (int k = 0; k < (r.getChildren()[4]).getChildren().length; k++) {
			  printJasmin((r.getChildren()[4]).getChildren()[k]);
		  }
		  
		  
		  if(((r.getChildren()[1]).getChildren()[0]).getInst().equals("void")) {
			  toPrint = "\treturn";
			  os.println(toPrint);
		  }
		  
		  toPrint = ".end method";
		  
		  os.println(toPrint);
		  
	  }
	  
	  
	  private void printInvoke(IRNode r) {
		  String toPrint = "\tinvoke"; //TODO: verificar static ou virtual
		  os.println(toPrint);
		  

		  toPrint = "\t\t" + r.getChildren()[0].getInst() + "." + r.getChildren()[1].getInst() + "()"; //TODO: colocar params
		  os.println(toPrint);

		  toPrint = "\t\t" + r.getChildren()[2].getInst() + "." + r.getChildren()[3].getInst() + "(";
		  
		  for (int i= 0; i < ((r.getChildren()[0]).getChildren().length); i++) {
			  toPrint += retType(r.getChildren()[0].getChildren()[i].getInst());
			  if(i != r.getChildren()[0].getChildren().length - 1)
				  toPrint +=", ";
		  }			  
		  toPrint += ")" + retType((r.getChildren()[1]).getChildren()[0].getInst()); //TODO: colocar params
		  os.println(toPrint);

		  
		  
	  }
	 /*
	  public String printJasmin(IRNode node) {
		    String func = "";

	          if (root.toString().equals("METHOD")) {
	        	 jasmin += ".method " + root.name + "(" + argsFun(root)+ ")"+ type(root.type)  +  "\n";
	          }
	          
              if(root.toString().equals("EXPRESSION")) {
            	  if(root.parent.toString().equals("RETURN_EXPRESSION")) {
            		  os.println("RETURN EX\n");
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
