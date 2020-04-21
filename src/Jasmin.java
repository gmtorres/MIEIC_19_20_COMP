import java.lang.reflect.Array; 
import java.io.PrintStream;

public class Jasmin {

	  IRNode root;
	  PrintStream os;
	  boolean debugMode;
	  
	  Integer loop_count = 0;
	  Integer if_count = 0;
	  
	  String current_loop;
	  String current_if;
	  
	  boolean in_while_condition = false;
	  boolean in_if_condition = false;
	  boolean not = false;
	  Integer not_count = 0;
	  
	  public Jasmin(IRNode r,PrintStream ps, boolean dg) {
		  this.root=r;
		  this.os = ps;
		  this.debugMode = dg;
		  printClass(this.root);
	  }
	  public Jasmin(IRNode r,PrintStream ps) {
		  this(r, ps,false);
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
		  	case "stg":
		  		printStoreGlobal(r);
		  		break;
		  	case "ldc":
		  	case "ldl":
		  	case "ldp":
		  		printLoad(r);
		  		break;
		  	case "ldg":
		  		printLoadGlobal(r);
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
			case "length":
				printLength(r);
				break;
		  	case "while":
		  		printWhile(r);
		  		break;
		  	case "if":
		  		printIf(r);
		  		break;
		  	case "not":
		  		printNot(r);
		  		break;
		  	case "fields":
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

		  switch(node.getInst()) {
		  case "+": this.println("iadd");
			  break;
		  case "-": this.println("isub");
			  break;
		  case "*": this.println("imul");
			  break;
		  case "/": this.println("idiv");
			  break;
		  }
		  
	  }
	  
	  private void printLoad(IRNode node) {
		  String prefix = "";
		  if(node.getInst().equals("ldc")) {
			  Integer value = Integer.parseInt(node.children[0].getInst());
			  if(value <= 5)
				  this.println("iconst_"+value);
			  else
				  this.println("bipush " + value);
		  }else if(node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
			  Integer local_var = node.children[0].local_var;
			  if(local_var < 4)
				  this.println( this.getType(node.type) + "load_" + node.children[0].local_var);
			  else
				  this.println( this.getType(node.type) + "load " + node.children[0].local_var);
		  }
		  
		  if( this.in_while_condition && node.type != null && node.type.equals("boolean") ) {
			  if(this.not) {
				  //this.println("ifeq not_" + this.not_count); 
				  this.println("ifne end_" + this.current_loop);
			  }else {
				  this.println("ifeq end_" + this.current_loop);
			  }
		  }
	  }
	  
	  private void printLoadGlobal(IRNode node) {
			this.println("aload_0\n" + "getfield " + root.getClassName()  + "/" + node.getChildren()[0].getInst() + " " + retType(node.type) );
	  }
	  
	  private void printLoadArray(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  this.println("iaload");
	  }
	  
	  private void printNewIntArr(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  this.println("newarray int");
	  }

	  private void printLength(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  this.println("arraylength");
	  }
	  
	  private void printStore(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  IRNode lhn = node.children[0];
		  Integer local_var = lhn.local_var;
		  if(local_var < 4)
			  this.println(this.getType(node.type) + "store_" + local_var);
		  else
			  this.println(this.getType(node.type) + "store " + local_var);
		  
	  }
	  
	  private void printStoreGlobal(IRNode node) {
			for(int i = 0; i < node.getChildren().length; i++) {
				printJasmin(node.getChildren()[i]);
			}
		//   putfield <field-spec> <descriptor>
		  this.println("putfield " + root.getClassName() + "/"  + node.getChildren()[0].getInst() + " " + retType(node.type));
	  }
	  
	  private void printStoreArray(IRNode node) {
		  IRNode lhn = node.children[0];
		  Integer local_var = lhn.local_var;
		  //TODO:: N�o sei se � preciso	
		  if(local_var!=null) {
			  if(local_var < 4)
				  this.println("aload_" + local_var);
			  else
				  this.println("aload " + local_var);
		  }else {
			  this.println("aload_0"); 
			  this.println("getfield " + root.getClassName() + "/"  + node.getChildren()[0].getInst() + " [" + retType(node.type));
		  }
		  
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  this.println(this.getType(node.type) + "astore");
		  
	  }
	  
	  private void printReturn(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  if(node.getChildren().length == 0)
			  this.println("return");
		  else {
			  String returnType = node.parent.parent.children[1].children[0].getInst();
			  this.println( getType(returnType) + "return");
		  }
		  
	  }
	  private void printLessThan(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  if(this.not == false) {
			  if(this.in_while_condition)
				  this.println("if_icmpge end_" + this.current_loop);
			  else if(this.in_if_condition)
				  this.println("if_icmpge else_" + this.current_if);
		  }else {
			  //se for o ultimo filho
			  if(node == node.parent.getChildren()[node.parent.getChildren().length - 1]) {
				  if(this.in_while_condition)
					  this.println("if_icmplt end_" + this.current_loop);
				  else if(this.in_if_condition)
					  this.println("if_icmplt else_" + this.current_if);
			  }else
				  this.println("if_icmpge not_" + this.not_count);
		  }
	  }
	  private void printNot(IRNode node) {
		  this.not = !this.not;
		  this.not_count++;
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  //this.println("goto end_" + this.current_loop);
		  this.println("not_" + this.not_count + ":");
		  this.not = !this.not;
	  }
	  
	  private void printAnd(IRNode node) {
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  //this.println("if_icmpge end_" + this.current_loop);
	  }
	  
	  private void printWhile(IRNode node) {
		  
		  String loop = "loop"+ ++this.loop_count;
		  
		  this.println(loop + ":");
		  
		  IRNode condition = node.children[0];
		  this.current_loop = loop;
		  this.in_while_condition = true;
		  printJasmin(condition);
		  this.in_while_condition = false;
		  
		  //this.println("if_icmpge end_" + loop);
		  this.println("");
		  for(int i = 1; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
			  this.println("");
		  }
		  this.println("goto " + loop);
		  this.println("end_"+ loop + ":");
	  }
	  private void printIf(IRNode node) {
		  
		  String c_if = "if"+ ++this.if_count;
		  
		  IRNode condition = node.children[0];
		  this.current_if = c_if;
		  this.in_if_condition = true;
		  printJasmin(condition);
		  this.in_if_condition = false;
		  
		  //this.println("if_icmpge end_" + loop);
		  this.println("");
		  for(int i = 0; i < node.children[1].getChildren().length; i++) {
			  printJasmin(node.children[1].getChildren()[i]);
			  this.println("");
		  }
		  this.println("goto end_" + c_if );
		  this.println("");
		  this.println("else_"+c_if + ":");
		  for(int i = 0; i < node.children[2].getChildren().length; i++) {
			  printJasmin(node.children[2].getChildren()[i]);
			  this.println("");
		  }
		  this.println("end_"+ c_if + ":");
	  }
	  
	  private void printClass(IRNode root) {
		  IRNode r = root.getChildren()[0];
		  String toPrint = ".class public ";
		  toPrint += r.getChildren()[0].getInst();
		  root.setClassName(r.getChildren()[0].getInst());
		  this.println(toPrint);
		  
		  toPrint = ".super ";
		  if (r.getChildren()[1].getInst().equals("Object")) {
			  toPrint += "java/lang/Object";
		  }
		  else {
			  toPrint += r.getChildren()[1].getInst();
		  }
		  this.println(toPrint);
		  
		  for(int i = 2; i < r.getChildren().length; i++) {
			  printJasmin(r.getChildren()[i]);
		  }
		  
	  }
	  
	  private void printMethod(IRNode r) {
		  this.println("");
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
		  
		  this.println(toPrint);
		  
		  toPrint = "\t.limit stack " + r.op_stack; //TODO: implementar na IR
		  this.println(toPrint);
		  
		  toPrint = "\t.limit locals ";
		  
		  toPrint += r.locals_stack;


		  this.println(toPrint);
		  this.println("");
		  
		  for (int k = 0; k < (r.getChildren()[4]).getChildren().length; k++) {
			  printJasmin((r.getChildren()[4]).getChildren()[k]);
			  this.println("");
		  }
		  
		  
		  if(((r.getChildren()[1]).getChildren()[0]).getInst().equals("void")) {
			  toPrint = "\treturn";
			  this.println(toPrint);
		  }
		  
		  toPrint = ".end method";
		  
		  this.println(toPrint);
		  
	  }
	  
	  
	  private void printInvoke(IRNode r) {
		  
		  for (int t = 4; t < r.getChildren().length; t++) {
			  printJasmin(r.getChildren()[t]);
		  }
		  
		  String toPrint = "invoke"; //TODO: verificar static ou virtual
		  this.println(toPrint);
		  

		  toPrint = "\t" + r.getChildren()[2].getInst() + "." + r.getChildren()[3].getInst() + "(";
		  IRNode [] params = (r.getChildren()[0]).getChildren();
		  for (int i= 0; i < params.length; i++) {
			  toPrint += retType(params[i].getInst());
			  if(i != params.length - 1)
				  toPrint +=", ";
		  }			  
		  //System.out.println("   dsfd " + (r.getChildren()[1]).getChildren()[0].getInst());
		  toPrint += ")" + retType((r.getChildren()[1]).getChildren()[0].getInst());
		  this.println(toPrint);

		  
		  
	  }
	  
	  private void printFields(IRNode r) {
		  this.println("");
		  for (int i = 0; i < r.getChildren().length; i++) {
			  String toPrint =".field ";
			  toPrint += r.getChildren()[i].getInst() + " " + retType(r.getChildren()[i].getIRType());
			  this.println(toPrint);
		  }
	  }
	  
	  
	  
	  
	  
	  
	  private void println(String str) {
		  this.os.println(str);
		  if(this.debugMode && this.os != System.out)
			  System.out.println(str);
	  }
	  
	  
	  
	 /*
	  public String printJasmin(IRNode node) {
		    String func = "";

	          if (root.toString().equals("METHOD")) {
	        	 jasmin += ".method " + root.name + "(" + argsFun(root)+ ")"+ type(root.type)  +  "\n";
	          }
	          
              if(root.toString().equals("EXPRESSION")) {
            	  if(root.parent.toString().equals("RETURN_EXPRESSION")) {
            		  this.println("RETURN EX\n");
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
