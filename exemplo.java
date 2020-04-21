class exemplo {
	
	  int[] row0;
	  int[] row1;
	  int[] row2;
	  int whoseturn;
	  int movesmade;
	  int[] pieces;
	  
	  public  exemplo(int t) {
		  whoseturn = t;
	  }

	// Initializes a Tic Tac Toe object.
	  public boolean init() {
	  	int[] g;
	  	int i;
	  	exemplo e = new exemplo(2);
	    row0 = new int[3];
	    row1 = new int[3];
	    row2 = new int[3];
	    pieces = new int[2];
	    pieces[0] = 1+3;
	    pieces[1] = 2;
	    whoseturn = 0;
	    movesmade = 0;
	    g = new int[7];
	    i = g[7];
	    e.init();
	    return true;
	  }



}