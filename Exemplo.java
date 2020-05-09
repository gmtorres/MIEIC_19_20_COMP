
public class Exemplo {
	
	
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
	
	public boolean eq(int a, int b) {
		return (!this.lt(a, b) && !this.lt(b, a));
	}
	
	public boolean ne(int a, int b) {
		return (!this.eq(a, b));
	}
	
    public boolean lt(int a, int b) {
		return (a < b);
    }
    
    public boolean gt(int a, int b) {
		return (!this.le(a, b));
    }
    
    public boolean ge(int a, int b) {
		return !(!this.gt(a, b) && !this.eq(a, b));
	}
	
	public boolean le(int a, int b) {
		return !(!this.lt(a, b) && !this.eq(a, b));
    }
	
	public static void main(String[] args) 
    { 
		int i;
		boolean a;
		boolean b;
		i = 0;
		a = true;
		b = true;
		
		if(!(a && b)){
			i = 2;
		}else{
			i = 3;
		}
	}
	
}
