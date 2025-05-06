package reversi.creversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import reversi.Board;
import reversi.Game;

public class Cgame extends Game {
	// プレイヤーの入力中であることを表す
	private boolean isThinking;

	// プレイヤーが入力した値
	int choice;
	BufferedReader playerInput = new BufferedReader(new InputStreamReader(System.in));

	Cboard cboard = new Cboard();

	/**
	 * 対局設定の選択
	 * 
	 * @throws IOException
	 */
	void start() throws IOException {
		do {
			System.out.println("石の色を選んで下さい。");
			System.out.print("黒(先手):" + Board.BLACK + "、白(後手):" + Board.WHITE + ">");
			playerStone = inputNumber();
		} while (playerStone != Board.BLACK && playerStone != Board.WHITE);

		enemyStone = playerStone * -1;

		do {
			System.out.println("COMの強さを選択して下さい。");
			System.out.print("レベル1:1、レベル2:2>");
			enemyLevel = inputNumber();
		} while (enemyLevel != 1 && enemyLevel != 2);
	}

	/**
	 * 対局
	 * 
	 * @return 勝敗
	 * @throws IOException
	 */
	@Override
	protected int playGame() throws IOException {
		System.out.println("対局開始");

		cboard = new Cboard();
		currentTurn = 1;
		turns = 0;
		passCount = 0;

		// 打てるマスが無くなるまで対局を続ける
		while (turns < 60 && passCount < 2) {
			cboard.drawBoard();
			cboard.checkSquares(currentTurn);

			// 打てるマスがあれば手番を回す
			if (cboard.getMovable().size() > 0) {
				passCount = 0; // 連続パス回数をリセット

				if (currentTurn == playerStone) {
					System.out.println("貴方の番です。");
					System.out.println("打ちたいマスの座標を順番に入力して下さい。");
					System.out.println("投了（降参）したい場合は-1を入力して下さい。");

					isThinking = true;
					
					do {
						// Y座標とX座標の2回入力させる
						for (loop = 0; loop < moveSquare.length; loop++) {
							do {
								System.out.print((loop == 0) ? "行(Y座標)>" : "列(X座標)>");
								moveSquare[loop] = inputNumber();

								if (moveSquare[loop] == -1) {
									System.out.print("投了しますか?\nYES:1、NO:-1>");

									if (inputNumber() == 1) {
										// 投了したらそこで終了
										return LOSE;
									}
								} else if (!(moveSquare[loop] >= 1 && moveSquare[loop] <= 8)) {
									System.out.println("範囲外の値です。");
								}
							} while (moveSquare[loop] < 1 || moveSquare[loop] > 8);
						}

					
						// 有効マスに入力したマスが含まれているか
						for (loop = 0; loop < cboard.getMovable().size(); loop++) {
							if (Arrays.equals(moveSquare, cboard.getMovable().get(loop))) {
								// 石を打って手番終了
								cboard.reverseStone(moveSquare, playerStone);
								isThinking = false;
								break;
							}
						}

						if (isThinking) {
							System.out.println("そこには打てません。");
						}
					} while (isThinking);
				} else {
					System.out.println("相手の番です。");
					cboard.reverseStone((enemyLevel == 1) ? level1() : level2(), enemyStone);

					try {
						// 2秒待つ
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				turns++;
			} else {
				System.out.println((++passCount < 2) ? "打てる手が無いのでパスします。" : "お互いに打てる手が無くなりました。");
			}

			// 相手に手番を回す
			currentTurn *= -1;
		}

		return gameOver();
	}

	/**
	 * COMレベル1の思考
	 * 
	 * @return 石を打つマスの座標
	 */
	@Override
	protected int[] level1() {
		return cboard.getMovable().get((int) (Math.random() * cboard.getMovable().size()));
	}

	/**
	 * COMレベル2の思考
	 * 
	 * @return 石を打つマスの座標
	 */
	@Override
	protected int[] level2() {
		// 最も評価値の高い有効マスの座標（まず1番目を代入）
		maxValue = 0;

		// 2番目から繰り返し始める
		for (loop = 1; loop < cboard.getMovable().size(); loop++) {
			// 現在のマスが前のマスより評価値が高ければ更新
			if (Board.VALUE[cboard.getMovable().get(loop)[0] - 1][cboard.getMovable().get(loop)[1]
					- 1] > Board.VALUE[cboard.getMovable().get(maxValue)[0] - 1][cboard.getMovable().get(maxValue)[1]
							- 1]) {
				maxValue = loop;
			}
		}

		return cboard.getMovable().get(maxValue);
	}

	/**
	 * 対局結果の判定
	 * 
	 * @return 勝敗
	 */
	@Override
	protected int gameOver() {
		System.out.println("終局");
		cboard.drawBoard();

		if (cboard.getBlackCount() > cboard.getWhiteCount()) {
			// 黒の方が多く、プレイヤーが黒なら勝ち、白なら負け
			return (playerStone == Board.BLACK ? WIN : LOSE);
		} else if (cboard.getBlackCount() < cboard.getWhiteCount()) {
			// 白の方が多く、プレイヤーが白なら勝ち、黒なら負け
			return (playerStone == Board.WHITE ? WIN : LOSE);
		} else {
			// 白黒同数なら引き分け
			return DRAW;
		}

	}

	/**
	 * プレイヤーのコマンド選択と着手
	 * 
	 * @return 入力した数値
	 * @throws IOException
	 */
	int inputNumber() throws IOException {
		choice = 0;

		try {
			choice = Integer.parseInt(playerInput.readLine());
		} catch (NumberFormatException e) {
			System.out.println("数値を入力して下さい。");
		}

		return choice;
	}
}
