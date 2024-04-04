package main;
import piece.*;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameSpace extends JPanel implements Runnable  {
    public static final int WIDTH =1260;
    public static final int HEIGHT =960;
    final int framePerSecond=60;
    Thread gameThread;
    Board board=new Board();
    Move move=new Move();

    public static ArrayList<Piece> pieces=new ArrayList<>();
    public static ArrayList<Piece> simPieces=new ArrayList<>();
    Piece activep;

    public static final int White =1260;
    public static final int Black =960;
    int currentcolor=White;

   boolean vaildSq;
   boolean canMove;
    public GameSpace() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.white  );
        addMouseListener(move);
        addMouseMotionListener(move);
        setPiece();
        copyPieces(pieces,simPieces);

    }
    public void runGame() {
        gameThread= new Thread(this);
        gameThread.start();
    }
    public void setPiece() {
        pieces.add(new Pawn(Black,0,1));
        pieces.add(new Pawn(Black,1,1));
        pieces.add(new Pawn(Black,2,1));
        pieces.add(new Pawn(Black,3,1));
        pieces.add(new Pawn(Black,4,1));
        pieces.add(new Pawn(Black,5,1));
        pieces.add(new Pawn(Black,6,1));
        pieces.add(new Pawn(Black,7,1));
        pieces.add(new Knight(Black,1,0));
        pieces.add(new Knight(Black,6,0));
        pieces.add(new Rook(Black,0,0));
        pieces.add(new Rook(Black,7,0));
        pieces.add(new Bishop(Black,2,0));
        pieces.add(new Bishop(Black,5,0));
        pieces.add(new Queen(Black,3,0));
        pieces.add(new King(Black,4,0  ));

        pieces.add(new Pawn(White,0,6));
        pieces.add(new Pawn(White,1,6));
        pieces.add(new Pawn(White,2,6));
        pieces.add(new Pawn(White,3,6));
        pieces.add(new Pawn(White,4,6));
        pieces.add(new Pawn(White,5,6));
        pieces.add(new Pawn(White,6,6));
        pieces.add(new Pawn(White,7,6));
        pieces.add(new Knight(White,6,7));
        pieces.add(new Knight(White,1,7));
        pieces.add(new Rook(White,0,7));
        pieces.add(new Rook(White,7,7));
        pieces.add(new Bishop(White,2,7));
        pieces.add(new Bishop(White,5,7));
        pieces.add(new Queen(White,3,7));
        pieces.add(new King(White,4,7));

    }
    private  void changeColour(){
        if(currentcolor==White){
            currentcolor=Black;
        }else{
            currentcolor=White;
        }
        activep=null;
    }

    private void copyPieces(ArrayList<Piece>source,ArrayList<Piece> target) {
        target.clear();
        for(int i=0;i<source.size();i++) {
            target.add(source.get(i));
        }
    }


    private void change() {
     if(move.clicked){
         if (activep==null){
             for (Piece piece:simPieces){
                 if(piece.color==currentcolor&&
                         piece.coloumn==move.x/Board.size&&
                         piece.row==move.y/Board.size){
                       activep=piece;
                 }
             }
         }else {
             simulate();
         }
     }
     if(move.clicked==false){
         if(activep!=null){
             if(vaildSq) {
                 copyPieces(simPieces, pieces);
                 activep.update();
                 changeColour();
             }
             else{
                 copyPieces(pieces,simPieces );
                 activep.resetPosition();
              activep=null;}
         }
     }
    }
public void simulate(){
        canMove=false;
        vaildSq=false;
        copyPieces(pieces,simPieces );
        activep.x=move.x-Board.halfSize;
        activep.y=move.y-Board.halfSize;
        activep.row=activep.getrow(activep.y);
        activep.coloumn=activep.getcol(activep.x);
      if( activep.canMove(activep.coloumn, activep.row )){
          canMove=true;
          if(activep.hitingp!=null){
              simPieces.remove(activep.hitingp.index());
          }
          vaildSq=true;
      }
};

    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g2=(Graphics2D)g1;

        board.make(g2);
        for(Piece p: simPieces) {
            p.draw(g2);
        }
        if (activep!=null) {
            if (canMove) {
                g2.setColor(Color.WHITE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activep.coloumn * Board.size, activep.row * Board.size, Board.size, Board.size);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                activep.draw(g2);
            }

        }
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.setFont(new Font("Book Antiqua",Font.BOLD,35));
        g2.setColor(Color.BLACK);
        if(currentcolor==White){
            g2.drawString("WHITE'S MOVE",980,580);

        }else{
            g2.drawString("BLACK'S MOVE",980,260);
        }
    }


    public void run() {

        double drawInterval=1000000000/framePerSecond;
        double delta =0;
        long lastTime=System.nanoTime();
        long currentTime;
        while(gameThread!=null) {
            currentTime=System.nanoTime();
            delta +=(currentTime-lastTime)/drawInterval;
            lastTime=currentTime;
            if(delta>=1) {
                change();
                repaint();  /*calling paint-component*/
                delta--;
            }
        }


    }



}
