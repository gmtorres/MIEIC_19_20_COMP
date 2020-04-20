import java.lang.reflect.Array; 
import java.io.PrintStream;

public class Jasmin {

	  IRNode root;
	  
	  PrintStream os;
	  
	  Integer loop_count = 0;
	  
	  String current_loop;
	  
	  boolean in_condition = false;
	  boolean not = false;
	  Integer not_count = 0;
	  
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
		    case "String":
		    	return "Ljava/lang/String";
		    case "String[]":
		    	return "[Ljava/lang/String";
		    default:
		        return "";
		    }
	  }
	  
	  private String getType(String type) {
		  switch (type) {  
		    case "boolean":
		    	//return "b";
		    case "int":
		        return "i";
		    case "int[]":
		        return "a";
		    default:
		        return "";
		    }
	  }
	  
	  
	  private void printJasmin(IRNode r) {
		  String toPrint = "";
		  
		  switch(r.getInst()) {
		  	case "method":
		  		os.print("\n");
		  		printMethod(r);
		  		break;
		  	case "invoke":
		  		printInvoke(r);
		  		break;
		  	//TER CASE VARIAVEL .FIELD
		  	case "st":
		  		printStore(r);
		  		break;
		  	case "sta":
		  		printStoreArray(r);
		  		break;
		  	case "ldc":
		  	case "ldl":
		  	case "ldp":
		  	/*case "ldg":*/
		  		printLoad(r);
		  		break;
		  	case "lda":
		  		printLoadArray(r);
		  		break;
		  	case "+":
		  	case "*":
		  	case "/":
		  	case "-":
		  		printOperation(r);
		  		break;
		  	case "<":
		  		printLessThan(r);
		  		break;
		  	case "&&":
		  		printAnd(r);
		  		break;
		  	case "new_int_arr":
		  		printNewIntArr(r);
		  		break;
		  	case "while":
		  		printWhile(r);
		  		break;
		  	case "not":
		  		printNot(r);
		  		break;
		  	case "fields":
		  		os.print("\n");
		  		printFields(r);
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
		  String prefix = "";
		  if(node.getInst().equals("ldc")) {
			  Integer value = Integer.parseInt(node.children[0].getInst());
			  if(value < 4)
				  os.println("iconst_"+value);
			  else
				  os.println("bipush " + value);
		  }else if(node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
			  Integer local_var = node.children[0].local_var;
			  if(local_var < 4)
				  os.println( this.getType(node.type) + "load_" + node.children[0].local_var);
			  else
				  os.println( this.getType(node.type) + "load " + node.children[0].local_var);
		  }
		  if(this.in_condition) {
			  os.println("ifeq end_" + this.current_loop);
		  }
	  }
	  private void printLoadArray(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  os.println("iaload");
	  }
	  
	  private void printNewIntArr(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  os.println("newarray int");
	  }
	  
	  private void printStore(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  IRNode lhn = node.children[0];
		  Integer local_var = lhn.local_var;
		  if(local_var < 4)
			  os.println(this.getType(node.type) + "store_" + local_var);
		  else
			  os.println(this.getType(node.type) + "store " + local_var);
		  
	  }
	  
	  private void printStoreArray(IRNode node) {
		  IRNode lhn = node.children[0];
		  Integer local_var = lhn.local_var;
		  if(local_var < 4)
			  os.println("aload_" + local_var);
		  else
			  os.println("aload " + local_var);
		  
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  os.println(this.getType(node.type) + "astore");

		  
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
	  private void printLessThan(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  if(this.not == false)
			  os.println("if_icmpge end_" + this.current_loop);
		  else
			  os.println("if_icmpge not_" + this.not_count);
	  }
	  private void printNot(IRNode node) {
		  this.not = !this.not;
		  this.not_count++;
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  os.println("goto end_" + this.current_loop);
		  os.println("not_" + this.not_count + ":");
		  this.not = !this.not;
	  }
	  private void printAnd(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  //os.println("if_icmpge end_" + this.current_loop);
	  }
	  
	  private void printWhile(IRNode node) {
		  
		  String loop = "loop"+ ++this.loop_count;
		  this.current_loop = loop;
		  
		  os.println(loop + ":");
		  
		  IRNode condition = node.children[0];
		  this.current_loop = loop;
		  this.in_condition = true;
		  printJasmin(condition);
		  this.in_condition = false;
		  
		  //os.println("if_icmpge end_" + loop);
		  os.println("");
		  for(int i = 1; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
			  os.println("");
		  }
		  os.println("goto " + loop);
		  os.println("end_"+ loop + ":");
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
			  os.println("");
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
	  
	  private void printFields(IRNode r) {
		  
		  for (int i = 0; i < r.getChildren().length; i++) {
			  String toPrint =".field ";
			  toPrint += r.getChildren()[i].getInst() + " " + retType(r.getChildren()[i].getIRType());
			  os.println(toPrint);
		  }
				  
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
