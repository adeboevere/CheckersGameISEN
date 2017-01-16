package isen.checkers;

public class AI extends Actor{
    public AI(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean valid(int[][] array, int row1, int col1, int row2, int col2, boolean capture) {
        return false;
    }

    public void move(){
        //pass
    }

}
