package reversi;

import java.util.List;

public class EnemyLevel2 extends Enemy {
	// 最も評価値の高い有効マスの座標
	int maxValue = 0;

	@Override
	public int[] enemyMove(List<int[]> movable) {
		// まず1番目を代入
		maxValue = 0;

		// 2番目から繰り返し始める
		for (int i = 1; i < movable.size(); i++) {
			// 現在のマスが前のマスより評価値が高ければ更新
			if (Board.VALUE[movable.get(i)[0] - 1][movable.get(i)[1]
					- 1] > Board.VALUE[movable.get(maxValue)[0]
							- 1][movable.get(maxValue)[1] - 1]) {
				maxValue = i;
			}
		}

		return movable.get(maxValue);
	}
}
