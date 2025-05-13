package reversi;

import java.util.Arrays;

public class EnemyLevel2 extends Enemy {
	int[] maxValue = { 0, 0 }; // 最も評価値の高い有効マスの座標

	public static final int[][] VALUE = { // マスの評価値
			{ 45, -11, 4, -1, -1, 4, -11, 45 }, { -11, -16, 1, -3, -3, -1, -16, -11 }, { 4, -1, 2, -1, -1, 2, -1, 4 },
			{ -1, -3, -1, 0, 0, -1, -3, -1 }, { -1, -3, -1, 0, 0, -1, -3, -1 }, { 4, -1, 2, -1, -1, 2, -1, 4 },
			{ -11, -16, 1, -3, -3, -1, -16, -11 }, { 45, -11, 4, -1, -1, 4, -11, 45 } };

	@Override
	public int[] enemyMove(int[][] movable) {
		maxValue = Arrays.copyOf(movable[0], maxValue.length); // まず1番目を代入

		for (int i = 1; i < movable.length; i++) { // 2番目から繰り返し始める
			// 現在のマスが前のマスより評価値が高ければ更新
			if (VALUE[movable[i][0] - 1][movable[i][1] - 1] > VALUE[maxValue[0] - 1][maxValue[1] - 1]) {
				maxValue = Arrays.copyOf(movable[i], maxValue.length);
			}
		}

		return maxValue;
	}
}
