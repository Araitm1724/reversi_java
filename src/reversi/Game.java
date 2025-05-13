package reversi;

public interface Game {
	// 勝敗
	int WIN = 1, LOSE = -1, DRAW = 0;

	/**
	 * 石の色（先後）を選択
	 */
	void selectStone();

	/**
	 * COMのレベルを選択
	 */
	void selectLevel();

	/**
	 * 対局
	 * 
	 * @return 勝敗
	 */
	int playGame();

	/**
	 * プレイヤーの手番
	 * 
	 * @return 投了したか
	 */
	boolean playerMove();

	/**
	 * 対局結果の判定
	 * 
	 * @return 勝敗
	 */
	int gameOver();
}
