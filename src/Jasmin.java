import java.lang.reflect.Array; 
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

public class Jasmin {

	  IRNode root;
	  PrintStream os;
	  boolean debugMode;
	  
	  Integer loop_count = 0;
	  Integer if_count = 0;
	  
	  String current_loop;
	  String current_if;
	  String current_or;
	  
	  String sucess_tag = null;
	  String fail_tag = null;
	  
	  boolean in_while_condition = false;
	  boolean in_if_condition = false;
	  boolean in_or = false;
	  Integer or_count = 0;
	  boolean not = false;
	  Integer and_count = 0;
	  
	  boolean and_in_or = false;
	  
	  List<String> opt;
	  
	  
	  public Jasmin(IRNode r,PrintStream ps, boolean dg, List<String> opt) {
		  this.root=r;
		  this.os = ps;
		  this.debugMode = dg;
		  this.opt = opt;
		  printClass(this.root);
	  }
	  public Jasmin(IRNode r,PrintStream ps) {
		  this(r, ps,false,new ArrayList<String>());
	  }
	  
	  
	  private String retType(String type) {
		    switch (type) {
		    case "void":
		        return "V";
		    case "int":
		        return "I";  
		    case "float":
		    	return "F";
		    case "boolean":
		    	return "Z";	      
		    case "int[]":
		    	return "[I";
		    case "String":
		    	return "Ljava/lang/String;";
		    case "String[]":
		    	return "[Ljava/lang/String;";
		    default:
		        return "L"+type+";";
		    }
	  }
	  
	  private String getType(String type) {
		  switch (type) {  
		    case "boolean":
		    	//return "b";
		    case "int":
		        return "i";
		    case "float":
		    	return "f";
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
		  	case "lds":
		  		printString(r);
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
		  	case "iinc":
		  		printInc(r);
		  		break;
		  	case "<":
		  		printLessThan(r);
		  		break;
		  	case ">":
		  		printGreaterThan(r);
		  		break;
		  	case "&&":
		  		printAnd(r);
		  		break;
		  	case "||":
		  		printOr(r);
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
		  	case "super":
		  		printSuper(r);
		  		break;
		  	case "pop":
		  		printPop(r);
		  		break;
		  	case "statements":
		  		printStatements(r);
		  		break;
		  }	
		  
		 
	  }
	  
	  private void printStatements(IRNode node) {
		  for(IRNode child : node.children)
			  this.printJasmin(child);
	  }
	  
	  private void printNewObject(IRNode node) {
		  this.println("new " + node.getChildren()[0].getInst());
		  this.println("dup");
		  for(int i = 0; i < node.getChildren()[1].getChildren().length; i++) {
			  printJasmin(node.getChildren()[1].getChildren()[i]);
		  }
		  String toPrint = "";
		  toPrint += ("invokespecial " +  node.getChildren()[0].getInst() + "/<init>" + "(");
		  for (int i = 0; i< node.getChildren()[1].getChildren().length; i++) {
			  toPrint += retType(node.getChildren()[1].getChildren()[i].type);
			  if (i != node.getChildren()[1].getChildren().length - 1) {
				  toPrint += ""; 
			  }
		  }
		  
		  toPrint += ")V";
		  this.println(toPrint);
		  
		  //this.printPop(node);
	  }
	  
	  
	  private void printOperation(IRNode node) {
		
 		if(this.opt.indexOf("s") != -1 && node.getIRType().equals("int")) {
 			if (node.getInst().equals("/")) {
 				if(node.getChildren()[1].getInst().equals("ldc")) {
 					int number = Integer.parseInt(node.getChildren()[1].getChildren()[0].getInst()); 
 					if((number != 0) && ((number & (number - 1)) == 0)) {
 						int shift = (int)(Math.log(number) / Math.log(2));
 						printJasmin(node.getChildren()[0]);
 						if(shift>= 0 && shift <= 5)
 							this.println("iconst_"+shift);
 						else if(shift>= -127 && shift <= 127)
 							this.println("bipush " + shift);
 				 		this.println("ishr");
 				 		return;
 					}
 				}
 			}
 			else if (node.getInst().equals("*")) {
 				if(node.getChildren()[0].getInst().equals("ldc")) {
 					int number = Integer.parseInt(node.getChildren()[0].getChildren()[0].getInst()); 
 					if((number != 0) && ((number & (number - 1)) == 0)) {
 						int shift = (int)(Math.log(number) / Math.log(2));
 						printJasmin(node.getChildren()[1]);
 						if(shift>= 0 && shift <= 5)
 							this.println("iconst_"+shift);
 						else if(shift>= -127 && shift <= 127)
 							this.println("bipush " + shift);
 				 		this.println("ishl");
 				 		return;
 					}
 				}
 				else if(node.getChildren()[1].getInst().equals("ldc")) {
 					int number = Integer.parseInt(node.getChildren()[1].getChildren()[0].getInst()); 
 					if((number != 0) && ((number & (number - 1)) == 0)) {
 						int shift = (int)(Math.log(number) / Math.log(2));
 						printJasmin(node.getChildren()[0]);
 						if(shift>= 0 && shift <= 5)
 							this.println("iconst_"+shift);
 						else if(shift>= -127 && shift <= 127)
 							this.println("bipush " + shift);
 				 		this.println("ishl");
 				 		return;
 					}
 				}
 			}
 			
 		}
		for(int i = 0; i < node.getChildren().length; i++) {
			printJasmin(node.getChildren()[i]);
		
		}
	  	
		String op = null;
		switch(node.getInst()) {
		case "+": op = "add";
		 	break;
		case "-": op = "sub";
			break;
		case "*": op = "mul";
			break;
		case "/": op = "div";
		  	break;
		default:
			return;
		}
		this.println(this.getType(node.getIRType()) + op);
	  }
	  private void printInc(IRNode node) {
		  this.println("iinc " + node.children[0].local_var + " " + node.children[1].getInst());
	  }
	  
	  private void printString(IRNode node) {
		  this.println("ldc " + node.children[0].getInst());
	  }
	  
	  private void printLoad(IRNode node) {
		  String prefix = "";
		  if(node.getInst().equals("ldc")) {
			  if(node.getIRType().equals("int") || node.getIRType().equals("boolean")) {
				  Integer value = Integer.parseInt(node.children[0].getInst());
				  if(value>= 0 && value <= 5)
					  this.println("iconst_"+value);
				  else if(value>= -127 && value <= 127)
					  this.println("bipush " + value);
				  else if(value>= -32767 && value <= 32767)
					  this.println("sipush " + value);
				  else
					  this.println("ldc " + value);
			  }else if(node.getIRType().equals("float")) {
				  Float value = Float.parseFloat(node.children[0].getInst());
				  this.println("ldc " + value + "f");
			  }
		  }else if(node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
			  Integer local_var = node.children[0].local_var;
			  if(local_var < 4)
				  this.println( this.getType(node.type) + "load_" + node.children[0].local_var);
			  else
				  this.println( this.getType(node.type) + "load " + node.children[0].local_var);
		  }
		  this.printConditions(node);
	  }
	  
	  private void printConditions(IRNode node) {
		  if((this.in_while_condition || this.in_if_condition) && node.type != null && node.type.equals("boolean") ) {
			  
			  String op = null;
			  String tag = this.fail_tag;
			  
			  boolean last = node.isLast();
			  //System.out.println(node.getInst() +"   F: "+ this.fail_tag + "  S: " + this.sucess_tag);
			  //System.out.println("or:" + this.in_or + "   and_in_or:" + this.and_in_or + " last:" + last);
			  if(this.in_or == true) {
				  if(!last)
					  tag = this.sucess_tag;
				  else
					  tag = this.fail_tag;
			  }else if(this.and_in_or == true && last) {
				  tag = this.sucess_tag;
			  }else { 
				  tag = this.fail_tag;
			  }
			  if(this.in_or == true) {
				  if(!last) {
					  if(this.not) op = "ifeq ";
					  else op = "ifne ";
				  }else {
					  if(this.not) op = "ifne ";
					  else op = "ifeq ";
				  }
			  }else if(this.and_in_or == true && last) {
				  if(this.not) op = "ifeq ";
				  else op = "ifne ";
			  }else {
				  if(this.not) op = "ifne ";
				  else op = "ifeq ";
			  }
			
			  this.println(op + tag);
		  }
	  }
	  
	  private void printLoadGlobal(IRNode node) {
		  	String name = node.getChildren()[0].getInst();
		  	if(name.equals("field"))
		  		name = "__"+name;
			this.println("aload_0\n" + "getfield " + root.getClassName()  + "/" + name + " " + retType(node.type) );
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
			this.printConditions(node);
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
		  IRNode lhn = node.children[0];
		  Integer local_var = lhn.local_var;
		  if(this.opt.indexOf("u") != -1) { //remove useless arithmetic code
			  if(local_var == -1 && node.children[1].getInst().equals("ldc")) { // arithmetic not assigned to a register
				  return;
			  }
		  }
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  if(local_var == -1)
			  this.println("pop");
		  else if(local_var < 4)
			  this.println(this.getType(node.type) + "store_" + local_var);
		  else
			  this.println(this.getType(node.type) + "store " + local_var);
		  
	  }
	  
	  private void printStoreGlobal(IRNode node) {
		  this.println("aload_0");
		  for(int i = 0; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
		  }
		  //putfield <field-spec> <descriptor>
		  String name = node.getChildren()[0].getInst();
		  if(name.equals("field"))
		  		name = "__"+name;
		  this.println("putfield " + root.getClassName() + "/"  + name + " " + retType(node.type));
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
			  int zero = 0;
			  if(node.children[0].getInst().equals("ldc")
					  && node.children[0].children[0].getInst().equals("0")) {
				  zero = 1;
				  printJasmin(node.getChildren()[1]);
			  }else if(node.children[1].getInst().equals("ldc")
					  && node.children[1].children[0].getInst().equals("0")) {
				  zero = 2;
				  printJasmin(node.getChildren()[0]);
			  }else {
				  for(int i = 0; i < node.getChildren().length; i++) {
					  printJasmin(node.getChildren()[i]);
				  }
			  }
			  
			  String op = null;
			  String tag = null;
			  
			  boolean last = node.isLast();
			  if(this.in_or == true) {
				  if(!last)
					  tag = this.sucess_tag;
				  else
					  tag = this.fail_tag;
			  }else if(this.and_in_or == true && last) {
				  tag = this.sucess_tag;
			  }else { 
				  tag = this.fail_tag;
			  }
			  if(this.in_or == true) {
				  if(!last) {
					  if(this.not) op = "if_icmpge ";
					  else op = "if_icmplt ";
				  }else {
					  if(this.not) op = "if_icmplt ";
					  else op = "if_icmpge ";
				  }
			  }else if(this.and_in_or == true && last) {
				  if(this.not) op = "if_icmpge ";
				  else op = "if_icmplt ";
			  }else {
				  if(this.not) op = "if_icmplt ";
				  else op = "if_icmpge ";
			  }
			  if(zero != 0) {
				  if(zero == 1) {
					  if(op.equals("if_icmplt ")) {
						  op = "ifge ";
					  }else if(op.equals("if_icmpge ")) {
						  op = "iflt ";
					  }
				  }else if(zero == 2) {
					  if(op.equals("if_icmplt ")) {
						  op = "iflt ";
					  }else if(op.equals("if_icmpge ")) {
						  op = "ifge ";
					  }
				  }
			  }
			
			  this.println(op + tag);
			  
		  }else {
			  this.printboolExpression(node);
		  }
	  }
	  
	  private void printGreaterThan(IRNode node) {
		  if(this.in_if_condition || this.in_while_condition) {
			  int zero = 0;
			  if(node.children[0].getInst().equals("ldc")
					  && node.children[0].children[0].getInst().equals("0")) {
				  zero = 1;
				  printJasmin(node.getChildren()[1]);
			  }else if(node.children[1].getInst().equals("ldc")
					  && node.children[1].children[0].getInst().equals("0")) {
				  zero = 2;
				  printJasmin(node.getChildren()[0]);
			  }else {
				  for(int i = 0; i < node.getChildren().length; i++) {
					  printJasmin(node.getChildren()[i]);
				  }
			  }
			  
			  String op = null;
			  String tag = null;
			  
			  boolean last = node.isLast();
			  if(this.in_or == true) {
				  if(!last)
					  tag = this.sucess_tag;
				  else
					  tag = this.fail_tag;
			  }else if(this.and_in_or == true && last) {
				  tag = this.sucess_tag;
			  }else { 
				  tag = this.fail_tag;
			  }
			  if(this.in_or == true) {
				  if(!last) {
					  if(this.not) op = "if_icmple ";
					  else op = "if_icmpgt ";
				  }else {
					  if(this.not) op = "if_icmpgt ";
					  else op = "if_icmple ";
				  }
			  }else if(this.and_in_or == true && last) {
				  if(this.not) op = "if_icmple ";
				  else op = "if_icmpgt ";
			  }else {
				  if(this.not) op = "if_icmpgt ";
				  else op = "if_icmple ";
			  }
			  
			  if(zero != 0) {
				  if(zero == 1) {
					  if(op.equals("if_icmple ")) {
						  op = "ifgt ";
					  }else if(op.equals("if_icmpgt ")) {
						  op = "ifle ";
					  }
				  }else if(zero == 2) {
					  if(op.equals("if_icmple ")) {
						  op = "ifle ";
					  }else if(op.equals("if_icmpgt ")) {
						  op = "ifgt ";
					  }
				  }
			  }
			
			  this.println(op + tag);
			  
		  }else {
			  this.printboolExpression(node);
		  }
	  }
	 
	  private void printAnd(IRNode node) {
		  if(this.in_if_condition || this.in_while_condition) {
			  String jump = null;
			  String prev_fail = null;
			  boolean prev_or = this.in_or;
			  boolean last = node.isLast();
			  this.in_or = false;
			  if(node.parent.getInst().equals("||") && !last) {
				  jump = "and_"+this.and_count++;
				  prev_fail = this.fail_tag;
				  this.fail_tag = jump;
				  this.and_in_or = true;
				  this.in_or = false;
			  }
			  //System.out.println(node.getInst() +"   "+ this.fail_tag + "  " + this.sucess_tag);
			  for(int i = 0; i < node.getChildren().length; i++) {
				  printJasmin(node.getChildren()[i]);
			  } 	
			  if(node.parent.getInst().equals("||")  && !last) {
				  this.and_in_or = false;
				  this.in_or = true;
				  this.println(jump+":");
				  this.fail_tag = prev_fail;
			  }
			  this.in_or = prev_or;
		  }else {
			  this.printboolExpression(node);
		  }
	  }
	  
	  private void printOr(IRNode node) {
		  if(this.in_if_condition || this.in_while_condition) {
			  String jump;
			  String prev_suc;
			  this.in_or = true;
			  
			  jump = "or_" + this.or_count++;
			  prev_suc = this.sucess_tag;
			  this.sucess_tag = jump;
			  //System.out.println(node.getInst() +"   "+ this.fail_tag + "  " + this.sucess_tag);
			  for(int i = 0; i < node.getChildren().length; i++) {
				  printJasmin(node.getChildren()[i]);
			  }
			  
			  this.println(jump + ":");
			  this.sucess_tag = prev_suc;
			  this.in_or = false;

		  }else {
			  this.printboolExpression(node);
		  }
	  }
	  
	  private void printNot(IRNode node) {
		  if(this.in_if_condition || this.in_while_condition) {
			  this.not = !this.not;
			  for(int i = 0; i < node.getChildren().length; i++) {
				  printJasmin(node.getChildren()[i]);
			  }
			  this.not = !this.not;
		  }else {
			  this.printboolExpression(node);
		  }
	  }
	  
	  private void printWhile(IRNode node) {
		  
		  String loop = "loop"+ ++this.loop_count;
		  
		  //this.println(loop + ":");
		  
		  IRNode condition = node.children[0];
		  this.current_loop = loop;
		  
		  this.fail_tag = "end_" + loop;
		  
		  this.in_while_condition = true;
		  printJasmin(condition);
		  this.in_while_condition = false;
		  
		  this.println("begin_" + loop + ":");
		  
		  //this.println("if_icmpge end_" + loop);
		  this.println("");
		  for(int i = 1; i < node.getChildren().length; i++) {
			  printJasmin(node.getChildren()[i]);
			  this.println("");
		  }
		  //this.println("goto " + loop);
		  IRBuilder.propagateNot(condition, 0);
		  condition = node.children[0];
		  this.fail_tag = "begin_" + loop;
		  this.in_while_condition = true;
		  printJasmin(condition);
		  this.in_while_condition = false;
		  this.println("end_"+ loop + ":");
	  }
	  private void printIf(IRNode node) {
		  Boolean branch = null;
		  String c_if = "if"+ ++this.if_count;
		  
		  IRNode condition = node.children[0];
		  
		  if(this.opt.indexOf("b") != -1 && condition.getInst().equals("ldc")) {
			  if(condition.children[0].getInst().equals("0"))
				  branch = false;
			  else if(condition.children[0].getInst().equals("1"))
				  branch = true;
		  }
		  if(branch == null) {
			  this.current_if = c_if;
			  
			  this.fail_tag = "else_" + c_if;
			  
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
		  }else if(branch) {
			  for(int i = 0; i < node.children[1].getChildren().length; i++) {
				  printJasmin(node.children[1].getChildren()[i]);
				  this.println("");
			  }
		  }else {
			  for(int i = 0; i < node.children[2].getChildren().length; i++) {
				  printJasmin(node.children[2].getChildren()[i]);
				  this.println("");
			  }
		  }
	  }
	  
	  private void printboolExpression(IRNode node) { //similar to if, called directly
		  
		  String c_if = "if"+ ++this.if_count;
		  
		  IRNode condition = node;
		  this.current_if = c_if;
		  
		  this.fail_tag = "else_" + c_if;
		  
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
		  obj= r.getChildren()[1].getInst();

		  this.println(toPrint + obj);
		  
		  boolean change = IRNode.hasConstructor;
		  for(int i = 2; i < r.getChildren().length; i++) {
			  IRNode n = r.getChildren()[i];
			  if(change == false && n.getInst().equals("method")) {
				  change = true;
				  this.println("\n.method public <init>()V");
				  this.println("aload_0");
				  this.println("invokenonvirtual " + obj + "/<init>()V");
				  //this.println("iconst_1");
				  //this.println("invokestatic " + "io.println(I)V");
				  this.println("return");
				  this.println(".end method");
			  }
			  printJasmin(n);
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
			  String inst = r.getChildren()[2].getChildren()[i].getInst();
			  if(inst.equals("this"))
				  continue;
			  toPrint += retType(r.getChildren()[2].getChildren()[i].getInst());
			  if(i != r.getChildren()[2].getChildren().length - 1)
				  toPrint +="";
		  }

		  
		  toPrint += ")";
		  toPrint += retType(((r.getChildren()[1]).getChildren()[0]).getInst());
		  
		  this.println(toPrint);
		  
		  //toPrint = "\t.limit stack 100 "/* + r.op_stack*/; 
		  toPrint = "\t.limit stack " + r.op_stack; 
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
				  toPrint +="";
		  }			  
		  //System.out.println("   dsfd " + (r.getChildren()[1]).getChildren()[0].getInst());
		  String type = (r.getChildren()[1]).getChildren()[0].getInst();
		  toPrint += ")" + retType(type);
		  this.println(toPrint);
		  
		  /*if(!type.equals("void"))
			  this.printPop(r);*/
		  
		  /*if((this.in_while_condition || this.in_if_condition) && r.type != null && r.type.equals("boolean") ) {
			  String tag = "";
			  if(this.in_while_condition) tag = "end_" + this.current_loop;
			  else if(this.in_if_condition) tag = "else_" + this.current_if;
			  if(this.not) {
				  //this.println("ifeq not_" + this.not_count); 
				  this.println("ifne " + tag);
			  }else {
				  this.println("ifeq " + tag);
			  }
		  }*/
		  this.printConditions(r);
		  
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
				  toPrint +="";
		  }
		  String type = (r.getChildren()[1]).getChildren()[0].getInst();
		  toPrint += ")" + retType(type);
		  this.println(toPrint);
		  
		  /*if(!type.equals("void"))
			  this.printPop(r);*/
		  
		  
		  /*if((this.in_while_condition || this.in_if_condition) && r.type != null && r.type.equals("boolean") ) {
			  String tag = "";
			  if(this.in_while_condition) tag = "end_" + this.current_loop;
			  else if(this.in_if_condition) tag = "else_" + this.current_if;
			  if(this.not) {
				  //this.println("ifeq not_" + this.not_count); 
				  this.println("ifne " + tag);
			  }else {
				  this.println("ifeq " + tag);
			  }
		  }*/
		  this.printConditions(r);
		  
	  }
	  
	  private void printFields(IRNode r) {
		  this.println("");
		  for (int i = 0; i < r.getChildren().length; i++) {
			  String toPrint =".field ";
			  String name = r.getChildren()[i].getInst();
			  if(name.equals("field"))
				  name = "__" + name;
			  toPrint += name + " " + retType(r.getChildren()[i].getIRType());
			  this.println(toPrint);
		  }
	  }
	  
	  private void printSuper(IRNode r) {
		  this.println("aload_0");
		  this.println("invokenonvirtual " + r.children[0].getInst() + "/<init>()V");
	  }
	  
	  
	  private void printPop(IRNode r) {
		  /*IRNode parent = r.getParent();
		  if(parent == null)
			  return;
		  String parentInst = parent.getInst();
		  if(!parentInst.equals("st") && 
				  !parentInst.equals("+") && 
				  !parentInst.equals("-") && 
				  !parentInst.equals("/") && 
				  !parentInst.equals("*") && 
				  !parentInst.equals("&&") && 
				  !parentInst.equals("not") && 
				  !parentInst.equals("<") && 
				  !parentInst.equals("param") &&
				  !parentInst.equals("funcParams") &&
				  !parentInst.equals("st") &&
				  !parentInst.equals("stg") &&
				  !parentInst.equals("sta") &&
				  !parentInst.equals("return")&&
				  !( parentInst.equals("if") && this.in_if_condition) &&
				  !( parentInst.equals("while") && this.in_while_condition) &&
				  !parentInst.equals("invoke_static") &&
				  !parentInst.equals("invoke_virtual") ) {*/
			  this.println("pop");
			  
		  //}
	  }
	  

	  private void println(String str) {
		  this.os.println(str);
		  if(this.debugMode && this.os != System.out)
			  System.out.println(str);
	  }
	  
}
