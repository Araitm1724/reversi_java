package creversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Game {
	int playerStone, enemyStone;

	int enemyLevel;

	int currentTurn;

	int turns;

	int[] moveSquare = new int[2];

	int passCount;

	int maxValue;

	boolean isThinking;

	int inputNumber;
	BufferedReader playerInput = new BufferedReader(new InputStreamReader(System.in));

	Board board;

	final int WIN = 1, LOSE = -1, DRAW = 0;

	boolean start() throws IOException {
		System.out.println("ようこそ\nここではリバーシをプレイできます。");

		do {
			System.out.print("黒（先手）なら" + board.BLACK + "、白（後手）なら" + board.WHITE + "を入力して下さい。＞");

			playerStone = inputNumber();
		} while (playerStone != board.BLACK && playerStone != board.WHITE);

		enemyStone = playerStone * -1;

		do {
			System.out.print("COMの強さを選択して下さい。");
			System.out.println("レベル1：1、レベル2：2＞");

			enemyLevel = inputNumber();
		} while (enemyLevel != 1 && enemyLevel != 2);

		return true;
	}

	int playGame() throws IOException {
		System.out.println("対局開始");

		board = new Board();

		currentTurn = 1;
		turns = 0;

		while (turns < 60 && passCount < 2) {
			board.drawBoard();

			board.checkSquares(currentTurn);

			if (board.validSquares.size() > 0) {
				passCount = 0;

				board.countStones();
				System.out.println("黒：" + board.stoneCount[0] + " 白：" + board.stoneCount[1]);

				if (currentTurn == playerStone) {
					isThinking = true;
					System.out.println("貴方の番です。");
					System.out.println("打ちたいマスを縦、横の順番で入力して下さい。");
					System.out.println("投了（降参）したい場合は-1を入力して下さい。");

					do {
						for (int i = 0; i < moveSquare.length; i++) {
							do {
								if (i == 0) {
									System.out.print("行（Y座標）＞");
								} else {
									System.out.print("列（X座標）＞");
								}

								moveSquare[i] = inputNumber();

								if (moveSquare[i] >= 1 && moveSquare[i] <= 8) {
									break;
								} else if (moveSquare[i] == -1) {
									System.out.println("本当に投了しますか?");
									System.out.print("yesなら1、noなら-1＞");
									
									if (inputNumber() == 1) {
										System.out.println("投了しました。");
										return LOSE;
									}
								} else {
									System.out.println("範囲外の値です。");
								}
							} while (true);
						}

						for (int i = 0; i < board.validSquares.size(); i++) {
							if (Arrays.equals(moveSquare, board.validSquares.get(i))) {
								board.reverseStone(moveSquare, playerStone);
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

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (enemyLevel == 1) {
						enemy1Move();
					} else {
						enemy2Move();
					}
				}

				for (int i = 0; i < moveSquare.length; i++) {
					moveSquare[i] = 0;
				}

				turns++;
			} else {
				System.out.println("打てる手が無いのでパスします。");
				passCount++;

				if (passCount >= 2) {
					System.out.println("お互いに打てる手が無くなりました。");
				}
			}

			currentTurn *= -1;
		}

		return gameOver();
	}

	void enemy1Move() {
		moveSquare = board.validSquares.get((int) (Math.random() * board.validSquares.size()));
		board.reverseStone(moveSquare, enemyStone);

		return;
	}

	void enemy2Move() {
		maxValue = -1000;

		for (int i = 0; i < board.validSquares.size(); i++) {
			moveSquare = (board.squareValue[board.validSquares.get(i)[0]][board.validSquares.get(i)[1]] > maxValue)
					? Arrays.copyOf(board.validSquares.get(i), 2)
					: moveSquare;
		}

		board.reverseStone(moveSquare, enemyStone);

		return;
	}

	int gameOver() {
		System.out.println("終局");

		board.drawBoard();
		board.countStones();
		System.out.println("黒：" + board.stoneCount[0] + " 白：" + board.stoneCount[1]);

		if (board.stoneCount[0] > board.stoneCount[1]) {
			if (playerStone == board.BLACK) {
				return WIN;
			} else {
				return LOSE;
			}
		} else if (board.stoneCount[0] < board.stoneCount[1]) {
			if (playerStone == board.BLACK) {
				return LOSE;
			} else {
				return WIN;
			}
		} else {
			return DRAW;
		}
	}

	int inputNumber() throws IOException {
		inputNumber = 0;

		try {
			inputNumber = Integer.parseInt(playerInput.readLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
			System.out.println("数値を入力して下さい。");
		}

		return inputNumber;
	}
}
