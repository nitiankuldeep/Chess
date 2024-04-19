package piece;

import main.Board;
import main.GameSpace;
import main.PieceId;

public class Rook extends Piece{

    public Rook(int color,int column,int row) {
    	super(color,column,row);

		Pid = PieceId.ROOK;

    	if(color==GameSpace.White) {
    		 image=getp("/piece/rookw.png");
    	}else {
    		image=getp("/piece/rookb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow) && sameSq(tarCol,tarRow)==false){
				if(isvalid(tarCol,tarRow) &&Math.abs((tarCol-prevcolumn))*Math.abs(tarRow-prevRow)==0 &&sameLine(tarCol,tarRow)==false){
					return true;
				}
		}
		return false;
	}
}
