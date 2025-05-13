package reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Board implements DisplayBoard {
	// 盤の状態（外側（[0]と[9]）は番兵）
	protected int[][] bStatus = new int[10][10];
	// それぞれの石の数
	protected int bCount, wCount;
	// マスを調べる際のループ用変数
	protected int y, x, nextY, nextX, furtherY, furtherX, previousY, previousX;
	// 石を打てるマスの座標
	protected int[] coordinate = new int[2];
	// 石を打てるマスの座標一覧
	protected List<int[]> movable = new ArrayList<int[]>();

	public Board() {
		// 外側を壁（番兵）、それ以外を全て空白にする
		for (y = 0; y < bStatus.length; y++) {
			for (x = 0; x < bStatus[0].length; x++) {
				bStatus[y][x] = (y == 0 || x == 0 || y == 10 || x == 10) ? WALL : SPACE;
			}
		}

		// 中央に石を配置
		bStatus[4][5] = BLACK;
		bStatus[5][4] = BLACK;
		bStatus[4][4] = WHITE;
		bStatus[5][5] = WHITE;
	}

	/**
	 * 黒石の数を取得
	 * 
	 * @return 黒石の数
	 */
	public int getBCount() {
		return bCount;
	}

	/**
	 * 白石の数を取得
	 * 
	 * @return 白石の数
	 */
	public int getWCount() {
		return wCount;
	}

	/**
	 * 着手可能なマスの一覧を取得
	 * 
	 * @return 着手可能なマスの一覧
	 */
	public int[][] getMovable() {
		int[][] copiedMovable = new int[movable.size()][];

		for (int i = 0; i < movable.size(); i++) {
			copiedMovable[i] = Arrays.copyOf(movable.get(i), movable.get(i).length);
		}

		return copiedMovable;
	}

	/**
	 * 石を数える
	 */
	protected void countStones() {
		bCount = wCount = 0;

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (bStatus[y][x] == BLACK) {
					bCount++;
				} else if (bStatus[y][x] == WHITE) {
					wCount++;
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
				// 空白マスを探す
				if (bStatus[y][x] == SPACE) {
					for (nextY = -1; nextY <= 1; nextY++) {
						for (nextX = -1; nextX <= 1; nextX++) {
							// 空白マスの隣が相手の石か調べる
							if (bStatus[y + nextY][x + nextX] == currentTurn * -1) {
								furtherY = (y + nextY);
								furtherX = (x + nextX);

								while (true) {
									// 相手の石の更に先を調べる
									furtherY += nextY;
									furtherX += nextX;

									// 自分の石か調べる
									if (bStatus[furtherY][furtherX] == currentTurn) {
										// 起点の空白マスを有効マスとして登録
										coordinate[0] = y;
										coordinate[1] = x;
										movable.add(Arrays.copyOf(coordinate, coordinate.length));

										break;
									} else if (bStatus[furtherY][furtherX] == SPACE
											|| bStatus[furtherY][furtherX] == WALL) {
										break; // 空白か壁に当たればその方向はハズレ
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
	public void reverseStones(int[] moveSquare, int currentTurn) {
		for (nextY = -1; nextY <= 1; nextY++) {
			for (nextX = -1; nextX <= 1; nextX++) {
				// 打ったマスの隣が相手の石か調べる
				if (bStatus[moveSquare[0] + nextY][moveSquare[1] + nextX] == currentTurn * -1) {
					furtherY = (moveSquare[0] + nextY);
					furtherX = (moveSquare[1] + nextX);

					while (true) {
						// 相手の石の更に先を調べる
						furtherY += nextY;
						furtherX += nextX;

						// 自分の石か調べる
						if (bStatus[furtherY][furtherX] == currentTurn) {
							previousY = furtherY;
							previousX = furtherX;

							do {
								// 来た方向を逆戻りする
								previousY += nextY * -1;
								previousX += nextX * -1;

								bStatus[previousY][previousX] = currentTurn;
							} while (previousY != moveSquare[0] || previousX != moveSquare[1]);

							break;
						} else if (bStatus[furtherY][furtherX] == SPACE || bStatus[furtherY][furtherX] == WALL) {
							break; // 空白か壁に当たればその方向はハズレ
						}
					}
				}
			}
		}
	}
}
