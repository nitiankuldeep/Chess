package piece;

import main.GameSpace;

public class Queen extends Piece{
    public Queen(int color,int coloumn,int row) {
    	super(color,coloumn,row);
    	if(color==GameSpace.White) {
    		 image=getp("/piece/queenw.png");
    	}else {
    		image=getp("/piece/queenb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)&&sameSq(tarCol,tarRow)==true){
			if(isvalid(tarCol,tarRow)&&sameLine(tarCol,tarRow)==false){
				return true;
			}
		}
		if(Math.abs(tarCol-prevColoumn)==Math.abs(tarRow-prevRow)){
			if(isvalid(tarCol,tarRow)&& sameDig(tarCol,tarRow)==false){
				return true;
			}
		}

		return false;
	}
}
