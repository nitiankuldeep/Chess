package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;
import main.GameSpace;

public class Piece {
 
    public BufferedImage image;	
    public int x,y;
    public int coloumn,row,prevColoumn,prevRow;
    public int color;
	public Piece hitingp;
	public  boolean moved;
     public Piece(int color,int coloumn,int row) {
    	 this.color=color;
    	 this.coloumn=coloumn;
    	 this.row=row;
    	 x=getX(coloumn);
    	 y=getY(row); 
    	 prevColoumn=coloumn;
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
     
     public int getX(int coloumn ) {
    	 return coloumn*Board.size;
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
		 x=getX(coloumn);
		 y=getY(row);
		 prevColoumn=getcol(x);
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
		 coloumn=prevColoumn;
		 row=prevRow;
		 x=getX(coloumn);
	     y=getY(row);
	 }
public Piece hitting(int tarCol,int tarRow){
for(Piece piece: GameSpace.simPieces) {
	if (tarCol == piece.coloumn && tarRow == piece.row && piece != this) {
		return piece;
	}
 }
return null;
}
public boolean sameSq(int tarCol,int tarRow){
	if(Math.abs(tarCol-prevColoumn)==0||Math.abs(tarRow-prevRow)==0){
		return true;
	}
	return false;
}
public boolean sameDig(int tarCol ,int tarRow){
		if(tarRow<prevRow) {
			for(int c=prevColoumn-1;c>tarCol;c--){
				int dif=Math.abs(c-prevColoumn);
				for(Piece piece:GameSpace.simPieces){
					if(piece.coloumn==c&&piece.row==prevRow-dif){
						hitingp=piece;
						return  true;
					}
				}
			}
			for(int c=prevColoumn+1;c<tarCol;c++){
				int dif=Math.abs(c-prevColoumn);
				for(Piece piece:GameSpace.simPieces){
					if(piece.coloumn==c&&piece.row==prevRow-dif){
						hitingp=piece;
						return  true;
					}
				}
			}

		} else if (tarRow>prevRow) {
			for(int c=prevColoumn-1;c>tarCol;c--) {
				int dif = Math.abs(c - prevColoumn);
				for (Piece piece : GameSpace.simPieces) {
					if (piece.coloumn == c && piece.row == prevRow + dif) {
						hitingp = piece;
						return true;
					}
				}
			}

			for(int c=prevColoumn+1;c<tarCol;c++) {
				int dif = Math.abs(c - prevColoumn);
				for (Piece piece : GameSpace.simPieces) {
					if (piece.coloumn == c && piece.row == prevRow + dif) {
						hitingp = piece;
						return true;
					}
				}
			}
		}
	return  false;
}
public boolean sameLine(int tarCol,int tarRow) {
	for (int c = prevColoumn-1;c>tarCol;c--){
		for(Piece piece:GameSpace.simPieces){
			if(piece.coloumn==c&&piece.row==tarRow){
				hitingp=piece;
				return true;
			}
		}
	}
	for (int c = prevColoumn+1;c<tarCol;c++){
		for(Piece piece:GameSpace.simPieces){
			if(piece.coloumn==c&&piece.row==tarRow){
				hitingp=piece;
				return true;
			}
		}
	}
	for (int c = prevRow-1;c>tarRow;c--){
		for(Piece piece:GameSpace.simPieces){
			if(piece.row==c&&piece.coloumn==tarCol){
				hitingp=piece;
				return true;
			}
		}
	}
	for (int c = prevRow+1;c<tarRow;c++){
		for(Piece piece:GameSpace.simPieces){
			if(piece.row==c&&piece.coloumn==tarCol){
				hitingp=piece;
				return true;
			}
		}
	}
	return false;
}
public void draw(Graphics2D g2) {
    	g2.drawImage(image,x,y,image.getWidth(),image.getHeight(), null);
    }
}
