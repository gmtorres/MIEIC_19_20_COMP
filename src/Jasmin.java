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
		    	return "Ljava/lang/String;";
		    case "String[]":
		    	return "[Ljava/lang/String;";
		    default:
		        return type;
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
		        return "a";
		    }
	  }
	  
	  
	  private void printJasmin(IRNode r) {
		  String toPrint = "";
		  
		  switch(r.getInst()) {
		  	case "method":
		  		printMethod(r);
		  		break;
		  	case "invoke_static":
		  		printInvokeStatic(r);
		  		break;
		  	case "invoke_virtual":
		  		printInvokeVirtual(r);
		  		break;
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
		  	case "new_object":
		  		printNewObject(r);
		  		break;
		  	case "return":
		  		printReturn(r);
		  		break;
		  }	
		  
		 
	  }
	  
	  private void printNewObject(IRNode node) {
		  this.println("new " + node.getChildren()[0].getInst());
		  this.println("dup");
		  String toPrint = "";
		  toPrint += ("invokespecial " +  node.getChildren()[0].getInst() + "/<init>" + "(");
		  for (int i = 0; i< node.getChildren()[1].getChildren().length; i++) {
			  toPrint += retType(node.getChildren()[1].getChildren()[i].getInst());
			  if (i != node.getChildren()[1].getChildren().length - 1) {
				  toPrint += ", "; 
			  }
		  }
		  
		  toPrint += ")V";
		  this.println(toPrint);
		  
		  this.printPop(node);
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
		  this.printPop(node);
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
		  
		  if((this.in_while_condition || this.in_if_condition) && node.type != null && node.type.equals("boolean") ) {
			  String tag = "";
			  if(this.in_while_condition) tag = "end_" + this.current_loop;
			  else if(this.in_if_condition) tag = "else_" + this.current_if;
			  if(this.not) {
				  //this.println("ifeq not_" + this.not_count); 
				  this.println("ifne " + tag);
			  }else {
				  this.println("ifeq " + tag);
			  }
		  }
		  //this.printPop(node);
	  }
	  
	  private void printLoadGlobal(IRNode node) {
			this.println("aload_0\n" + "getfield " + root.getClassName()  + "/" + node.getChildren()[0].getInst() + " " + retType(node.type) );
			/*if((this.in_while_condition || this.in_if_condition) && node.type != null && node.type.equals("boolean") ) {
				  String tag = "";
				  if(this.in_while_condition) tag = "end_" + this.current_loop;
				  else if(this.in_if_condition) tag = "else_" + this.current_if;
				  if(this.not) {
					  //this.println("ifeq not_" + this.not_count); 
					  this.println("ifne " + tag);
				  }else {
					  this.println("ifeq " + tag);
				  }
			  }
			  //this.printPop(node);*/
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
		  if(this.in_if_condition || this.in_while_condition) {
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
		  }else {
			  this.printboolExpression(node);
		  }
	  }
	  private void printNot(IRNode node) {
		  if(this.in_if_condition || this.in_while_condition) {
			  this.not = !this.not;
			  this.not_count++;
			  for(int i = 0; i < node.getChildren().length; i++) {
				  printJasmin(node.getChildren()[i]);
			  }
			  //this.println("goto end_" + this.current_loop);
			  this.println("not_" + this.not_count + ":");
			  this.not = !this.not;
		  }else {
			  this.printboolExpression(node);
		  }
	  }
	  
	  private void printAnd(IRNode node) {
		  if(this.in_if_condition || this.in_while_condition) {
			  for(int i = 0; i < node.getChildren().length; i++) {
				  printJasmin(node.getChildren()[i]);
			  }
		  }
		  else {
			  this.printboolExpression(node);
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
	  
	  private void printboolExpression(IRNode node) { //similar to if, called directly
		  
		  String c_if = "if"+ ++this.if_count;
		  
		  IRNode condition = node;
		  this.current_if = c_if;
		  this.in_if_condition = true;
		  printJasmin(condition);
		  this.in_if_condition = false;
		  
		  //this.println("if_icmpge end_" + loop);
		  this.println("");
		  this.println("iconst_1");
		  this.println("");
		  
		  this.println("goto end_" + c_if );
		  this.println("");
		  this.println("else_"+c_if + ":");
		  this.println("iconst_0");
		  this.println("");
		  this.println("end_"+ c_if + ":");
	  }
	  
	  private void printClass(IRNode root) {
		  IRNode r = root.getChildren()[0];
		  String toPrint = ".class public ";
		  toPrint += r.getChildren()[0].getInst();
		  root.setClassName(r.getChildren()[0].getInst());
		  this.println(toPrint);
		  
		  toPrint = ".super ";
		  String obj;
		  if (r.getChildren()[1].getInst().equals("Object")) {
			  obj = "java/lang/Object";
		  }
		  else {
			  obj= r.getChildren()[1].getInst();
		  }
		  this.println(toPrint + obj);
		  
		  this.println("\n.method public <init>()V");
		  this.println("aload_0");
		  this.println("invokenonvirtual " + obj + "/<init>()V");
		  this.println("return");
		  this.println(".end method");
		  
		  for(int i = 2; i < r.getChildren().length; i++) {
			  printJasmin(r.getChildren()[i]);
		  }
		  
	  }
	  
	  private void printMethod(IRNode r) {
		  this.println("");
		  String toPrint = ".method ";
		  for(int i = 0; i < r.getChildren()[0].children.length;i++) {
			  toPrint += ((r.getChildren()[0]).getChildren()[i]).getInst();
			  toPrint += " ";
		  }
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
		  
		  toPrint = "\t.limit stack " + r.op_stack; 
		  this.println(toPrint);
		  
		  toPrint = "\t.limit locals 10";
		  
		  //toPrint += r.locals_stack;


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
	  
	  
	  private void printInvokeStatic(IRNode r) {
		  
		  for (int t = 4; t < r.getChildren().length; t++) {
			  printJasmin(r.getChildren()[t]);
		  }
		  
		  String toPrint = "invokestatic"; 
		  //this.println(toPrint);
		  

		  toPrint += "\t" + r.getChildren()[2].getInst() + "." + r.getChildren()[3].getInst() + "(";
		  IRNode [] params = (r.getChildren()[0]).getChildren();
		  for (int i= 0; i < params.length; i++) {
			  toPrint += retType(params[i].getInst());
			  if(i != params.length - 1)
				  toPrint +=", ";
		  }			  
		  //System.out.println("   dsfd " + (r.getChildren()[1]).getChildren()[0].getInst());
		  String type = (r.getChildren()[1]).getChildren()[0].getInst();
		  toPrint += ")" + retType(type);
		  this.println(toPrint);
		  
		  if(!type.equals("void"))
			  this.printPop(r);
		  
	  }
	  
private void printInvokeVirtual(IRNode r) {

		  printJasmin(r.getChildren()[3]);

		  for (int t = 5; t < r.getChildren().length; t++) {
			  printJasmin(r.getChildren()[t]);
		  }

		  String toPrint = "invokevirtual"; 
		  
		  toPrint += " " + r.getChildren()[2].getInst() + "." + r.getChildren()[4].getInst() + "(";
		  IRNode [] params = (r.getChildren()[0]).getChildren();
		  for (int i= 0; i < params.length; i++) {
			  toPrint += retType(params[i].getInst());
			  if(i != params.length - 1)
				  toPrint +=", ";
		  }
		  String type = (r.getChildren()[1]).getChildren()[0].getInst();
		  toPrint += ")" + retType(type);
		  this.println(toPrint);
		  
		  if(!type.equals("void"))
			  this.printPop(r);
		  
	  }
	  
	  private void printFields(IRNode r) {
		  this.println("");
		  for (int i = 0; i < r.getChildren().length; i++) {
			  String toPrint =".field ";
			  toPrint += r.getChildren()[i].getInst() + " " + retType(r.getChildren()[i].getIRType());
			  this.println(toPrint);
		  }
	  }
	  
	  
	  private void printPop(IRNode r) {
		  IRNode parent = r.getParent();
		  if(parent == null)
			  return;
		  String parentInst = parent.getInst();
		  if(!parentInst.equals("st") && 
				  !parentInst.equals("+") && 
				  !parentInst.equals("-") && 
				  !parentInst.equals("/") && 
				  !parentInst.equals("*") && 
				  !parentInst.equals("&&") && 
				  !parentInst.equals("<") && 
				  !parentInst.equals("param") &&
				  !parentInst.equals("funcParams") &&
				  !parentInst.equals("st") &&
				  !parentInst.equals("stg") &&
				  !parentInst.equals("sta")&&
				  !parentInst.equals("return") ) {
			  this.println("pop");
		  }
	  }
	  

	  private void println(String str) {
		  this.os.println(str);
		  if(this.debugMode && this.os != System.out)
			  System.out.println(str);
	  }
	  
}
