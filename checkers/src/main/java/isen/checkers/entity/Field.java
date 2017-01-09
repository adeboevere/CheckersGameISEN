package isen.checkers.entity;

import java.awt.Frame;
import java.util.Random;

public class Field {
	private int height, width;
	private Figure[][] matrix;
	private Frame frame;
	private Figure selected;
	private FigureColor turn = FigureColor.w;

	public FigureColor getTurn() {
		return this.turn;
	}

	public void changeTurn() {
		if (turn.equals(FigureColor.w))
			turn = FigureColor.b;
		else
			turn = FigureColor.w;
	}

	public Figure getSelected() {
		return selected;
	}

	public void setSelected(Figure figure) {

		if (figure != null) {

			this.selected = figure;
			frame.drawSelected(figure.getFigureX(), figure.getFigureY());
		} else {
			frame.drawCell(selected.getFigureX(), selected.getFigureY(), selected);
			this.selected = null;
		}
	}

	public Field(int fieldWidth, int fieldHeight, Frame frame) {

		Random rand = new Random();
		FigureColor playerColor1 = FigureColor.values()[rand.nextInt(2)];
		FigureColor playerColor2 = null;
		if (playerColor1 == FigureColor.w)
			playerColor2 = FigureColor.b;
		else
			playerColor2 = FigureColor.w;

		Player player1 = new Player(Player.Position.down, playerColor1);
		Player player2 = new Player(Player.Position.top, playerColor2);

		this.height = fieldWidth;
		this.width = fieldHeight;
		this.frame = frame;
		matrix = new Figure[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				if (((y + 1) % 2 != 0 && (x + 1) % 2 == 0) || ((y + 1) % 2 == 0 && (x + 1) % 2 != 0)) {
					if (y < 3)
						matrix[y][x] = new Checker(player2, this, player2.getColor(), x, y);
					if (y > 4)
						matrix[y][x] = new Checker(player1, this, player1.getColor(), x, y);

				} else
					matrix[y][x] = null;
				frame.drawCell(x, y, matrix[y][x]);
			}

		}

	}

	public void setValueAt(Figure figure, int x, int y) {
		matrix[y][x] = figure;
		frame.drawCell(x, y, matrix[y][x]);
	}

	@Override
	public String toString() {
		String result = "";
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {

				if (((y + 1) % 2 != 0 && (x + 1) % 2 == 0) || ((y + 1) % 2 == 0 && (x + 1) % 2 != 0))
					if (matrix[y][x] == null)
						result += "o";
					else
						result += matrix[y][x];
				else
					result += "*";
			}
			result += "|\n";
		}
		return result;
	}

	public boolean isBrownCell(int x, int y) {
		if (((y + 1) % 2 != 0 && (x + 1) % 2 == 0) || ((y + 1) % 2 == 0 && (x + 1) % 2 != 0))
			return true;

		return false;
	}

	public Figure getValueAt(int x, int y) {
		// TODO Auto-generated method stub
		return matrix[y][x];
	}

	public boolean needsToCapture() {
		for (int y = 0; y < matrix.length; y++)
			for (int x = 0; x < matrix.length; x++)
				if (matrix[y][x] != null && matrix[y][x].getColor() == turn && matrix[y][x].isCapturePossibility())
					return true;
		return false;
	}
}
