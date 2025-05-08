package reversi.creversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import reversi.Board;
import reversi.Enemy;
import reversi.EnemyLevel2;

public class Cgame {
	// プレイヤーとCOMの石の色
	protected int myStone, enemyStone;
	// COMのレベル
	protected int level;
	// 現在の手番（石の色）
	protected int currentTurn;
	// 総手数
	protected int turns;
	// プレイヤーが打った（入力した）マスの座標
	protected int[] moveSquare = new int[2];
	// どこにも打てないときに加算する（2以上になったら終局）
	protected int pass;
	// 勝敗
	public static final int WIN = 1, LOSE = -1, DRAW = 0;
	// プレイヤーが入力した値
	int choice = 0;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	// プレイヤーの入力中であることを表す
	private boolean isThinking = false;
	// 盤の情報
	Board board;
	// COM
	Enemy enemy;

	/**
	 * 石の色（先後）を選択
	 */
	protected void selectStone() {
		do {
			System.out.println("石の色(先後)を選んで下さい。");
			System.out.print("黒:" + Board.BLACK + "、白:" + Board.WHITE + ">");
			myStone = playerInput();
		} while (myStone != Board.BLACK && myStone != Board.WHITE);

		enemyStone = myStone * -1;
	}

	/**
	 * COMのレベルを選択
	 */
	protected void selectLevel() {
		do {
			System.out.println("COMの強さを選択して下さい。");
			System.out.print("レベル1:1、レベル2:2>");
			level = playerInput();
		} while (level != 1 && level != 2);

		// 選択したレベルのCOMを呼び出す
		enemy = (level == 1) ? new Enemy() : new EnemyLevel2();
	}

	/**
	 * 対局
	 * 
	 * @return 勝敗
	 */
	protected int playGame() {
		selectStone();
		selectLevel();

		System.out.println("対局開始");

		board = new Board();
		currentTurn = 1;
		turns = 0;
		pass = 0;

		// 打てるマスが無くなるまで対局を続ける
		while (turns < 60 && pass < 2) {
			board.drawBoard();
			board.checkSquares(currentTurn);

			// 打てるマスがあれば手番を回す
			if (board.getMovable().size() > 0) {
				pass = 0; // 連続パス回数をリセット

				if (currentTurn == myStone) {
					// 投了したかを判定
					if (playerMove()) {
						return LOSE;
					}
				} else {
					System.out.println("相手の番です。");
					board.reverseStones(enemy.enemyMove(board.getMovable()), enemyStone);

					try {
						// 2秒待つ
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				turns++;
			} else {
				System.out.println((++pass < 2)
						? "打てる手が無いのでパスします。"
						: "お互いに打てる手が無くなりました。");
			}

			// 相手に手番を回す
			currentTurn *= -1;
		}

		return gameOver();
	}

	/**
	 * プレイヤーの手番
	 * 
	 * @return 投了したか
	 */
	protected boolean playerMove() {
		System.out.println("貴方の番です。");
		System.out.println("打ちたいマスの座標を順番に入力して下さい。");
		System.out.println("投了（降参）したい場合は-1を入力して下さい。");

		isThinking = true;

		do {
			// Y座標とX座標の2回入力させる
			for (int i = 0; i < moveSquare.length; i++) {
				do {
					System.out.print((i == 0) ? "行(Y座標)>" : "列(X座標)>");
					moveSquare[i] = playerInput();

					if (moveSquare[i] == -1) {
						System.out.print("投了しますか?\nYES:1、NO:-1>");

						if (playerInput() == 1) {
							// 投了したらそこで終了
							return true;
						}
					} else if (moveSquare[i] < 1 || moveSquare[i] > 8) {
						System.out.println("範囲外の値です。");
					}
				} while (moveSquare[i] < 1 || moveSquare[i] > 8);
			}

			// 有効マスに入力したマスが含まれているか
			for (int[] i : board.getMovable()) {
				if (Arrays.equals(moveSquare, i)) {
					// 石を打って手番終了
					board.reverseStones(moveSquare, myStone);
					isThinking = false;
					break;
				}
			}

			if (isThinking) {
				System.out.println("そこには打てません。");
			}
		} while (isThinking);

		return false;
	}

	/**
	 * 対局結果の判定
	 * 
	 * @return 勝敗
	 */
	protected int gameOver() {
		System.out.println("終局");
		board.drawBoard();

		if (board.getBCount() > board.getWCount()) {
			// 黒の方が多く、プレイヤーが黒なら勝ち、白なら負け
			return (myStone == Board.BLACK ? WIN : LOSE);
		} else if (board.getBCount() < board.getWCount()) {
			// 白の方が多く、プレイヤーが白なら勝ち、黒なら負け
			return (myStone == Board.WHITE ? WIN : LOSE);
		} else {
			// 白黒同数なら引き分け
			return DRAW;
		}
	}

	/**
	 * プレイヤーのコマンド選択と着手
	 * 
	 * @return プレイヤーが入力した数値
	 */
	protected int playerInput() {
		choice = 0;

		try {
			choice = Integer.parseInt(reader.readLine());
		} catch (IOException | NumberFormatException e) {
			System.out.println("数値を入力して下さい。");
		}

		return choice;
	}
}
