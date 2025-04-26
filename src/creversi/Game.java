package creversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Game {
	int i;
	int playerStone, enemyStone;

	int enemyLevel;

	int currentTurn;

	int turns;

	int[] moveSquare = new int[2];

	int passCount;

	int maxValue;

	boolean isThinking;

	int choice;
	BufferedReader playerInput = new BufferedReader(new InputStreamReader(System.in));

	Board board = new Board();

	final int WIN = 1, LOSE = -1, DRAW = 0;

	boolean start() throws IOException {
		System.out.println("ようこそ\nここではリバーシをプレイできます。");

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

		return true;
	}

	int playGame() throws IOException {
		System.out.println("対局開始");
		board.resetBoard();

		currentTurn = 1;
		turns = 0;
		passCount = 0;

		while (turns < 60 && passCount < 2) {
			board.drawBoard();
			board.checkSquares(currentTurn);

			if (board.movable.size() > 0) {
				passCount = 0;

				if (currentTurn == playerStone) {
					isThinking = true;
					System.out.println("貴方の番です。");
					System.out.println("打ちたいマスの座標を順番に入力して下さい。");
					System.out.println("投了（降参）したい場合は-1を入力して下さい。");

					do {
						for (i = 0; i < moveSquare.length; i++) {
							do {
								if (i == 0) {
									System.out.print("行（Y座標）>");
								} else {
									System.out.print("列（X座標）>");
								}

								moveSquare[i] = inputNumber();

								if (moveSquare[i] == -1) {
									System.out.print("投了しますか?\nYES：1、NO：-1>");

									if (inputNumber() == 1) {
										return LOSE;
									}
								} else if (!(moveSquare[i] >= 1 && moveSquare[i] <= 8)) {
									System.out.println("範囲外の値です。");
								}
							} while (moveSquare[i] < 1 || moveSquare[i] > 8);
						}

						for (i = 0; i < board.movable.size(); i++) {
							if (Arrays.equals(moveSquare, board.movable.get(i))) {
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
						level1();
					} else {
						level2();
					}
				}

				for (i = 0; i < moveSquare.length; i++) {
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

	void level1() {
		moveSquare = board.movable.get((int) (Math.random() * board.movable.size()));
		board.reverseStone(moveSquare, enemyStone);
	}

	void level2() {
		maxValue = -9999;

		for (i = 0; i < board.movable.size(); i++) {
			moveSquare = (board.value[board.movable.get(i)[0] - 1][board.movable.get(i)[1] - 1] > maxValue)
					? moveSquare = Arrays.copyOf(board.movable.get(i), 2)
					: moveSquare;
		}

		board.reverseStone(moveSquare, enemyStone);
	}

	int gameOver() {
		System.out.println("終局");
		board.drawBoard();

		if (board.blackCount > board.whiteCount) {
			return (playerStone == board.BLACK ? WIN : LOSE);
		} else if (board.blackCount < board.whiteCount) {
			return (playerStone == board.WHITE ? WIN : LOSE);
		} else {
			return DRAW;
		}
	}

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
