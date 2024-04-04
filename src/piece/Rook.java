package piece;

import main.GameSpace;

public class Rook extends Piece{
    public Rook(int color,int coloumn,int row) {
    	super(color,coloumn,row);
    	if(color==GameSpace.White) {
    		 image=getp("/piece/rookw.png");
    	}else {
    		image=getp("/piece/rookb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)&&sameSq(tarCol,tarRow)==true){
				if(isvalid(tarCol,tarRow)&&sameLine(tarCol,tarRow)==false){
					return true;
				}
		}
		return false;
	}
}
