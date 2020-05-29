import java.util.List;
import java.util.ArrayList;

public class CFGNode {
	
	CFGNode[] sucessors = new CFGNode[0];
	
	IRNode correspondent = null;
	
	CFGNode last = null;
	
	Integer line = -1;
	
	List<Simbol> def = new ArrayList<Simbol>();
	List<Simbol> use = new ArrayList<Simbol>();
	
	List<Simbol> in = new ArrayList<Simbol>();
	List<Simbol> out = new ArrayList<Simbol>();
	
	public CFGNode() {
		
	}
	
	private List<Simbol> diffList(List<Simbol> l1,List<Simbol> l2){
		List<Simbol> l = new ArrayList<Simbol>();
		for(int i=0;i<l1.size();i++) {
			if(l2.indexOf(l1.get(i)) == -1)
				l.add(l1.get(i));
		}
		return l;
	}
	
	private List<Simbol> unionList(List<Simbol> l1,List<Simbol> l2){
		List<Simbol> l = new ArrayList<Simbol>();
		for(int i=0;i<l1.size();i++) {
				l.add(l1.get(i));
		}
		for(int i=0;i<l2.size();i++) {
			if(l.indexOf(l2.get(i)) == -1)
				l.add(l2.get(i));
		}
		return l;
	}
	private boolean compareList(List<Simbol> l1, List<Simbol> l2) {
		if(l1.size() != l2.size())
			return false;
		for(int i=0; i < l1.size();i++) {
			if(l2.indexOf(l1.get(i)) == -1)
				return false;
		}
		return true;
	}
	
	public void addToList(List<Simbol> l,Simbol d) {
		if(l.indexOf(d) != -1)
			return;
		l.add(d);
	}
	
	public void setUseDef(CFGNode n) {
		if(n == null)
			return;
		if(n.correspondent.getInst().equals("st")) {
			if(n.correspondent.children[0].simbol != null) {
				n.addToList(n.def,n.correspondent.children[0].simbol);
			}
			setUse(n,n.correspondent.children[1]);
		}else {
			setUse(n,n.correspondent);
		}
		for(int i = 0; i < n.sucessors.length;i++) {
			if(n.sucessors[i].line <= n.line)
				continue;
			setUseDef(n.sucessors[i]);
		}
	}
	
	private void setUse(CFGNode n,IRNode node) {
		for(IRNode child : node.children)
			setUse(n,child);
		if(node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
			n.addToList(n.use,node.children[0].simbol);
		}
	}
	
	private void addSucessor(CFGNode n) {
		 if (sucessors == null) {
			 sucessors = new CFGNode[1];
		 }else {
			 CFGNode c[] = new CFGNode[this.sucessors.length+1];
		     System.arraycopy(sucessors, 0, c, 0, sucessors.length);
		     sucessors = c;
		 }
		 int length = sucessors.length;
		 sucessors[length-1] = n;
	}
	
	public void buildCFG(IRNode method) {
		IRNode args= method.children[2];
		for(IRNode arg : args.children) {
			this.addToList(this.def, arg.simbol);
		}
		this.buildBase(method.children[4]);
		this.setUseDef(this);
		this.print();
		this.livenessAnalysis();
		this.print();
		System.out.println("");
	}
	
	private void buildBase(IRNode method) {
		
		
		Integer l = 0;
		//System.out.println("CFG" + method.getInst());
		CFGNode cfg = this;
		for(int i = 0; i < method.children.length;i++) {
			if(method.children[i].getInst().equals("while")) {
				l = cfg.buildWhile(method.children[i], l);
			}else if(method.children[i].getInst().equals("if")) {
				CFGNode suc = null;
				if(i < method.children.length-1)
					suc = new CFGNode();
				l = cfg.buildIf(method.children[i], l, suc);
				cfg = suc;
				continue;
			}else {
				cfg.correspondent = method.children[i];
				cfg.line = l++;
			}
			if(i < method.children.length-1) {
				CFGNode suc = new CFGNode();
				cfg.addSucessor(suc);
				cfg = suc;
			} 
		}
		this.last = cfg;
	}
	
	public int buildWhile(IRNode loop, Integer l) {
		this.correspondent = loop.children[0];
		this.line = l++;
		CFGNode cfg = this;
		if(loop.children.length != 0) {
			CFGNode suc = new CFGNode();
			cfg.addSucessor(suc);
			cfg = suc;
		}
		for(int i = 1; i < loop.children.length;i++) {
				if(loop.children[i].getInst().equals("while")) {
					//System.out.println("in while");
					l = cfg.buildWhile(loop.children[i], l);
	
				}else if(loop.children[i].getInst().equals("if")) {
					CFGNode suc = null;
					if(i < loop.children.length-1)
						suc = new CFGNode();
					else
						suc = this;
					l = cfg.buildIf(loop.children[i], l, suc);
					cfg = suc;
					continue;
				}else{	
					cfg.correspondent = loop.children[i];
					cfg.line = l++;
				}
			if(i < loop.children.length-1) {
				CFGNode suc = new CFGNode();
				cfg.addSucessor(suc);
				cfg = suc;
			} 
		}
		cfg.addSucessor(this);
		
		return l;
	}
	
	public int buildIf(IRNode branch, int l,CFGNode successor) {
		this.correspondent = branch.children[0];
		this.line = l++;
		CFGNode cfg = this;	
		
		IRNode if_node = branch.children[1];
		IRNode else_node = branch.children[2];
		
		if(if_node.children.length != 0) {
			CFGNode suc = new CFGNode();
			cfg.addSucessor(suc);
			cfg = suc;
		}
		for(int i = 0; i < if_node.children.length;i++) {
			if(if_node.children[i].getInst().equals("while")) {
				//System.out.println("in while");
				l = cfg.buildWhile(if_node.children[i], l);

			}else if(if_node.children[i].getInst().equals("if")) {
				CFGNode suc = null;
				if(i < if_node.children.length-1)
					suc = new CFGNode();
				else
					suc = successor;
				l = cfg.buildIf(if_node.children[i], l, suc);
				cfg = suc;
				continue;
			}else{	
				cfg.correspondent = if_node.children[i];
				cfg.line = l++;
			}
			if(i < if_node.children.length-1) {
				CFGNode suc = new CFGNode();
				cfg.addSucessor(suc);
				cfg = suc;
			} 
		}
		
		if(successor != null)	cfg.addSucessor(successor);
		
		cfg = this;
		if(else_node.children.length != 0) {
			CFGNode suc = new CFGNode();
			cfg.addSucessor(suc);
			cfg = suc;
		}
		for(int i = 0; i < else_node.children.length;i++) {
			if(else_node.children[i].getInst().equals("while")) {
				//System.out.println("in while");
				l = cfg.buildWhile(else_node.children[i], l);

			}else if(else_node.children[i].getInst().equals("if")) {
				CFGNode suc = null;
				if(i < else_node.children.length-1)
					suc = new CFGNode();
				else
					suc = successor;
				l = cfg.buildIf(else_node.children[i], l, suc);
				cfg = suc;
			}else{	
				cfg.correspondent = else_node.children[i];
				cfg.line = l++;
			}
			if(i < else_node.children.length-1) {
				CFGNode suc = new CFGNode();
				cfg.addSucessor(suc);
				cfg = suc;
			} 
		}
		
		if(successor != null)	
			cfg.addSucessor(successor);
		
		return l;
	}
	
	public void print() {
		printCFG(this);
	}
	
	private void printCFG(CFGNode n) {
		if(n == null)
			return;
		System.out.print(n.line + "\t" + n.correspondent.getInst() + "\tn:" + n.sucessors.length + "\t");
		for(int i = 0; i < n.sucessors.length;i++) {
			System.out.print(n.sucessors[i].line + ",");
		}
		System.out.println("\tUse: " + n.use + "\t\tDEF: " + n.def  + "\t\tOUT: " + n.out + "\t\tIN: " + n.in);
		for(int i = 0; i < n.sucessors.length;i++) {
			if(n.sucessors[i].line <= n.line)
				continue;
			printCFG(n.sucessors[i]);
		}
	}
	
	private void addToStack(List<CFGNode> stack, CFGNode n) {
		if(stack.indexOf(n) != -1)
			return;
		stack.add(n);
	}
	
	private void buildStackAux(List<CFGNode> stack, CFGNode n) {
		if(n == null)
			return;
		for(int i = n.sucessors.length-1; i >= 0;i--) {
			if(n.sucessors[i].line <= n.line)
				continue;
			buildStackAux(stack,n.sucessors[i]);
		}
		addToStack(stack,n);
	}
	
	private List<CFGNode> buildStack(CFGNode n){
		List<CFGNode> stack = new ArrayList<CFGNode>();
		buildStackAux(stack,n);
		return stack;
	}
	
	public void livenessAnalysis() {
		
		List<CFGNode> stack = buildStack(this);
		
		for(CFGNode n : stack) {
			System.out.println(n.line);
		}
		
		boolean completed = true;
		
		do {
			completed = true;
			for(CFGNode n : stack) {
				
				List<Simbol> in_temp = n.in;
				List<Simbol> out_temp = n.out;
				for(CFGNode child : n.sucessors) {
					n.out = this.unionList(n.out,child.in);
				}
				//System.out.println(n.out+"\t"+n.def + "   ->   " + this.diffList(n.out, n.def));
				n.in = this.unionList(n.use, this.diffList(n.out, n.def));
				if(!this.compareList(n.in, in_temp) || !this.compareList(n.out, out_temp))
					completed = false;
			}		
		}while(completed == false);
		
		
		
	}

}
