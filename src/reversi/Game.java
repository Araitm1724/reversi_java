package reversi;

import java.io.IOException;

public abstract class Game implements ReversiData {
	protected int loop; // ループ用変数
	protected int playerStone, enemyStone; // プレイヤーとCOMの石の色

	protected int enemyLevel; // COMのレベル

	protected int currentTurn; // 現在の手番（石の色）

	protected int turns; // 総手数

	protected int[] moveSquare = new int[2]; // プレイヤーが打った（入力した）マスの座標

	protected int passCount; // どこにも打てないときに加算する（2以上になったら終局）

	protected int maxValue; // 最も評価値の高い有効マスの座標（COMレベル2が使用）

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
