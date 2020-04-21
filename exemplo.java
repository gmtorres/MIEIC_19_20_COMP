class exemplo {
	
	  int[] row0;
	  int[] row1;
	  int[] row2;
	  int whoseturn;
	  int movesmade;
	  int[] pieces;

	// Initializes a Tic Tac Toe object.
	  public boolean init() {
	    row0 = new int[3];
	    row1 = new int[3];
	    row2 = new int[3];
	    pieces = new int[2];
	    pieces[0] = 1+3;
	    pieces[1] = 2;
	    whoseturn = 0;
	    movesmade = 0;
	    return true;
	  }

	  public int[] getRow0(){
	    return row0;
	  }

	  public int[] getRow1(){
	    return row1;
	  }
	  public int[] getRow2(){
	    return row2;
	  }
	  
	  public boolean inbounds(int row, int column) {
	    boolean in;
	    if (row < 0)
	      in =  false;
	    else if (column < 0)
	      in =  false;
	    else if (2 < row) 
	      in =  false;
	    else if (2 < column) 
	      in = false;
	    else in = true;
	    return in;
	  }



}