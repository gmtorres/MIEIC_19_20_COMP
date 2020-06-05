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
	
	boolean visited = false;
	
	public CFGNode() {
		
	}
	
	public void buildCFG(IRNode method, Integer registers, List<String> opt) throws RegisterAllocationException {
		if(opt.indexOf("o") != -1)
			this.constantPropagation(method);
		if(registers != null)
			this.registerOptimization(method, registers);
		//System.out.println("");
	}
	
	private void constantPropagation(IRNode method) {
		List<Simbol> values = new ArrayList<Simbol>();
		IRNode statements = method.children[4];
		
		doConstantPropagation(statements,values);
		
	}
	
	private void addToValues(List<Simbol> values, Simbol s) {
		if(values.indexOf(s) == -1)
			values.add(s);
	}
	private void removeFromValues(List<Simbol> values, Simbol s) {
		values.remove(s);
	}
	private void removeFromValues(List<Simbol> values, List<Simbol> def) {
		for(Simbol s : def)
			values.remove(s);
	}
	
	private void doConstantPropagation(IRNode method, List<Simbol> values) {
		for(IRNode child : method.children) {
			String inst = child.getInst();
			if(inst.equals("st")) {
				if(child.children[1].getInst().equals("ldc")) {
					Simbol s = child.children[0].simbol;
					String value = child.children[1].children[0].getInst();
					s.value = Integer.parseInt(value);
					addToValues(values,s);
				}
				substituteConstant(child,values);
			}else if(inst.equals("if")) {
				doConstantPropagationIf(child,values);
			}else if(inst.equals("while")) {
				doConstantPropagationWhile(child,values);
			}else 
				substituteConstant(child,values);
		}
		//System.out.println(values);
	}
	
	private void doConstantPropagationWhile(IRNode loop, List<Simbol> values) {
		List<Simbol> def = this.getAllDefs(loop);
		this.removeFromValues(values, def);
		doConstantPropagation(loop,values);
		this.removeFromValues(values, def);
	}
	
	private void doConstantPropagationIf(IRNode branch, List<Simbol> values) {
		List<Simbol> def = this.getAllDefs(branch);
		doConstantPropagation(branch.children[0],values);
		List<Simbol> temp_values = new ArrayList<Simbol>(values);
		List<Integer> prev_values = new ArrayList<Integer>();
		for(Simbol s : temp_values)
			prev_values.add(new Integer(s.value));
		doConstantPropagation(branch.children[1],temp_values);
		temp_values = new ArrayList<Simbol>(values);
		for(int i = 0; i < temp_values.size();i++)
			temp_values.get(i).value = prev_values.get(i);
		doConstantPropagation(branch.children[2],temp_values);
		this.removeFromValues(values, def);
	}
	
	private void substituteConstant(IRNode statement,List<Simbol> values) {
		for(IRNode child : statement.children)
			substituteConstant(child,values);
		String inst = statement.getInst();
		if(inst.equals("ldl") || inst.equals("ldp")) {
			Simbol s = statement.children[0].simbol;
			if(values.indexOf(s) != -1) {
				statement.setInst("ldc");
				statement.children[0].setInst(String.valueOf(s.value));
			}
		}
	}
	
	private List<Simbol> getAllDefs(IRNode node){
		List<Simbol> defs = new ArrayList<Simbol>();
		this.getAllDefAux(node, defs);
		return defs;
	}
	
	private void getAllDefAux(IRNode node, List<Simbol> defs){
		for(IRNode child : node.children)
			getAllDefAux(child,defs);
		if(node.getInst().equals("st"))
			this.addToValues(defs, node.children[0].simbol);
	}
	
	private void registerOptimization(IRNode method, Integer registers) throws RegisterAllocationException  {
		IRNode args= method.children[2];
		this.line = -1;
		this.correspondent = method;
		if(method.children[0].children.length == 1)
			this.is_static = false;
		for(IRNode arg : args.children) {
			this.addToList(this.def, arg.simbol);
		}
		this.buildBase(method.children[4]);
		this.setUseDef(this.sucessors[0]);

		System.out.println("");
		System.out.println(method.children[1].children[1].getInst());
		this.livenessAnalysis(registers);
		System.out.println("\n");
		this.print();
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
		if((node.getInst().equals("sta")&& node.children[0].local_var != null) || node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
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
		CFGNode prev = this;
		CFGNode cfgNode = new CFGNode();
		int line = 0;
		for(int i = 0; i < method.children.length;i++) {
			IRNode irNode = method.children[i];
			String inst = irNode.getInst();
			if(inst.equals("while")) {
				line = cfgNode.buildWhile(irNode,line);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}else if(inst.equals("if")) {
				CFGNode suc = null;
				if(i < method.children.length-1)
					suc = new CFGNode();
				line = cfgNode.buildIf(irNode,line,suc);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = null;
				cfgNode = suc;
			}else {
				cfgNode.correspondent = irNode;
				cfgNode.line = line++;
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}
		}
	}
	private int buildWhile(IRNode loop, int line) {
		this.correspondent = loop.children[0];
		this.line = line++;
		CFGNode prev = this;
		CFGNode cfgNode = new CFGNode();
		for(int i = 1; i < loop.children.length;i++) {
			IRNode irNode = loop.children[i];
			String inst = irNode.getInst();
			if(inst.equals("while")) {
				line = cfgNode.buildWhile(irNode,line);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}else if(inst.equals("if")) {
				CFGNode suc = null;
				if(i < loop.children.length-1)
					suc = new CFGNode();
				else
					suc = this;
				line = cfgNode.buildIf(irNode,line,suc);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = null;
				cfgNode = suc;
			}else {
				cfgNode.correspondent = irNode;
				cfgNode.line = line++;
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}
		}
		if(prev != null)	prev.addSucessor(this);
		return line;
	}
	
	private int buildIf(IRNode branch, int line,CFGNode successor) {
		this.correspondent = branch.children[0];
		this.line = line++;
		IRNode if_node = branch.children[1];
		IRNode else_node = branch.children[2];
		
		CFGNode prev = this;
		CFGNode cfgNode = new CFGNode();
		for(int i = 0; i < if_node.children.length;i++) {
			IRNode irNode = if_node.children[i];
			String inst = irNode.getInst();
			if(inst.equals("while")) {
				line = cfgNode.buildWhile(irNode,line);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}else if(inst.equals("if")) {
				CFGNode suc = null;
				if(i < if_node.children.length-1)
					suc = new CFGNode();
				line = cfgNode.buildIf(irNode,line,suc);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = null;
				cfgNode = suc;
			}else {
				cfgNode.correspondent = irNode;
				cfgNode.line = line++;
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}
		}		
		if(successor != null && prev != null)	prev.addSucessor(successor);
		
		prev = this;
		cfgNode = new CFGNode();
		for(int i = 0; i < else_node.children.length;i++) {
			IRNode irNode = else_node.children[i];
			String inst = irNode.getInst();
			if(inst.equals("while")) {
				line = cfgNode.buildWhile(irNode,line);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}else if(inst.equals("if")) {
				CFGNode suc = null;
				if(i < else_node.children.length-1)
					suc = new CFGNode();
				else 
					suc = successor;
				line = cfgNode.buildIf(irNode,line,suc);
				if(prev != null) prev.addSucessor(cfgNode);
				prev = null;
				cfgNode = suc;
			}else {
				cfgNode.correspondent = irNode;
				cfgNode.line = line++;
				if(prev != null) prev.addSucessor(cfgNode);
				prev = cfgNode;
				cfgNode = new CFGNode();
			}
		}		
		if(successor != null && prev != null)	prev.addSucessor(successor);
		
		return line;
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
	
	private void resetVisited(List<CFGNode> stack) {
		for(CFGNode node : stack)
			node.visited = false;
	}
	
	private List<CFGNode> buildStack(CFGNode n){
		List<CFGNode> stack = new ArrayList<CFGNode>();
		buildStackAux(stack,n);
		return stack;
	}
	
	private Integer getMaxDepth(CFGNode node, Simbol s) {
		if(node == null)
			return null;
		node.visited = true;
		int depth = node.line;
		for(CFGNode sucessor : node.sucessors) {
			if(/*sucessor.line <= node.line*/ sucessor.visited)
				continue;
			if(sucessor.in.indexOf(s) == -1)
				continue;
			Integer t = getMaxDepth(sucessor,s);
			/*if(t != null && t > depth)*/
				depth = t;
		}
		return depth;
	}
	
	public void livenessAnalysis(Integer k) throws RegisterAllocationException {
		
		List<CFGNode> stack = buildStack(this);
		// add this in case it is not static
		if(this.is_static == false)
			this.addToList(stack.get(0).use,this.def.get(0));
		boolean completed = true;
		/*
		 * Build use and def lists
		 */
		do {
			completed = true;
			for(CFGNode n : stack) {
				List<Simbol> in_temp = n.in;
				List<Simbol> out_temp = n.out;
				for(CFGNode child : n.sucessors) {
					n.out = this.unionList(n.out,child.in);
				}
				n.in = this.unionList(n.use, this.diffList(n.out, n.def));
				if(!this.compareList(n.in, in_temp) || !this.compareList(n.out, out_temp))
					completed = false;
			}		
		}while(completed == false);
		
		/*
		 * Compute ranges of each variable, from the moment they are defined to the moment they are used, 
		 * keeping coerence between ranges and registers
		 */
		for(int i = stack.size() -1; i >= 0; i--) {
			CFGNode n = stack.get(i);
			for(Simbol s : n.out) {
				for(CFGNode suc : n.sucessors) {
					if(suc.in.indexOf(s) != -1) {
						addToRanges(new LiveRange(s,n.line,suc.line));
				
					}
				}

			}
		}
		
		/*for(LiveRange range : this.ranges) {
			System.out.println(range);
		}*/
		//System.out.println("\n\n\n\n");
		
		/*
		 * Build intergerence graph
		 */
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
		if(k != null) {
			// do not count with register 0, for this
			k = k+1;
			List<LiveRange> stackRange = new ArrayList<LiveRange>();
			/*
			 * The number of registers needs to be greater or equal to the number of arguments of the functions
			 */
			if(k < this.def.size()){
				throw new RegisterAllocationException("Less registers than arguments");
			}
			
			/*
			 * Stack registers for coloring
			 */
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
					throw new RegisterAllocationException("Few registers for interference graph");
				}
			}
			/*
			 * Color registers
			 */
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
					throw new RegisterAllocationException("Few registers for allocations");
				}
			}
			/*
			 * Order registers in correct order, this first and the order the arguments appear
			 */
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
			
			// print for show
			for(LiveRange range : stackRange) {
				System.out.println(range);
			}

			//allocate registers in each statement accordinly to the ranges
			this.allocRegisters(ranges, stack);
		}
		
		
	}
	
	private void allocRegisters(List<LiveRange> ranges, List<CFGNode> stack) {
		
		for(int i = 0; i < stack.size()-1;i++) {
			CFGNode node = stack.get(i);
			Integer line = node.line;
			IRNode irNode = node.correspondent;
			String inst = irNode.getInst();
			if(inst.equals("st") || (inst.equals("sta") && irNode.children[0].local_var != null))
				irNode.children[0].local_var = -1;
			allocRegisters_Aux(ranges,irNode,line);
				
		}		
	}
	
	private void allocRegisters_Aux(List<LiveRange> ranges, IRNode node, int line) {
		for(IRNode child : node.children)
			allocRegisters_Aux(ranges,child,line);
		if(node.getInst().equals("st") || (node.getInst().equals("sta") && node.children[0].local_var != null) || node.getInst().equals("ldl") || node.getInst().equals("ldp")) {
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
			//System.out.println("register => " + register);
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
			if(range.intersectClose(r)) {
				if(range.end < r.end)
					range.end = r.end;
				return;
			}
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
			if(b<e) {
				this.begin = b;
				this.end = e;
			}else {
				this.begin = e;
				this.end = b;
			}
		}
		public boolean intersect(LiveRange range) {
			if(range.begin >= this.begin && range.begin < this.end)
				return true;
			return false;
		}
		
		public boolean intersectClose(LiveRange range) {
			if(range.begin >= this.begin && range.begin <= this.end)
				return true;
			return false;
		}
		public String toString() {
			String str = this.s + "\t\t" + begin + "\t\t" + end +"\t\tregister: " + this.color +"\tinterferences:\t";
			for(LiveRange r : this.interferences)
				str+= r.s + ", ";
			
			return str;
		}
	}


}

