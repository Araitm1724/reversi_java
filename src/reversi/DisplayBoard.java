package reversi;

public interface DisplayBoard {
	// 盤の状態（マスの描画に使用）
	char[] squareStatus = { '　', '●', '○' };
	// マスの状態を表す
	int BLACK = 1, WHITE = BLACK * -1, SPACE = 0, WALL = 9;

	/**
	 * 現在の盤の状態を描画する
	 */
	void drawBoard();
}
