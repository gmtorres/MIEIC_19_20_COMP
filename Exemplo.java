
public class Exemplo {
	
	public static void main(String[] args) 
    { 
		boolean b;
		int i;
		i = 0;
		
		b = !( !(i<5 && i<2) && !(i<5) );
		
		//b = !(!(!(i < 5) && i<2) && !(i < 3));
	}
	
}
