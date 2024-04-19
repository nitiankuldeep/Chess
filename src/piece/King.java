package piece;

import main.GameSpace;
import main.PieceId;

public class King extends Piece{
    public King(int color,int column,int row) {
    	super(color,column,row);

		Pid = PieceId.KING;

    	if(color==GameSpace.White) {
    		 image=getp("/piece/kingw.png");
    	}else {
    		image=getp("/piece/kingb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)){

			//Move the piece
			if(Math.abs(tarCol-prevcolumn)+Math.abs(tarRow-prevRow)==1||Math.abs(tarCol-prevcolumn)*Math.abs(tarRow-prevRow)==1 ){
				if(isvalid(tarCol,tarRow )) {
					return true;
				}
			}

			//Castling
			if(moved==false){
				//castling right
				if(tarCol == prevcolumn+2 && tarRow == prevRow && sameLine(tarCol,tarRow) == false) {
					for(Piece piece : GameSpace.simPieces) {
						if(piece.column == prevcolumn+3 && piece.row == prevRow && piece.moved == false){
							GameSpace.castlingp = piece;
							return true;
						}
					}
				}

				//castling left
				if(tarCol == prevcolumn-2 && tarRow == prevRow && sameLine(tarCol,tarRow) == false) {
					Piece p[] = new Piece[2];
					for(Piece piece : GameSpace.simPieces) {
						if(piece.column == prevcolumn-3 && piece.row == tarRow){
							p[0] = piece;
						}
						if(piece.column == prevcolumn-4 && piece.row == tarRow) {
							p[1] = piece;
						}
						if(p[0] == null && p[1] != null && p[1].moved == false) {
							GameSpace.castlingp = p[1];
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
