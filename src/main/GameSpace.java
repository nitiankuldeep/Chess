package main;
import piece.*;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameSpace extends JPanel implements Runnable  {
    public static final int WIDTH =1144;
    public static final int HEIGHT =832;
    final int framePerSecond=60;
    Thread gameThread;
    Board board=new Board();
    Move move=new Move();

    public static ArrayList<Piece> pieces=new ArrayList<>();
    public static ArrayList<Piece> simPieces=new ArrayList<>();
    ArrayList<Piece> promoPiece = new ArrayList<>();
    Piece activep, checkingp;
    public static Piece castlingp;

    public static final int White =1080;
    public static final int Black =832;
    int currentcolor=White;

   boolean validSq;
   boolean canMove;
   boolean promote;
   boolean gameOver;
    public GameSpace() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.lightGray);
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
    private  void changeColour() {

        if(currentcolor==White){
            currentcolor=Black;
            //Reset black 2 step status
            for(Piece piece : pieces) {
                if(piece.column == Black) {
                    piece.twoStep = false;
                }
            }
        }
        else{
            currentcolor=White;
            //Reset white 2 step status
            for(Piece piece : pieces) {
                if(piece.column == White) {
                    piece.twoStep = false;
                }
            }
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

        if(promote) {
            promoting();
        }
        else if(gameOver == false) {
            if(move.clicked) {
                if (activep == null) {
                    for (Piece piece : simPieces) {
                        if (piece.color == currentcolor &&
                                piece.column == move.x / Board.size &&
                                piece.row == move.y / Board.size) {
                            activep = piece;
                        }
                    }
                } else {
                    simulate();
                }
            }
            if(move.clicked==false){
                if(activep!=null){
                    if(validSq) {

                        //Move confirmed


                        copyPieces(simPieces, pieces);
                        activep.update();
                        if(castlingp != null) {
                            castlingp.update();
                        }

                        if(isKingCheck() && isCheckmate()) {
                            gameOver = true;
                        }
                        else {
                            if (canPromote()) {
                                promote = true;
                            } else {
                                changeColour();
                            }
                        }


                    }
                    else{
                        copyPieces(pieces,simPieces );
                        activep.resetPosition();
                        activep=null;
                    }
                }
            }
        }
    }

    private boolean canPromote() {

        if(activep.Pid == PieceId.PAWN) {
            if(currentcolor == White && activep.row == 0 || currentcolor == Black && activep.row == 7) {
                promoPiece.clear();
                promoPiece.add(new Rook(currentcolor,9,2));
                promoPiece.add(new Knight(currentcolor,9,3));
                promoPiece.add(new Bishop(currentcolor,9,4));
                promoPiece.add(new Queen(currentcolor,9,5));
                return true;
            }
        }
        return false;
    }

    private void promoting() {
        if(move.clicked) {
            for(Piece piece : promoPiece) {
                if(piece.column == move.x/Board.size && piece.row == move.y/Board.size) {
                    switch(piece.Pid) {
                        case ROOK: simPieces.add(new Rook(currentcolor, activep.column, activep.row));
                            break;
                        case KNIGHT: simPieces.add(new Knight(currentcolor, activep.column, activep.row));
                            break;
                        case BISHOP: simPieces.add(new Bishop(currentcolor, activep.column, activep.row));
                            break;
                        case QUEEN: simPieces.add(new Queen(currentcolor, activep.column, activep.row));
                            break;
                        default:
                            break;
                    }
                    simPieces.remove(activep.index());
                    copyPieces(simPieces,pieces);
                    activep = null;
                    promote = false;
                    changeColour();
                }
            }
        }
    }

    private boolean isKingCheck() {
        Piece king = getKing(true);

        if(activep.canMove(king.column, king.row)) {
            checkingp = activep;
            return true;
        }
        else {
            checkingp = null;
        }
        return false;
    }

    private Piece getKing(boolean opponent) {
        Piece king = null;
        for(Piece piece : simPieces) {
            if(opponent) {
                if(piece.Pid == PieceId.KING && piece.color != currentcolor) {
                    king = piece;
                }
            }
            else {
                if(piece.Pid == PieceId.KING && piece.color == currentcolor) {
                    king = piece;
                }
            }
        }
        return king;
    }

    private boolean isCheckmate() {

        Piece king = getKing(true);
        if(kingCanMove(king)) {
            return false;
        }
        else {
            int colDiff = Math.abs(checkingp.column - king.column);
            int rowDiff = Math.abs(checkingp.row - king.row);

            if(colDiff == 0) {
                if(checkingp.row < king.row) {
                    for(int row = checkingp.row; row < king.row; row++) {
                        for(Piece piece : simPieces) {
                            if(piece != king && piece.color != currentcolor && piece.canMove(checkingp.column,row)) {
                                return false;
                            }
                        }
                    }
                }
                if(checkingp.row > king.row) {
                    for(int row = checkingp.row; row > king.row; row--) {
                        for(Piece piece : simPieces) {
                            if(piece != king && piece.color != currentcolor && piece.canMove(checkingp.column,row)) {
                                return false;
                            }
                        }
                    }
                }
            }
            else if(rowDiff == 0) {
                if(checkingp.column < king.column) {
                    for(int col = checkingp.column; col < king.row; col++) {
                        for(Piece piece : simPieces) {
                            if(piece != king && piece.color != currentcolor && piece.canMove(col,checkingp.row)) {
                                return false;
                            }
                        }
                    }
                }
                if(checkingp.column > king.column) {
                    for(int col = checkingp.column; col > king.row; col--) {
                        for(Piece piece : simPieces) {
                            if(piece != king && piece.color != currentcolor && piece.canMove(col,checkingp.row)) {
                                return false;
                            }
                        }
                    }
                }
            }
            else if(colDiff == rowDiff) {
                if(checkingp.row < king.row) {
                    if(checkingp.column < king.column) {
                        for(int col = checkingp.column, row = checkingp.row; col < king.column; col++, row++) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentcolor && piece.canMove(col,row)) {
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingp.column > king.column) {
                        for(int col = checkingp.column, row = checkingp.row; col > king.column; col--, row++) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentcolor && piece.canMove(col,row)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            else {
                if(checkingp.row > king.row) {
                    if(checkingp.column < king.column) {
                        for(int col = checkingp.column, row = checkingp.row; col < king.column; col++, row--) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentcolor && piece.canMove(col,row)) {
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingp.column > king.column) {
                        for(int col = checkingp.column, row = checkingp.row; col > king.column; col--, row--) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentcolor && piece.canMove(col,row)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean kingCanMove(Piece king) {

        if(isValidMove(king,-1,-1)) {return true;}
        if(isValidMove(king,0,-1)) {return true;}
        if(isValidMove(king,1,-1)) {return true;}
        if(isValidMove(king,-1,0)) {return true;}
        if(isValidMove(king,1,0)) {return true;}
        if(isValidMove(king,-1,1)) {return true;}
        if(isValidMove(king,0,1)) {return true;}
        if(isValidMove(king,1,1)) {return true;}

        return false;
    }

    private boolean isValidMove(Piece king, int colPlus, int rowPlus) {
        boolean isValidMove = false;
        king.column += colPlus;
        king.row +=rowPlus;

        if(king.canMove(king.column, king.row)) {
            if(king.hitingp != null) {
                simPieces.remove(king.hitingp.index());
            }
            if(isIlegal(king) == false) {
                isValidMove = true;
            }
        }

        king.resetPosition();
        copyPieces(pieces,simPieces);

        return isValidMove;
    }
    private boolean isIlegal(Piece king) {
        if(king.Pid == PieceId.KING) {
            for(Piece piece : simPieces) {
                if(piece != king && piece.color != king.color && piece.canMove(king.column, king.row)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean opponentCanCaptureKing() {
        Piece king = getKing(false);
        for(Piece piece : simPieces) {
            if(piece.color != king.color && piece.canMove(king.column, king.row)) {
                return true;
            }
        }
        return false;
    }

    private void checkCastle() {

        if(castlingp != null) {
            if(castlingp.column == 0) {
                castlingp.column += 3;
            }
            else if(castlingp.column == 7) {
                castlingp.column -= 2;
            }
            castlingp.x = castlingp.getX(castlingp.column);
        }
    }

public void simulate(){
        canMove=false;
        validSq=false;

        copyPieces(pieces,simPieces );

        if(castlingp != null) {
            castlingp.column = castlingp.prevcolumn;
            castlingp.x = castlingp.getX(castlingp.column);
            castlingp = null;
        }

        activep.x = move.x-Board.halfSize;
        activep.y = move.y-Board.halfSize;
        activep.row = activep.getrow(activep.y);
        activep.column = activep.getcol(activep.x);

      if( activep.canMove(activep.column, activep.row )) {
          canMove = true;
          if(activep.hitingp != null){
              simPieces.remove(activep.hitingp.index());
          }

          checkCastle();

          if(isIlegal(activep) == false && opponentCanCaptureKing() == false) {
              validSq = true;
          }

          validSq=true;
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
                if(isIlegal(activep) || opponentCanCaptureKing()) {
                    g2.setColor(Color.RED );
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activep.column * Board.size, activep.row * Board.size, Board.size, Board.size);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    validSq=false;
                }
                else{
                    g2.setColor(Color.WHITE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activep.column * Board.size, activep.row * Board.size, Board.size, Board.size);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }

                activep.draw(g2);
            }
        }

        //Status Messages
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.setFont(new Font("Book Antiqua",Font.BOLD,26));
        g2.setColor(Color.BLACK);

        if(promote) {
            g2.drawString("PROMOTE TO",890,150);
            for(Piece piece: promoPiece) {
                g2.drawImage(piece.image, piece.getX(piece.column), piece.getY(piece.row), Board.size, Board.size, null);
            }
        }
        else if(gameOver) {
            String s = "";
            if(currentcolor == White) {
                s = "!_White Wins_!";
            }
            else {
                s ="!_Black Wins_!";
            }
            g2.setFont(new Font("Arial",Font.PLAIN, 90));
            g2.setColor(Color.GREEN);
            g2.drawString(s,200,420);
        }
        else{
            if(currentcolor==Black){
                g2.drawString("BLACK'S MOVE",880,260);
                if(checkingp != null && checkingp.color == White) {
                    g2.setColor(Color.red);
                    g2.drawString("KING IS",880,100);
                    g2.drawString("IN CHECK",880,150);
                }

            }else{
                g2.drawString("WHITE'S MOVE",880,580);
                if(checkingp != null && checkingp.color == Black) {
                    g2.setColor(Color.red);
                    g2.drawString("KING IS",880,650);
                    g2.drawString("IN CHECK!!",880,700);
                }

            }
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
