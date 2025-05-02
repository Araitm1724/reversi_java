package reversi;

public interface ReversiData {
	// マスの状態を表す
	public static final int BLACK = 1, WHITE = BLACK * -1, SPACE = 0, WALL = 9;

	public static final int[][] VALUE = {
			{ 45, -11, 4, -1, -1, 4, -11, 45 },
			{ -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 },
			{ -1, -3, -1, 0, 0, -1, -3, -1 },
			{ -1, -3, -1, 0, 0, -1, -3, -1 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 },
			{ -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 45, -11, 4, -1, -1, 4, -11, 45 }
	}; // マスの評価値（COMレベル2が使用）

	// 勝敗
	public static final int WIN = 1, LOSE = -1, DRAW = 0;

}
