package piece;

import main.GameSpace;
import main.PieceId;

public class Queen extends Piece{
    public Queen(int color,int column,int row) {
    	super(color,column,row);

		Pid = PieceId.QUEEN;

    	if(color==GameSpace.White) {
    		 image=getp("/piece/queenw.png");
    	}else {
    		image=getp("/piece/queenb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)&& sameSq(tarCol,tarRow)==false ){
			if(isvalid(tarCol,tarRow) &&Math.abs((tarCol-prevcolumn))*Math.abs(tarRow-prevRow)==0&& sameLine(tarCol,tarRow)==false){
				return true;
			}
			else if(Math.abs(tarCol-prevcolumn)==Math.abs(tarRow-prevRow)){
				if(isvalid(tarCol,tarRow)&& sameDig(tarCol,tarRow)==false){
					return true;
				}
			}
		}
		return false;
	}
}
