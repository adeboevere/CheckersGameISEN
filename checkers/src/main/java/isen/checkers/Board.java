package isen.checkers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel {
    /*
     * white cell = -1;
     * empty black cell = 0;
     * black = 1;
     * white = 2
     * black queen = 3;
     * white queen = 4;
     */

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static int size = 8;
    static int width = 80;
    int init = 0;
    int[][] array;
    Color beige = new Color(221, 221, 179);
    Color brown = new Color(162, 112, 4);
    Actor player = null;
    Actor otherPlayer = null;
    int whitePieces = 0;
    int blackPieces = 0;

    boolean isWhiteMove = true;
    int startX;
    int startY;

    int prevX;
    int prevY;

    int endX;
    int endY;

    boolean capture = false;
    boolean prevCapture;

    public static void main(String[] args) {

        JFrame jFrame = new JFrame();

        Board board = new Board();

        board.setPreferredSize(new Dimension(720, 640));
        jFrame.add(board);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        //jFrame.setResizable(false);
        board.setup();
        board.play();

    }

    public void play() {
        player = new Player(true);
        otherPlayer = new Player(false);

        if (player instanceof Player || otherPlayer instanceof Player) {
            mouseListen();
            while (!invokeGameOver()){
                repaint();
            }
        }

    }

    public static int[][] fill(int[][] array, int val) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = val;
            }
        }
        return array;
    }

    public void mouseListen() {

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent me) {

                startX = me.getX() / width;
                startY = me.getY() / width;
            }

            public void mouseReleased(MouseEvent me) {

                endX = me.getX() / width;
                endY = me.getY() / width;

                /*
                 * Start:
                 * * * startY, startX
                 * End:
                 * * * endY, endX
                 */

                if (startX != endX || startY != endY) {

                    try {

                        if (init == 0) {
                            prevCapture = capture;
                            capture = checkCapture();

                            if (player.valid(array, startY, startX, endY, endX, capture)
                                    && player.isWhite == isWhiteMove) {
                                move();

                                if (capture && checkCapture(endY, endX)) {
                                    init++;
                                } else {
                                    invertMove();
                                    init = 0;
                                }
                            }

                        }
                        if (init > 0) {
                            if (capture && checkCapture(prevY, prevX)) {
                                if (prevX == startX && prevY == startY
                                        && player.valid(array, startY, startX, endY, endX, capture)
                                        && player.isWhite == isWhiteMove) {

                                    move();

                                    if (!capture || !checkCapture(endY, endX)) {
                                        invertMove();
                                        init = 0;
                                    }
                                }
                            }
                        }


                    } catch (ArrayIndexOutOfBoundsException e) {
                        //System.out.println("ArrayIndexOutOfBoundsException");
                    }

                }

                /*if (whitePieces <=0 || blackPieces <= 0) {
                    repaint();
                    invokeGameOver();
                }
                else{
                    repaint();
                }*/
            }
        });


    }

    public boolean invokeGameOver() {
        if (whitePieces == 0){
            System.out.println("black won");
            displayImage("black_won.jpg");
            return true;
        }
        else if (blackPieces == 0){
            System.out.println("white won");
            displayImage("white_won.jpg");
            return true;
        }
        return false;
    }

    public void displayImage(String image){
        try {
            System.out.println("displaying");
            BufferedImage myPicture = ImageIO.read(new File(image));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));

            this.setLayout(new BorderLayout());
            this.add(picLabel, BorderLayout.CENTER);

        } catch (IOException e) {
            System.out.println("IOException, but you won");
        }
    }

    public void move() {
        array[endY][endX] = array[startY][startX];
        array[startY][startX] = 0;
        if (endY == 0 && array[endY][endX] == 2){
            array[endY][endX] = 4;
        }
        else if (endY == 7 && array[endY][endX] == 1){
            array[endY][endX] = 3;
        }

        decrementPieces(capture);
        prevX = endX;
        prevY = endY;
    }

    public void decrementPieces(boolean capture) {

        if (capture) {
            if (isWhiteMove) {
                blackPieces--;
            } else {
                whitePieces--;
            }
        }

    }

    public void invertMove() {
        //invert move if successful

        isWhiteMove = !isWhiteMove;

        Actor temp = player;
        player = otherPlayer;
        otherPlayer = temp;
    }

    public boolean checkCapture() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (sameColor(array, i, j)){
                    if ((array[i][j] == 1 || array[i][j] == 2) && checkCapture(i, j)){

                        return true;
                    }
                    else if ((array[i][j] == 3 || array[i][j] == 4) && checkQueenCapture(i, j)){

                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean checkQueenCapture(int i, int j) {
        
        int row1, row2, col1, col2;
        boolean f1 = true, f2 = true, f3 = true, f4 = true;

        int count = 0;
        //1
        while(f1) {

            row1 = i - 1 - count;
            row2 = i - 2 - count;
            col1 = j - 1 - count;
            col2 = j - 2 - count;

            count++;
            if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {
                return true;
            } else if (inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && array[row2][col2] == 0) {
                //pass
            }
            else if( inBounds(row1, row2, col1, col2) &&  array[row1][col1] == 0 && oppositeColor(array, row2, col2)){
                //pass
            }
            else {
                f1 = false;
            }
        }

        count = 0;
        //2
        while(f2) {

            row1 = i - 1 - count;
            row2 = i - 2 - count;
            col1 = j + 1 + count;
            col2 = j + 2 + count;

            count++;
            if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {
                return true;
            } else if (inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && array[row2][col2] == 0) {
                //pass
            }
            else if(inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && oppositeColor(array, row2, col2)){
                //pass
            }
            else {
                f2 = false;
            }
        }

        //3
        count = 0;
        while(f3) {

            row1 = i + 1 + count;
            row2 = i + 2 + count;
            col1 = j + 1 + count;
            col2 = j + 2 + count;

            count++;

            if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {
                return true;
            }
            else if (inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && array[row2][col2] == 0) {
                //pass
            }
            else if(inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && oppositeColor(array, row2, col2)){
                //pass
            } else {
                f3 = false;
            }
        }

        count = 0;
        //4
        while(f4){

            row1 = i + 1 + count;
            row2 = i + 2 + count;
            col1 = j - 1 - count;
            col2 = j - 2 - count;

            count++;
            if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {
                return true;
            }
            else if(inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && array[row2][col2] == 0){
                //pass
            }
            else if(inBounds(row1, row2, col1, col2) && array[row1][col1] == 0 && oppositeColor(array, row2, col2)){
                //pass
            }
            else{
                f4 = false;
            }
        }

        return false;
    }

    public boolean checkCapture(int i, int j) {

        int row1, row2, col1, col2;

        row1 = i - 1;
        row2 = i - 2;
        col1 = j - 1;
        col2 = j - 2;

        if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {

            return true;
        }

        row1 = i - 1;
        row2 = i - 2;
        col1 = j + 1;
        col2 = j + 2;

        if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {

            return true;
        }

        row1 = i + 1;
        row2 = i + 2;
        col1 = j + 1;
        col2 = j + 2;


        if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {

            return true;
        }

        row1 = i + 1;
        row2 = i + 2;
        col1 = j - 1;
        col2 = j - 2;

        if (inBounds(row1, row2, col1, col2) && oppositeColor(array, row1, col1) && array[row2][col2] == 0) {

            return true;
        }

        return false;
    }

    private boolean inBounds(int row1, int row2, int col1, int col2) {
        if (row1 < 0 || row2 < 0 || col1 < 0 || col2 < 0) {
            return false;
        }
        if (row1 >= size || row2 >= size || col1 >= size || col2 >= size) {
            return false;
        }
        return true;
    }

    public void setup() {

        whitePieces = 12;
        blackPieces = 12;

        //create empty
        array = new int[size][size];

        //empty
        array = fill(array, 0);

        //black
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (array[i][j] != -1) {
                    setBlack(i, j);
                }
            }
        }

        //white
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (array[i][j] != -1) {
                    setWhite(i, j);
                }
            }
        }
    }

    public void setBlack(int i, int j) {
        array[i][j] = 1;
    }


    public void setWhite(int i, int j) {
        array[i][j] = 2;
    }

    /**
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //draw grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        g.setColor(beige);
                        array[i][j] = -1;
                    } else {
                        g.setColor(brown);

                    }
                } else {
                    if (j % 2 == 0) {
                        g.setColor(brown);
                    } else {
                        g.setColor(beige);
                        array[i][j] = -1;
                    }
                }

                g.fillRect(j * width, i * width, width, width);

                //draw ints
                if (array[i][j] == 1) {
                    drawBlack(g, i, j);
                } else if (array[i][j] == 2) {
                    drawWhite(g, i, j);
                }
                else if (array[i][j] == 3) {
                    drawBlackQueen(g, i, j);
                }
                else if (array[i][j] == 4) {
                    drawWhiteQueen(g, i, j);
                }
            }
        }


        g.setColor(new Color(0x923F13));
        g.fillRect(size * width, 0, width, size * width);

        g.setColor(new Color(41, 189, 158));
        if (isWhiteMove) {

            g.fillOval(8 * width + 6, 4 * width + 6, width - 12, width - 12);

        } else {

            g.fillOval(8 * width + 6, 3 * width + 6, width - 12, width - 12);
        }

        g.setFont(new Font("Arial", 1, 50));
        g.setColor(Color.black);
        g.drawString(String.valueOf(blackPieces), 8 * width + 10, width);
        g.drawString(String.valueOf(whitePieces), 8 * width + 10, 7 * width);
        drawBlack(g, 3, 8);
        g.drawLine(size * width, size * width / 2, (size + 1) * width, size * width / 2);
        drawWhite(g, 4, 8);


    }

    private void drawWhite(Graphics g, int i, int j) {
        g.setColor(Color.white);
        g.fillOval(j * width + 10, i * width + 10, width - 20, width - 20);

        //inner
        g.setColor(new Color(0xB1B1B1));
        g.fillOval(j * width + 15, i * width + 15, width - 30, width - 30);

        //inner inner
        g.setColor(Color.white);
        g.fillOval(j * width + 17, i * width + 17, width - 34, width - 34);
    }

    private void drawWhiteQueen(Graphics g, int i, int j) {
        drawWhite(g, i, j);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("black.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image, j * width + 20, i * width + 20, null);
    }

    private void drawBlackQueen(Graphics g, int i, int j) {
        drawBlack(g, i, j);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("white.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image, j * width + 20, i * width + 20, null);
    }

    private void drawBlack(Graphics g, int i, int j) {
        g.setColor(Color.black);
        g.fillOval(j * width + 10, i * width + 10, width - 20, width - 20);

        //inner
        g.setColor(new Color(0x606060));
        g.fillOval(j * width + 15, i * width + 15, width - 30, width - 30);

        //inner inner
        g.setColor(Color.black);
        g.fillOval(j * width + 17, i * width + 17, width - 34, width - 34);
    }

    public boolean sameColor(int[][] array, int i, int j) {
        if (isWhiteMove) {
            return array[i][j] == 2 || array[i][j] == 4;
        } else {
            return array[i][j] == 1 || array[i][j] == 3;
        }
    }

    public boolean oppositeColor(int[][] array, int i, int j) {
        if (isWhiteMove) {
            return array[i][j] == 1 || array[i][j] == 3;
        } else {
            return array[i][j] == 2 || array[i][j] == 4;
        }
    }
}
