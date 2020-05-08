
public class Exemplo {
	
	public static void main(String[] args) 
    { 
		int d;
		d = 1;
		Exemplo e;
		e = new Exemplo();
		while(d < 16){
			io.println(e.fibbonaci(d));
			d = d + 1;
		}
	}
	
	public Exemplo getInst() {
		return new Exemplo();
	}
	
	public int fibbonaci(int n){

		int r;
		if(n < 3)
			r = 1;
		else
			r = fibbonaci(n-2) + fibbonaci(n-1);
		return r;
	}
}
