
public class Exemplo {
	
	public static void main(String[] args) 
    { 
		boolean b;
		int i;
		i = 0;
		
		b = 3 < i && !(i < 5 && !(3<i && !(i<2 && 9 < i)));
		
		//b = !(!(!(i < 5) && i<2) && !(i < 3));
	}
	
}
