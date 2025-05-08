package reversi;

import java.util.List;

public class Enemy {
	/**
	 * 有効マスの中から石を打つ
	 * 
	 * @return 石を打つマスの座標
	 */
	public int[] enemyMove(List<int[]> movable) {
		// 有効マスからランダムに選択
		return movable.get((int) (Math.random() * movable.size()));
	}
}
