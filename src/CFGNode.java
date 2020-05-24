import java.util.ArrayList;

public class CFGNode {
	
	CFGNode[] sucessors = new CFGNode[0];
	
	IRNode correspondent = null;
	
	CFGNode last = null;
	
	Integer line = -1;
	
	ArrayList<Simbol> def = new ArrayList<Simbol>();
	ArrayList<Simbol> use = new ArrayList<Simbol>();
	
	public CFGNode() {

	}
	
	public void addDef(Simbol d) {
		for(Simbol obj : def) {
			if(obj == d)
				return;
		}
		def.add(d);
		System.out.println("added  " + def);
	}
	
	public void setUseDef(CFGNode n) {
		if(n == null)
			return;
		if(n.correspondent.getInst().equals("st")) {
			if(n.correspondent.children[0].simbol != null) {
				n.addDef(n.correspondent.children[0].simbol);
			}
		}
		for(int i = 0; i < n.sucessors.length;i++) {
			if(n.sucessors[i].line <= n.line)
				continue;
			setUseDef(n.sucessors[i]);
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
		if(branch.children[1].children.length != 0) {
			CFGNode suc = new CFGNode();
			cfg.addSucessor(suc);
			cfg = suc;
		}
		for(int i = 0; i < branch.children[1].children.length;i++) {
			if(branch.children[1].children[i].getInst().equals("while")) {
				//System.out.println("in while");
				l = cfg.buildWhile(branch.children[1].children[i], l);

			}else if(branch.children[1].children[i].getInst().equals("if")) {
				CFGNode suc = null;
				if(i < branch.children[1].children.length-1)
					suc = new CFGNode();
				l = cfg.buildIf(branch.children[1].children[i], l, suc);
				cfg = suc;
				continue;
			}else{	
				cfg.correspondent = branch.children[1].children[i];
				cfg.line = l++;
			}
			if(i < branch.children[1].children.length-1) {
				CFGNode suc = new CFGNode();
				cfg.addSucessor(suc);
				cfg = suc;
			} 
		}
		
		if(successor != null)	cfg.addSucessor(successor);
		
		cfg = this;
		if(branch.children[1].children.length != 0) {
			CFGNode suc = new CFGNode();
			cfg.addSucessor(suc);
			cfg = suc;
		}
		for(int i = 0; i < branch.children[2].children.length;i++) {
			if(branch.children[2].children[i].getInst().equals("while")) {
				//System.out.println("in while");
				l = cfg.buildWhile(branch.children[2].children[i], l);

			}else if(branch.children[2].children[i].getInst().equals("if")) {
				CFGNode suc = null;
				if(i < branch.children[2].children.length-1)
					suc = new CFGNode();
				l = cfg.buildIf(branch.children[2].children[i], l, suc);
				cfg = suc;
			}else{	
				cfg.correspondent = branch.children[2].children[i];
				cfg.line = l++;
			}
			if(i < branch.children[2].children.length-1) {
				CFGNode suc = new CFGNode();
				cfg.addSucessor(suc);
				cfg = suc;
			} 
		}
		
		if(successor != null)	cfg.addSucessor(successor);
		
		return l;
	}
	
	public void printCFG(CFGNode n) {
		if(n == null)
			return;
		System.out.print(n.line + "  " + n.correspondent.getInst() + "    n:" + n.sucessors.length + "   DEF: " + this.def + "   Use: " + this.use);
		System.out.println("");
		for(int i = 0; i < n.sucessors.length;i++) {
			if(n.sucessors[i].line <= n.line)
				continue;
			printCFG(n.sucessors[i]);
		}
	}
	
	public void printCFG() {
		printCFG(this);
	}

}
