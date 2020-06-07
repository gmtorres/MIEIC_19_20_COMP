class Exemplo {

	public static void main(String args[]) {
		
		float a;
		int b;
		b = 3;
		a = b;
		
	}
	
	public float sqrt(int a){
		float n;
		float x;
		float l;
		float root;
		n = a;
		x = a;
		l = 0.01f;
		root = 0.0f;
		
		while(this.abs(root-x) > l){
			io.println(root);
			io.println(x);
			io.println(0.5f * (x + (n/x)));
			x = root;
			root = (0.5f * (x + (n/x)));
		}
		return root;
	}
	
	public float abs(float v){
		if(v < 0.0f)
			v = 0.0f - v;
		else{}
		return v;
	}
}
