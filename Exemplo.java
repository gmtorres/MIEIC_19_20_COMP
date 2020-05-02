
public class Exemplo {
	
	public static void main(String[] args) 
    { 
		int d;
		d = 1;
		while(d < 16){
			io.println(this.fibbonaci(d));
			d = d + 1;
		}
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
