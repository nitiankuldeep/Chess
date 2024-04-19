package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;
import main.GameSpace;
import main.PieceId;

public class Piece {

	public PieceId Pid;
    public BufferedImage image;	
    public int x,y;
    public int column,row,prevcolumn,prevRow;
    public int color;
	public Piece hitingp;
	public  boolean moved, twoStep;
     public Piece(int color,int column,int row) {
    	 this.color=color;
    	 this.column=column;
    	 this.row=row;
    	 x=getX(column);
    	 y=getY(row); 
    	 prevcolumn=column;
    	 prevRow=row;
    	 
     }
     public BufferedImage getp(String imageLocation) {
    	 BufferedImage image=null;
    	 try {
			 System.out.println(imageLocation);
    		 image=ImageIO.read(getClass().getResourceAsStream(imageLocation));
    	 }catch(IOException e) {
    		 e.printStackTrace();
    	 }
    	 return image; 
     }
     
     public int getX(int column ) {
    	 return column*Board.size;
     }
     public int getY(int row ) {
    	 return row*Board.size;
     }
	 public int getcol(int x){
		 return (x+Board.halfSize)/Board.size;
	 }
	public int getrow(int y){
		return (y+Board.halfSize)/Board.size;
	}
	public void  update(){

		 //Checking En Passant
		if(Pid == PieceId.PAWN) {
			if(Math.abs(row - prevRow) == 2) {
				twoStep = true;
			}
		}

		 x=getX(column);
		 y=getY(row);
		 prevcolumn=getcol(x);
		 prevRow=getrow(y);
		 moved=true;
	}
	public boolean within(int tarCol,int tarRow){
		 if(tarCol>-1&&tarCol<8&&tarRow>-1&&tarRow<8){
			 return true;
		 }
		 return false;
	}
	public boolean isvalid(int tarCol,int tarRow){
		 hitingp=hitting(tarCol,tarRow);
		 if(hitingp==null){
			 return true;
		 }else if(hitingp.color!=this.color){
			 return true;
		 }else{
			 hitingp= null;
		 }
		  return false;
	}

public int index(){
		 for(int ind=0;ind<GameSpace.simPieces.size();ind++){
			 if(GameSpace.simPieces.get(ind)==this){
				 return ind;
			 }
		 }
		 return 0;
}

public boolean canMove(int tarCol,int tarRow){
		 return false;
	}

public void resetPosition(){
		 column=prevcolumn;
		 row=prevRow;
		 x=getX(column);
	     y=getY(row);
	 }
public Piece hitting(int tarCol,int tarRow){
for(Piece piece: GameSpace.simPieces) {
	if (tarCol == piece.column && tarRow == piece.row && piece != this) {
		return piece;
	}
 }
return null;
}
public boolean sameSq(int tarCol,int tarRow){
	if(tarCol==prevcolumn&&tarRow==prevRow){
		return true;
	}
	return false;
}
public boolean sameDig(int tarCol ,int tarRow){
		if(tarRow<prevRow) {
			for(int c=prevcolumn-1;c>tarCol;c--){
				int dif=Math.abs(c-prevcolumn);
				for(Piece piece:GameSpace.simPieces){
					if(piece.column==c&&piece.row==prevRow-dif){
						hitingp=piece;
						return  true;
					}
				}
			}
			for(int c=prevcolumn+1;c<tarCol;c++){
				int dif=Math.abs(c-prevcolumn);
				for(Piece piece:GameSpace.simPieces){
					if(piece.column==c&&piece.row==prevRow-dif){
						hitingp=piece;
						return  true;
					}
				}
			}

		} else if (tarRow>prevRow) {
			for(int c=prevcolumn-1;c>tarCol;c--) {
				int dif = Math.abs(c - prevcolumn);
				for (Piece piece : GameSpace.simPieces) {
					if (piece.column == c && piece.row == prevRow + dif) {
						hitingp = piece;
						return true;
					}
				}
			}

			for(int c=prevcolumn+1;c<tarCol;c++) {
				int dif = Math.abs(c - prevcolumn);
				for (Piece piece : GameSpace.simPieces) {
					if (piece.column == c && piece.row == prevRow + dif) {
						hitingp = piece;
						return true;
					}
				}
			}
		}
	return  false;
}

public boolean sameLine(int tarCol,int tarRow) {
	for (int c = prevcolumn-1;c>tarCol;c--){
		for(Piece piece:GameSpace.simPieces){
			if(piece.column==c&&piece.row==tarRow){
				hitingp=piece;
				return true;
			}
		}
	}
	for (int c = prevcolumn+1;c<tarCol;c++){
		for(Piece piece:GameSpace.simPieces){
			if(piece.column==c&&piece.row==tarRow){
				hitingp=piece;
				return true;
			}
		}
	}
	for (int c = prevRow-1;c>tarRow;c--){
		for(Piece piece:GameSpace.simPieces){
			if(piece.row==c&&piece.column==tarCol){
				hitingp=piece;
				return true;
			}
		}
	}
	for (int c = prevRow+1;c<tarRow;c++){
		for(Piece piece:GameSpace.simPieces){
			if(piece.row==c&&piece.column==tarCol){
				hitingp=piece;
				return true;
			}
		}
	}
	return false;
}
public void draw(Graphics2D g2) {
		 g2.drawImage(image,x,y,Board.size,Board.size, null);
    }
}
