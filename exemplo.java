class exemplo {
	
	  int[] row0;
	  int[] row1;
	  int[] row2;
	  int whoseturn;
	  int movesmade;
	  int[] pieces;
	  exemplo pach;

	// Initializes a Tic Tac Toe object
	  
	  public exemplo(int i) {
	  }
	  
	  public boolean init() {
	    pach = new exemplo(1);
	    this.getRow1(1);
	    return true;
	  }

	  public int[] getRow0(){
	    return row0;
	  }

	  public int[] getRow1(int i){
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