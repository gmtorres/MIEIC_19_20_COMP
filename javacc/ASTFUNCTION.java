/* Generated By:JJTree: Do not edit this line. ASTFUNCTION.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
import java.util.List;
import java.util.ArrayList;

public
class ASTFUNCTION extends SimpleNode {
  public ASTFUNCTION(int id) {
    super(id);
  }

  public ASTFUNCTION(Jmm p, int id) {
    super(p, id);
  }
  
  
  public boolean doSemanticAnalysis(StringBuilder info) throws SemanticException {

		boolean result = true;

		if(this.children != null) {
			for(Node node : this.children) {
				boolean r = ((SimpleNode) node).doSemanticAnalysis(info);
				result = result && r;
			}
		}

		if (result == false) {
			return result;
		}
		this.type = "null";
	    SimpleNode lhn  = (SimpleNode) this.children[0];
	    SimpleNode rhn  = (SimpleNode) this.children[1];
	    boolean request_static = false;
	    
		ArrayList<String> objs = new ArrayList<String>();
		if(lhn.toString().equals("NEW_IDENTIFIER")) {
			objs = this.descriptors.getDescriptor(((SimpleNode) lhn.children[0]).name).getAllTypes();
		}else if(lhn.toString().equals("FUNCTION")) {
			objs = this.descriptors.getDescriptor(lhn.type).getAllTypes();
		}
		else if(lhn.name.equals("this")) {
			objs = this.descriptors.getDescriptor("this").getAllTypes();
		}
		else{
			if(this.simbolTable.isSimbolKnown(lhn.name)) {
				Simbol s = this.simbolTable.getSimbol(lhn.name);
				if(s.getAssignType() == null) {
					  System.out.println("Error on line " + lhn.lineNo + ": Simbol " + lhn.name + " has not been initiated.");
					  this.decrementMaxErros();
					  return false;
				  }
				objs = this.simbolTable.getSimbol(lhn.name).getAssignType().getAllTypes();
			}else {
				request_static = true;
				Descriptor d = this.descriptors.getDescriptor(lhn.name);
				//System.out.println(lhn.name + "  " + d);
				if(d != null)
					objs = d.getAllTypes();
				else
					objs.add(lhn.name);
			}
		}
		String params = "";
		for(int i = 0; i < rhn.jjtGetNumChildren();i++) {
			SimpleNode rhnc = (SimpleNode) rhn.children[i];
			params+=((SimpleNode)rhnc.children[0]).type+",";
		}
		System.out.println(params);
		ArrayList<Function> f = new ArrayList<Function>();
		for(int i = 0; i < objs.size();i++)
			if(this.functionTable.isFunctionHere( objs.get(i),this.name,params))
				f.add(this.functionTable.getFunction(objs.get(i),this.name,params));
		int i = 0;
		for(;i<f.size();i++) {
			//this.type = f.get(i).getType();
			List<Descriptor> listDesc = f.get(i).getDescriptors();
			if (listDesc.size() != rhn.jjtGetNumChildren()) {
				if(objs.size() == 1)
					System.out.println("1Error on line " + this.lineNo + ", column " + this.columnNo + ": Wrong number of parameters in function " + lhn.name + "." + this.name);
				continue;
			}
			else {
				int ii = 0;
				for (; ii < rhn.jjtGetNumChildren(); ii++) {
					SimpleNode rhnc = (SimpleNode) rhn.children[ii];
					if(!(listDesc.get(ii).getName().equals(((SimpleNode)rhnc.children[0]).type))) {
						if(objs.size() == 1)
							System.out.println("2Error on line " + this.lineNo + ", column " + this.columnNo + ": Wrong argument type: " + ((SimpleNode)rhnc.children[0]).type + " should be " + listDesc.get(ii).getName());
						break;
					}
				}
				if(ii != rhn.jjtGetNumChildren())
					continue;
				else {
					if(request_static && !f.get(i).isStatic) {
						continue;
					}
					this.type = f.get(i).getType();
					break;
				}
			}
		}
		if(i == f.size()) {
			result = false;
			String toPrint = "3Error on line " + this.lineNo + ", column " + this.columnNo + ": Function ";
			if(request_static)
				toPrint+= "static ";
			toPrint+=  lhn.name + "." + this.name+"(";
			for(int a = 0; a < rhn.jjtGetNumChildren(); a++ ) {
				SimpleNode rhnc = (SimpleNode) rhn.children[a];
				toPrint+= ((SimpleNode)rhnc.children[0]).type;
				if(a != rhn.jjtGetNumChildren() - 1 ) toPrint+=", ";
			}
			System.out.println(toPrint + ") is not known.");
			this.decrementMaxErros();
		}
	
	  return result;
  }
}
/* JavaCC - OriginalChecksum=ff7d338328dd91daef21bbda214f1c59 (do not edit this line) */
