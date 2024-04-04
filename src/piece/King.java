package piece;

import main.GameSpace;

public class King extends Piece{
    public King(int color,int coloumn,int row) {
    	super(color,coloumn,row);
    	if(color==GameSpace.White) {
    		 image=getp("/piece/kingw.png");
    	}else {
    		image=getp("/piece/kingb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)){
			if(Math.abs(tarCol-prevColoumn)+Math.abs(tarRow-prevRow)==1||Math.abs(tarCol-prevColoumn)*Math.abs(tarRow-prevRow)==1 ){
				if(isvalid(tarCol,tarRow )) {
					return true;
				}
			}
		}
		return false;
	}
}
