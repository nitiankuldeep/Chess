package piece;

import main.GameSpace;
import main.PieceId;

public class Pawn extends Piece{
    public Pawn(int color,int column,int row) {
    	super(color,column,row);

		Pid = PieceId.PAWN;

    	if(color==GameSpace.White) {
    		 image=getp("/piece/pawnw.png");
    	}
		else {
    		image=getp("/piece/pawnb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(isvalid(tarCol, tarRow) ){
			int move;
			if(color==GameSpace.White)
				move=-1;
			else
				move=1;
			hitingp=hitting(tarCol, tarRow);
			if(tarCol==prevcolumn&&tarRow==prevRow+move&&hitingp==null){
				return true;
			}

			if(tarCol==prevcolumn&&tarRow==prevRow+move*2&&hitingp==null&&moved==false&&sameLine(tarCol,tarRow)==false){
				return true;
			}
			if(Math.abs(tarCol-prevcolumn)==1 && tarRow==prevRow+move && hitingp!=null){
				return  true;
			}

			//En Passant
			if(Math.abs(tarCol-prevcolumn)==1 && tarRow==prevRow+move) {
				for(Piece piece : GameSpace.simPieces) {
					if(piece.column == tarCol && piece.row == prevRow && piece.twoStep == true) {
						hitingp = piece;
						return  true;
					}
				}
			}
		}
		return false;
	}
}
