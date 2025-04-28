package creversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * リバーシ盤クラス
 */
public class Board {
	int[][] boardStatus = new int[10][10]; // 盤の状態（外側（[0]と[9]）は番兵）

	final int BLACK = 1, WHITE = BLACK * -1, SPACE = 0, WALL = 9; // マスの状態を表す

	char[] squareStatus = { '　', '●', '○' }; // 盤の状態（マスの描画に使用）

	final int[][] value = { { 45, -11, 4, -1, -1, 4, -11, 45 }, { -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 }, { -1, -3, -1, 0, 0, -1, -3, -1 }, { -1, -3, -1, 0, 0, -1, -3, -1 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 }, { -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 45, -11, 4, -1, -1, 4, -11, 45 }, }; // マスの評価値（COMレベル2が使用）

	int blackCount, whiteCount; // それぞれの石の数

	int y, x, nextY, nextX, furtherY, furtherX, previousY, previousX; // マスを調べる際のループ用

	int[] coordinate = new int[2]; // 石を打てるマスの座標

	List<int[]> movable = new ArrayList<int[]>(); // 石を打てるマスの座標一覧

	/**
	 * 対局開始時に盤の状態をリセットする
	 */
	void resetBoard() {
		for (y = 0; y < boardStatus.length; y++) { // 外側を壁（番兵）、それ以外を全て空白にする
			for (x = 0; x < boardStatus[0].length; x++) {
				boardStatus[y][x] = (y == 0 || x == 0 || y == 10 || x == 10) ? WALL : SPACE;
			}
		}

		boardStatus[4][5] = BLACK; // 中央に石を配置
		boardStatus[5][4] = BLACK;
		boardStatus[4][4] = WHITE;
		boardStatus[5][5] = WHITE;
	}

	/**
	 * 現在の盤の状態を描画する
	 */
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

		countStones(); // 描画を終えたら石を数える
	}

	/**
	 * 石を数えて表示する
	 */
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

	/**
	 * 打てるマスを抽出する
	 * 
	 * @param currentTurn 現在の手番（石の色）
	 */
	void checkSquares(int currentTurn) {
		movable.clear();

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (boardStatus[y][x] == SPACE) { // 空白マスを探す
					for (nextY = -1; nextY <= 1; nextY++) {
						for (nextX = -1; nextX <= 1; nextX++) {
							if (boardStatus[y + nextY][x + nextX] == currentTurn * -1) { // 空白マスの隣が相手の石か調べる
								furtherY = (y + nextY);
								furtherX = (x + nextX);

								while (true) { // 相手の石の更に先を調べる
									furtherY += nextY; // 同じ方向の更に先に進む
									furtherX += nextX;

									if (boardStatus[furtherY][furtherX] == currentTurn) { // 自分の石か調べる
										coordinate[0] = y; // 起点の空白マスを有効マスとして登録
										coordinate[1] = x;
										movable.add(Arrays.copyOf(coordinate, coordinate.length));
									} else if (boardStatus[furtherY][furtherX] == SPACE
											|| boardStatus[furtherY][furtherX] == WALL) { // 空白マスか壁に当たればその方向はハズレ
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

	/**
	 * 石を反転させる
	 * 
	 * @param moveSquare 石を打ったマスの座標
	 * @param currentTurn 現在の手番（石の色）
	 */
	void reverseStone(int[] moveSquare, int currentTurn) {
		for (nextY = -1; nextY <= 1; nextY++) {
			for (nextX = -1; nextX <= 1; nextX++) {
				if (boardStatus[moveSquare[0] + nextY][moveSquare[1] + nextX] == currentTurn * -1) { // 打ったマスの隣が相手の石か調べる
					furtherY = (moveSquare[0] + nextY);
					furtherX = (moveSquare[1] + nextX);

					while (true) { // 相手の石の更に先を調べる
						furtherY += nextY;
						furtherX += nextX;

						if (boardStatus[furtherY][furtherX] == currentTurn) { // 自分の石か調べる
							previousY = furtherY;
							previousX = furtherX;

							do { // 相手の石を自分に石に変えていく
								previousY += nextY * -1; // 来た方向を逆戻りする
								previousX += nextX * -1;

								boardStatus[previousY][previousX] = currentTurn;
							} while (previousY != moveSquare[0] || previousX != moveSquare[1]);
						} else if (boardStatus[furtherY][furtherX] == SPACE
								|| boardStatus[furtherY][furtherX] == WALL) { // 空白マスか壁に当たればその方向はハズレ
							break;
						}
					}
				}
			}
		}
	}
}
