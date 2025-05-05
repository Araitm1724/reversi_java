package reversi;

import java.io.IOException;

public abstract class Game {
	// ループ用変数
	protected int loop;
	// プレイヤーとCOMの石の色
	protected int playerStone, enemyStone;
	// COMのレベル
	protected int enemyLevel;
	// 現在の手番（石の色）
	protected int currentTurn;
	// 総手数
	protected int turns;
	// プレイヤーが打った（入力した）マスの座標
	protected int[] moveSquare = new int[2];
	// どこにも打てないときに加算する（2以上になったら終局）
	protected int passCount;
	// 最も評価値の高い有効マスの座標（COMレベル2が使用）
	protected int maxValue;
	// 勝敗
	public static final int WIN = 1, LOSE = -1, DRAW = 0;

	/**
	 * 対局
	 * 
	 * @return 勝敗
	 * @throws IOException
	 */
	protected abstract int playGame() throws IOException;

	/**
	 * COMレベル1の思考
	 * 
	 * @return 石を打つマスの座標
	 */
	protected abstract int[] level1();

	/**
	 * COMレベル2の思考
	 * 
	 * @return 石を打つマスの座標
	 */
	protected abstract int[] level2();

	/**
	 * 対局結果の判定
	 * 
	 * @return 勝敗
	 */
	protected abstract int gameOver();
}
