package creversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	int[][] boardStatus = new int[10][10];
	final int SPACE = 0, BLACK = 1, WHITE = BLACK * -1, WALL = 9;

	final int[][] value = { { 45, -11, 4, -1, -1, 4, -11, 45 }, { -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 }, { -1, -3, -1, 0, 0, -1, -3, -1 }, { -1, -3, -1, 0, 0, -1, -3, -1 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 }, { -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 45, -11, 4, -1, -1, 4, -11, 45 }, };

	char[] squareStatus = { '　', '●', '○' };

	int blackCount, whiteCount;

	int[] coordinate = new int[2];
	int y, x, nextY, nextX, furtherY, furtherX, previousY, previousX;
	List<int[]> movable = new ArrayList<int[]>();

	void resetBoard() {
		for (y = 0; y < boardStatus.length; y++) {
			for (x = 0; x < boardStatus[0].length; x++) {
				boardStatus[y][x] = (y == 0 || x == 0 || y == 10 || x == 10) ? WALL : SPACE;
			}
		}

		boardStatus[4][4] = BLACK;
		boardStatus[5][5] = BLACK;
		boardStatus[4][5] = WHITE;
		boardStatus[5][4] = WHITE;
	}

	void drawBoard() {
		System.out.println("  １ ２ ３ ４ ５ ６ ７ ８");

		for (y = 1; y <= 8; y++) {
			System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");
			System.out.print(y);

			for (x = 1; x <= 8; x++) {
				switch (boardStatus[y][x]) {
				case SPACE -> System.out.print("|" + squareStatus[0]);
				case BLACK -> System.out.print("|" + squareStatus[1]);
				case WHITE -> System.out.print("|" + squareStatus[2]);
				default -> throw new IllegalArgumentException("Unexpected value: " + boardStatus[y][x]);
				}
			}

			System.out.println("|");
		}

		System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");

		countStones();
	}

	void countStones() {
		blackCount = 0;
		whiteCount = 0;

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (boardStatus[y][x] == BLACK) {
					blackCount++;
				} else if (boardStatus[y][x] == WHITE) {
					whiteCount++;
				}
			}
		}

		System.out.println("黒：" + blackCount + " 白：" + whiteCount);
	}

	void checkSquares(int currentTurn) {
		movable.clear();

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (boardStatus[y][x] == SPACE) {
					for (nextY = -1; nextY <= 1; nextY++) {
						for (nextX = -1; nextX <= 1; nextX++) {
							if (boardStatus[y + nextY][x + nextX] == currentTurn * -1) {
								furtherY = (y + nextY);
								furtherX = (x + nextX);

								while (true) {
									furtherY += nextY;
									furtherX += nextX;

									if (boardStatus[furtherY][furtherX] == currentTurn) {
										coordinate[0] = y;
										coordinate[1] = x;
										movable.add(Arrays.copyOf(coordinate, coordinate.length));
									} else if (boardStatus[furtherY][furtherX] == SPACE
											|| boardStatus[furtherY][furtherX] == WALL) {
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	void reverseStone(int[] moveSquare, int currentTurn) {
		for (nextY = -1; nextY <= 1; nextY++) {
			for (nextX = -1; nextX <= 1; nextX++) {
				if (boardStatus[moveSquare[0] + nextY][moveSquare[1] + nextX] == currentTurn * -1) {
					furtherY = (moveSquare[0] + nextY);
					furtherX = (moveSquare[1] + nextX);

					while (true) {
						furtherY += nextY;
						furtherX += nextX;

						if (boardStatus[furtherY][furtherX] == currentTurn) {
							previousY = furtherY;
							previousX = furtherX;

							do {
								previousY += (nextY * -1);
								previousX += (nextX * -1);
								boardStatus[previousY][previousX] = currentTurn;
							} while (previousY != moveSquare[0] || previousX != moveSquare[1]);
						} else if (boardStatus[furtherY][furtherX] == SPACE
								|| boardStatus[furtherY][furtherX] == WALL) {
							break;
						}
					}
				}
			}
		}
	}
}
