package piece;

import main.GameSpace;
import main.PieceId;

public class Knight extends Piece{
    public Knight(int color,int column,int row) {
    	super(color,column,row);

		Pid = PieceId.KNIGHT;

    	if(color==GameSpace.White) {
    		 image=getp("/piece/Knightw.png");
    	}else {
    		image=getp("/piece/Knightb.png");
    	}

    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		 if(within(tarCol,tarRow)){
			 if(Math.abs(tarCol-prevcolumn)*Math.abs((tarRow-prevRow))==2) {
				 if (isvalid(tarCol, tarRow)) {
					 return true;
				 }
			 }
		 }
		return false;
	}
}
