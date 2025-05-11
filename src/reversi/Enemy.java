package reversi;

public class Enemy {
	/**
	 * 有効マスの中から石を打つ
	 * 
	 * @return 石を打つマスの座標
	 */
	public int[] enemyMove(int[][] movable) {
		// 有効マスからランダムに選択
		return movable[(int) (Math.random() * movable.length)];
	}
}
