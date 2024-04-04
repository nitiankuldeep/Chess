package piece;
import main.GameSpace;
import main.Main;

public class Bishop extends Piece{
    public Bishop(int color,int coloumn,int row) {
    	super(color,coloumn,row);
    	if(color==GameSpace.White) {
    		 image=getp("/piece/bishopw.png");
    	}else {
    		image=getp("/piece/bishopb.png");
    	}
    }

	@Override
	public boolean canMove(int tarCol, int tarRow) {
		if(within(tarCol,tarRow)){
			if(Math.abs(tarCol-prevColoumn)==Math.abs(tarRow-prevRow)){
				if(isvalid(tarCol,tarRow)&& sameDig(tarCol,tarRow)==false){
					return true;
				}
			}
		}
		return  false;
	}
}

