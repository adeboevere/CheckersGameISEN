package isen.checkers; 

public class Player extends Actor {

    public Player(boolean isWhite) {
        super(isWhite);
    }

    public boolean valid(int[][] array, int row1, int col1, int row2, int col2,
                         boolean capture) {

        if (array[row1][col1] == -1 || array[row2][col2] == -1) {

            return false;
        }
        if (array[row1][col1] == 0 || array[row2][col2] != 0) {

            return false;
        }
        if (array[row1][col1] == 1 && isWhite) {

            return false;
        }
        if (array[row1][col1] == 2 && !isWhite) {

            return false;
        }

        if (array[row1][col1] == 3 && isWhite) {

            return false;
        }
        if (array[row1][col1] == 4 && !isWhite) {

            return false;
        }

        if (array[row1][col1] == 3 || array[row1][col1] == 4) {

            if (Math.abs(col2 - col1) != Math.abs(row2 - row1)) {

                return false;
            }
            if (capture) {

                for (int i = 1; i < Math.abs(col2 - col1); i++) {
                    int row3, col3, col4, row4;

                    if (col2 > col1) {
                        col3 = col1 + i;
                        col4 = col1 + i + 1;
                    } else {
                        col3 = col1 - i;
                        col4 = col1 - i - 1;
                    }

                    if (row2 > row1) {
                        row3 = row1 + i;
                        row4 = row1 + i + 1;
                    } else {
                        row3 = row1 - i;
                        row4 = row1 - i - 1;
                    }

                    if (oppositeColor(array, row3, col3) && array[row4][col4] == 0) {
                        array[row3][col3] = 0;

                        return true;
                    }
                }
                return false;

            }

            //no capture
            else {
                for (int i = 1; i < Math.abs(col2 - col1); i++) {
                    int row3, col3;

                    if (col2 > col1) {

                        col3 = col1 + i;
                    } else {
                        col3 = col1 - i;
                    }

                    if (row2 > row1) {

                        row3 = row1 + i;
                    } else {

                        row3 = row1 - i;
                    }

                    if (array[row3][col3] != 0) {

                        return false;
                    }
                }
                return true;
            }

        }

        //capture 1, 3
        if (col2 - col1 == row2 - row1 && Math.abs(col2 - col1) == 2
                && oppositeColor(array, row1 + (row2 - row1) / 2, col1 + (col2 - col1) / 2)) {
            if (capture) {
                array[row1 + (row2 - row1) / 2][col1 + (col2 - col1) / 2] = 0;

                return true;
            }
            return false;

        }

        //capture
        if (col2 - col1 == row1 - row2 && Math.abs(col2 - col1) == 2) {
            if (col2 - col1 == 2 && oppositeColor(array, row1 - 1, col1 + 1)) {
                if (capture) {
                    array[row1 - 1][col1 + 1] = 0;
                    return true;
                }

                return false;

            } else if (col2 - col1 == -2 && oppositeColor(array, row1 + 1, col1 - 1)) {
                if (capture) {
                    array[row1 + 1][col1 - 1] = 0;

                    return true;
                }
                return false;

            } else {

                return false;
            }
        }

        if (Math.abs(col2 - col1) > 1 || Math.abs(row2 - row1) > 1) {

            return false;
        }
        if (capture) {

            return false;
        }
        if (isWhite) {
            if (row2 > row1) {

                return false;
            }
        } else {
            if (row2 < row1) {

                return false;
            }
        }

        return true;

    }

    public boolean oppositeColor(int[][] array, int i, int j) {
        if (isWhite) {
            return array[i][j] == 1 || array[i][j] == 3;
        } else {
            return array[i][j] == 2 || array[i][j] == 4;
        }
    }
}
