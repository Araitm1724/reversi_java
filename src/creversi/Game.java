package creversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Game {
	int loop;
	int playerStone, enemyStone;

	int enemyLevel;

	int currentTurn; // 現在の手番（石の色）

	int turns; // 総手数

	int[] moveSquare = new int[2]; // プレイヤーが打った（入力した）マスの座標

	int passCount; // どこにも打てないときに加算する（2以上になったら終局）

	int maxValue; // 最も評価値の高い有効マスの座標（COMレベル2が使用）

	boolean isThinking; // プレイヤーの入力中

	BufferedReader playerInput = new BufferedReader(new InputStreamReader(System.in));
	int choice; // プレイヤーが入力した値

	Board board = new Board();

	final int WIN = 1, LOSE = -1, DRAW = 0;

	/**
	 * 対局設定の選択
	 * 
	 * @throws IOException
	 */
	void start() throws IOException {
		do {
			System.out.println("石の色を選んで下さい。");
			System.out.print("黒（先手）：" + board.BLACK + "、白（後手）：" + board.WHITE + ">");
			playerStone = inputNumber();
		} while (playerStone != board.BLACK && playerStone != board.WHITE);

		enemyStone = playerStone * -1;

		do {
			System.out.print("COMの強さを選択して下さい。");
			System.out.print("レベル1：1、レベル2：2>");
			enemyLevel = inputNumber();
		} while (enemyLevel != 1 && enemyLevel != 2);
	}

	/**
	 * 対局
	 * 
	 * @return 勝敗
	 * @throws IOException
	 */
	int playGame() throws IOException {
		System.out.println("対局開始");

		board.resetBoard();
		currentTurn = 1;
		turns = 0;
		passCount = 0;

		while (turns < 60 && passCount < 2) { // 打てるマスが無くなるまで対局を続ける
			board.drawBoard();
			board.checkSquares(currentTurn);

			if (board.movable.size() > 0) { // 打てるマスがあれば手番を回す
				passCount = 0; // 連続パス回数をリセット

				if (currentTurn == playerStone) {
					System.out.println("貴方の番です。");
					System.out.println("打ちたいマスの座標を順番に入力して下さい。");
					System.out.println("投了（降参）したい場合は-1を入力して下さい。");

					isThinking = true;

					do { //
						for (loop = 0; loop < moveSquare.length; loop++) { // Y座標とX座標の2回入力させる
							do {
								System.out.print((loop == 0) ? "行（Y座標）>" : "列（X座標）>");
								moveSquare[loop] = inputNumber();

								if (moveSquare[loop] == -1) {
									System.out.print("投了しますか?\nYES：1、NO：-1>");

									if (inputNumber() == 1) {
										return LOSE; // 投了したらそこで終了
									}
								} else if (!(moveSquare[loop] >= 1 && moveSquare[loop] <= 8)) {
									System.out.println("範囲外の値です。");
								}
							} while (moveSquare[loop] < 1 || moveSquare[loop] > 8);
						}

						for (loop = 0; loop < board.movable.size(); loop++) { // 有効マスに入力したマスが含まれているか
							if (Arrays.equals(moveSquare, board.movable.get(loop))) {
								board.reverseStone(moveSquare, playerStone); // 石を打って手番終了
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
					board.reverseStone((enemyLevel == 1) ? level1() : level2(), enemyStone);

					try { // 2秒待つ（これが無いと一瞬で終わるので混乱する）
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				turns++;
			} else {
				System.out.println((++passCount < 2) ? "打てる手が無いのでパスします。" : "お互いに打てる手が無くなりました。");
			}

			currentTurn *= -1; // 相手に手番を回す
		}

		return gameOver(); // 対局が終了したら結果の判定
	}

	/**
	 * COMレベル1の思考
	 * 
	 * @return 石を打つマスの座標
	 */
	int[] level1() { // 有効マスの中からランダムに打つ
		return board.movable.get((int) (Math.random() * board.movable.size()));
	}

	/**
	 * COMレベル2の思考
	 * 
	 * @return 石を打つマスの座標
	 */
	int[] level2() {
		maxValue = 0; // 最も評価値の高い有効マスの座標（まず1番目を代入）

		for (loop = 1; loop < board.movable.size(); loop++) { // 2番目から繰り返し始める
			if (board.value[board.movable.get(loop)[0] - 1][board.movable.get(loop)[1]
					- 1] > board.value[board.movable.get(maxValue)[0] - 1][board.movable.get(maxValue)[1] - 1]) {
				maxValue = loop; // 現在のマスが前のマスより評価値が高ければ更新
			}
		}

		return board.movable.get(maxValue);
	}

	/**
	 * 対局結果の判定
	 * 
	 * @return 勝敗
	 */
	int gameOver() {
		System.out.println("終局");
		board.drawBoard();

		if (board.blackCount > board.whiteCount) { // 黒の方が多く、プレイヤーが黒なら勝ち、白なら負け
			return (playerStone == board.BLACK ? WIN : LOSE);
		} else if (board.blackCount < board.whiteCount) { // 白の方が多く、プレイヤーが白なら勝ち、黒なら負け
			return (playerStone == board.WHITE ? WIN : LOSE);
		} else { // 白黒同数なら引き分け
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
			// TODO: handle exception
			System.out.println("数値を入力して下さい。");
		}

		return choice;
	}
}
