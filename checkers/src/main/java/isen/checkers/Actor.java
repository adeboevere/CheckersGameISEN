package isen.checkers;

public abstract class Actor {
    protected boolean isWhite;

    public Actor(boolean isWhite){
        this.isWhite = isWhite;
    }

    public abstract boolean valid(int [][] array, int row1, int col1, int row2, int col2,
                                  boolean capture);
}
