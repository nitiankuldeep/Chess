package piece;
import main.GameSpace;
import main.Main;
import main.PieceId;

public class Bishop extends Piece{
    public Bishop(int color,int column,int row) {
    	super(color,column,row);

		Pid = PieceId.BISHOP;

    	if(color==GameSpace.White) {
    		 image=getp("/piece/bishopw.png");
    	}else {
    		image=getp("/piece/bishopb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)&&sameSq(tarCol,tarRow)==false){
			if(Math.abs(tarCol-prevcolumn)==Math.abs(tarRow-prevRow)){
				if(isvalid(tarCol,tarRow)&& sameDig(tarCol,tarRow)==false){
					return true;
				}
			}
		}
		return  false;
	}
}

