package piece;

import main.GameSpace;

public class Pawn extends Piece{
    public Pawn(int color,int coloumn,int row) {
    	super(color,coloumn,row);
    	if(color==GameSpace.White) {
    		 image=getp("/piece/pawnw.png");
    	}else {
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
			if(tarCol==prevColoumn&&tarRow==prevRow+move&&hitingp==null){
				return true;
			}

			if(tarCol==prevColoumn&&tarRow==prevRow+move*2&&hitingp==null&&moved==false&&sameLine(tarCol,tarRow)==false){
				return true;
			}
			if(Math.abs(tarCol-prevColoumn)==1 && tarRow==prevRow+move && hitingp!=null){
				return  true;
			}
		}
		return false;
	}
}
