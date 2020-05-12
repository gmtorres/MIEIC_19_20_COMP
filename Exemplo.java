
public class Exemplo {
	
	public static void main(String[] args) 
    { 
		boolean b;
		int i;
		i = 0;
		
		b = !( !(i<5 && !(3 < i && 2 < i))  && !(!(7 < i && 4 < i) && 5<i) );
		
		//b = !(!(!(i < 5) && i<2) && !(i < 3));
	}
	
}
