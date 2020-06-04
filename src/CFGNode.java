import java.util.List;
import java.util.ArrayList;

public class CFGNode {
	
	CFGNode[] sucessors = new CFGNode[0];
	
	IRNode correspondent = null;
	
	boolean is_static = true;
	
	Integer line = -1;
	
	List<Simbol> def = new ArrayList<Simbol>();
	List<Simbol> use = new ArrayList<Simbol>();
	
	List<Simbol> in = new ArrayList<Simbol>();
	List<Simbol> out = new ArrayList<Simbol>();
	
	List<LiveRange> ranges = new ArrayList<LiveRange>();
	
	public CFGNode() {
		
	}
	
	public void buildCFG(IRNode method) {
		IRNode args= method.children[2];
		this.line = -1;
		this.correspondent = method;
		if(method.children[0].children.length == 1)
			this.is_static = false;
		for(IRNode arg : args.children) {
			this.addToList(this.def, arg.simbol);
		}
		this.buildBase(method.children[4]);
		this.setUseDef(this);
		//this.print();
		this.livenessAnalysis();
		System.out.println("\n\n");
		this.print();
			
		System.out.println("");
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
		if(node.getInst().equals("sta") || node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
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
	
	private void buildBase(IRNode method) {
		CFGNode statements = new CFGNode();
		this.addSucessor(statements);
		
		Integer l = 0;
		//System.out.println("CFG" + method.getInst());
		CFGNode cfg = statements;
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
		//this.last = cfg;
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
	
	public String toString() {
		String str = "";
		str += this.line + "\t" + this.correspondent.getInst() + "\tn:" + this.sucessors.length + "\t";
		for(int i = 0; i < this.sucessors.length;i++) {
			str+=this.sucessors[i].line + ",";
		}
		str+= "\tUse: " + this.use + "\t\tDEF: " + this.def  + "\t\tOUT: " + this.out + "\t\tIN: " + this.in;
		return str;
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
		if(this.is_static == false) {
			this.addToList(stack.get(0).use,this.def.get(0));
		}
		
		/*for(CFGNode n : stack) {
			System.out.println(n.line);
		}*/
		
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
		
		for(int i = stack.size() -1; i >= 0; i--) {
			CFGNode n = stack.get(i);
			for(Simbol s : n.out) {
				int a = i-1;
				for(;a>=0 && stack.get(a).in.indexOf(s) != -1;a--) {
					
				}
				//System.out.println(s);
				addToRanges(new LiveRange(s,n.line,stack.get(a+1).line));
			}
		}
		/*for(LiveRange range : this.ranges) {
			System.out.println(range);
		}*/
		//System.out.println("\n\n\n\n");
		
		for(int i = 0; i < ranges.size(); i++) {
			LiveRange n = ranges.get(i);
			for(int a = i +1;a < ranges.size();a++) {
				LiveRange next = ranges.get(a);
				if(n.intersect(next))
					n.addInterference(next);
			}
		}
		
		/*for(LiveRange range : this.ranges) {
			System.out.println(range);
		}*/
		
		List<LiveRange> stackRange = new ArrayList<LiveRange>();
		int k = 10;
		
		if(k < this.def.size()){
			System.out.println("Less registries than arguments");
			return;
		}
		
		while(stackRange.size() != this.ranges.size()) {
			int old_size = stackRange.size();
			
			for(int i = 0; i < this.ranges.size();i++) {
				LiveRange range = this.ranges.get(i);
				if(range.on_stack)
					continue;
				if(range.degree() < k) {
					range.on_stack = true;
					stackRange.add(range);
				}
				
			}
			
			if(old_size == stackRange.size()) {
				System.out.println("COULD NOT HAVE SO FEW REGISTERS");
				return;
			}
		}
		
		for(int i = stackRange.size() -1; i>=0 ; i--) {
			LiveRange range = stackRange.get(i);
			int color = 0;
			for(; color < k; color++) {
				if(range.color == null && range.checkColor(color)) {
					range.color = color;
					break;
				}
			}
			if(color == k){
				System.out.println("COULD NOT COLOR WITH SO FEW REGISTERS");
				return;
			}
		}
		
		for(int a = 0; a < this.out.size();a++) {
			Simbol outS = this.out.get(a);
			for(int b = 0; b < this.ranges.size();b++) {
				LiveRange range = this.ranges.get(b);
				if(range.s == outS) {
					int def_p = this.def.indexOf(outS);
					if(range.color != def_p) {
						//System.out.println("Tenho que trocar  " + outS + "  " + def_p + " por " + range.color);
						Integer switch1 = def_p;
						Integer switch2 = range.color;
						for(int c = 0; c < this.ranges.size();c++) {
							LiveRange range_t = this.ranges.get(c);
							if(range_t.color == switch1)
								range_t.color = switch2;
							else if(range_t.color == switch2)
								range_t.color = switch1;
						}
					}
					break;
				}
			}
		}
		
		for(LiveRange range : stackRange) {
			System.out.println(range);
		}
		
		System.out.println("STACK:");
		for(CFGNode n : stack) {
			System.out.println(n);
			
		}
		
		this.allocRegisters(ranges, stack);
		
		
	}
	
	private void allocRegisters(List<LiveRange> ranges, List<CFGNode> stack) {
		
		for(int i = 0; i < stack.size()-1;i++) {
			CFGNode node = stack.get(i);
			Integer line = node.line;
			IRNode irNode = node.correspondent;
			String inst = irNode.getInst();
			if(inst.equals("st") || inst.equals("sta"))
				irNode.children[0].local_var = -1;
			allocRegisters_Aux(ranges,irNode,line);
				
		}		
	}
	
	private void allocRegisters_Aux(List<LiveRange> ranges, IRNode node, int line) {
		for(IRNode child : node.children)
			allocRegisters_Aux(ranges,child,line);
		if(node.getInst().equals("st") || node.getInst().equals("sta") || node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
			int register = -1;
			IRNode child = node.children[0];
			for(int i = 0; i < ranges.size();i++) {
				LiveRange range = ranges.get(i);
				if(range.s == child.simbol) {
					if(range.begin<=line && line<=range.end) {
						
						register = range.color;
					}
				}
			}
			System.out.println("register => " + register);
			child.local_var = register;
		}
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
	
	
	private void addToRanges(LiveRange r) {
		for(LiveRange range : this.ranges) {
			if(range.s != r.s)
				continue;
			if(range.intersect(r))
				return;
		}
		this.ranges.add(r);
	}
	
	
	
	
	
	class LiveRange{
		Simbol s;
		int begin;
		int end;
		
		List<LiveRange> interferences = new ArrayList<LiveRange>();
		
		boolean on_stack = false;
		
		Integer color = null;
		
		public void addInterference(LiveRange range) {
			this.interferences.add(range);
			range.interferences.add(this);
		}
		
		public int degree() {
			int d = 0;
			for(LiveRange range : this.interferences)
				if(range.on_stack == false)
					d++;
			return d;
		}
		
		public boolean checkColor(int c) {
			for(LiveRange range : this.interferences) {
				if(range.color == null)
					continue;
				if(range.color == c)
					return false;
			}
			return true;
		}
		
		
		public LiveRange(Simbol simbol, int b, int e) {
			this.s = simbol;
			this.begin = b;
			this.end = e;
		}
		public boolean intersect(LiveRange range) {
			if(range.begin >= this.begin && range.begin < this.end)
				return true;
			return false;
		}
		public String toString() {
			String str = this.s + "\t" + begin + "\t" + end +"\tregister: " + this.color +"    interferences:     ";
			for(LiveRange r : this.interferences)
				str+= r.s + ", ";
			
			return str;
		}
	}


}

