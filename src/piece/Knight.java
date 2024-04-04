package piece;

import main.GameSpace;

public class Knight extends Piece{
    public Knight(int color,int coloumn,int row) {
    	super(color,coloumn,row);
    	if(color==GameSpace.White) {
    		 image=getp("/piece/Knightw.png");
    	}else {
    		image=getp("/piece/Knightb.png");
    	}

    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		 if(within(tarCol,tarRow)){
			 if(Math.abs(tarCol-prevColoumn)*Math.abs((tarRow-prevRow))==2) {
				 if (isvalid(tarCol, tarRow)) {
					 return true;
				 }
			 }
		 }
		return false;
	}
}
