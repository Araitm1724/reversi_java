package reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Board implements ReversiData {
	//	盤の状態（外側（[0]と[9]）は番兵）
	protected int[][] boardStatus = new int[10][10];

	protected int blackCount, whiteCount; // それぞれの石の数

	//	マスを調べる際のループ用変数
	protected int y, x, nextY, nextX, furtherY, furtherX, previousY, previousX;

	protected int[] coordinate = new int[2]; // 石を打てるマスの座標

	protected List<int[]> movable = new ArrayList<int[]>(); // 石を打てるマスの座標一覧

	protected Board() {
		for (y = 0; y < boardStatus.length; y++) { // 外側を壁（番兵）、それ以外を全て空白にする
			for (x = 0; x < boardStatus[0].length; x++) {
				boardStatus[y][x] = (y == 0 || x == 0 || y == 10 || x == 10)
						? WALL
						: SPACE;
			}
		}

		//		中央に石を配置
		boardStatus[4][5] = BLACK;
		boardStatus[5][4] = BLACK;
		boardStatus[4][4] = WHITE;
		boardStatus[5][5] = WHITE;

		blackCount = 2;
		whiteCount = 2;
	}

	/**
	 * 黒石の数を取得
	 * 
	 * @return 黒石の数
	 */
	public int getBlackCount() {
		return blackCount;
	}

	/**
	 * 白石の数を取得
	 * 
	 * @return 白石の数
	 */
	public int getWhiteCount() {
		return whiteCount;
	}

	/**
	 * 着手可能なマスの一覧を取得
	 * 
	 * @return 着手可能なマスの一覧
	 */
	public List<int[]> getMovable() {
		return movable;
	}

	/**
	 * 現在の盤の状態を描画する
	 */
	protected abstract void drawBoard();

	/**
	 * 石を数えて表示する
	 */
	protected void countStones() {
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
	}

	/**
	 * 打てるマスを抽出する
	 * 
	 * @param currentTurn 現在の手番（石の色）
	 */
	public void checkSquares(int currentTurn) {
		movable.clear();

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (boardStatus[y][x] == SPACE) { // 空白マスを探す
					for (nextY = -1; nextY <= 1; nextY++) {
						for (nextX = -1; nextX <= 1; nextX++) {
							// 空白マスの隣が相手の石か調べる
							if (boardStatus[y + nextY][x + nextX] == currentTurn * -1) {
								furtherY = (y + nextY);
								furtherX = (x + nextX);

								while (true) { // 相手の石の更に先を調べる
									// 同じ方向の更に先に進む
									furtherY += nextY;
									furtherX += nextX;

									// 自分の石か調べる
									if (boardStatus[furtherY][furtherX] == currentTurn) {
										// 起点の空白マスを有効マスとして登録
										coordinate[0] = y;
										coordinate[1] = x;
										movable.add(Arrays.copyOf(coordinate,
												coordinate.length));
									} else if (boardStatus[furtherY][furtherX] == SPACE
											|| boardStatus[furtherY][furtherX] == WALL) {
										// 空白マスか壁に当たればその方向はハズレ
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
	 * @param moveSquare  石を打ったマスの座標
	 * @param currentTurn 現在の手番（石の色）
	 */
	public void reverseStone(int[] moveSquare, int currentTurn) {
		for (nextY = -1; nextY <= 1; nextY++) {
			for (nextX = -1; nextX <= 1; nextX++) {
				if (boardStatus[moveSquare[0] + nextY][moveSquare[1] + nextX] == currentTurn * -1) { // 打ったマスの隣が相手の石か調べる
					furtherY = (moveSquare[0] + nextY);
					furtherX = (moveSquare[1] + nextX);

					while (true) { // 相手の石の更に先を調べる
						furtherY += nextY;
						furtherX += nextX;

						// 自分の石か調べる
						if (boardStatus[furtherY][furtherX] == currentTurn) {
							previousY = furtherY;
							previousX = furtherX;

							do { // 相手の石を自分に石に変えていく
									// 来た方向を逆戻りする
								previousY += nextY * -1;
								previousX += nextX * -1;

								boardStatus[previousY][previousX] = currentTurn;
							} while (previousY != moveSquare[0] || previousX != moveSquare[1]);

							break;
						} else if (boardStatus[furtherY][furtherX] == SPACE
								|| boardStatus[furtherY][furtherX] == WALL) {
							// 空白マスか壁に当たればその方向はハズレ
							break;
						}
					}
				}
			}
		}
	}
}
