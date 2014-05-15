package rule;

public class Rule {

	public boolean move(String name, int x, int y, int toX, int toY, int color,
			Chessboard c, int whoseTurn) {
		String subName = "";

		int count = 0;
		int grid = 0;

		if (whoseTurn != color)// whose turn
		{
			return false;
		}
		if (c.getChessboard()[toY][toX] != null
				&& c.getChessboard()[toY][toX].getColor() == color) {
			return false;
		}
		if (color == 0)// string start with red
		{// get chess name without red or black in beginning and end with number
			// for chess
			if (name.substring(3, 4).equals("K")) {
				subName = name.substring(3, name.length());
			} else
				subName = name.substring(3, name.length() - 1);
		} else { // string start with black
			if (name.substring(5, 6).equals("K")) {
				subName = name.substring(5, name.length());
			} else
				subName = name.substring(5, name.length() - 1);
		}
		if (subName.equals("Rook")) {

			if (toX == x)// 只移動y軸座標
			{
				grid = Math.abs(toY - y);
				if (toY - y < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[y - 1 - i][toX] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[y + 1 + i][toX] != null) {
							return false;
						}
					}
				}
				c.isKingOrNot(c, toX, toY);
				return true;
			} else // 只移動x軸座標
			{
				grid = Math.abs(toX - x);
				if (toX - x < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toY][x - i - 1] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toY][x + i + 1] != null) {
							return false;
						}
					}
				}
				c.isKingOrNot(c, toX, toY);
				return true;
			}
		} else if (subName.equals("Horse")) {
			if (Math.abs(toX - x) == 2 && Math.abs(toY - y) == 1) {
				if (c.getChessboard()[y][x + ((toX - x) / 2)] != null) {
					return false;
				}
				c.isKingOrNot(c, toX, toY);
				return true;
			} else if (Math.abs(toY - y) == 2 && Math.abs(toX - x) == 1) {
				if (c.getChessboard()[y + ((toY - y) / 2)][x] != null) {
					return false;
				}
				c.isKingOrNot(c, toX, toY);
				return true;
			}
			return false;
		} else if (subName.equals("Elephant")) {
			if (color == 0) { // 假如為紅色
				if (toY <= 4) { // 假如沒過河或超過範圍
					return false;
				}
			} else {
				if (toY >= 5) {
					return false;
				}
			}
			if (Math.abs(toX - x) == 2 && Math.abs(toY - y) == 2) { // 只能斜向移動
																	// 往右邊移動
				if (c.getChessboard()[y + (toY - y) / 2][x + (toX - x) / 2] == null) { // 如果沒有塞象眼

					return true;
				}

				else {
					return false;
				}
			} else
				return false;

		} else if (subName.equals("Warrior")) {
			if (toX > 5 || toX < 3)
				return false;
			else {
				if (color == 0) { // 假如為紅色
					if (toY <= 6) { // 判斷是否在九宮格內
						return false;
					}
				} else {// 假如為黑色
					if (toY >= 3) {// 判斷是否在九宮格內
						return false;
					}
				}
			}

			if (Math.abs(toX - x) == 1 && Math.abs(toY - y) == 1) { // 只能斜向移動
				return true;
			}
		} else if (subName.equals("King")) {
			if (toX < 3 || toX > 5)
				return false;
			else {
				if (color == 0) { // 假如為紅色
					if (toY <= 6) {
						return false;
					}
				} else { // 假如為黑色
					if (toY >= 3) {
						return false;
					}
				}
			}

			if ((Math.abs(toX - x)) + (Math.abs(toY - y)) == 1) { // 只能移動Y軸

				return true;
			} else {
				return false;
			}
		} else if (subName.equals("Cannon")) {
			if (toX == x) {
				grid = Math.abs(toY - y);
			} else {
				grid = Math.abs(toX - x);
			}
			if (c.getChessboard()[toY][toX] != null) {
				count = 0;
				for (int i = 0; i < grid - 1; i++) {
					if (toX == x) {
						if (toY - y < 0) {
							if (c.getChessboard()[y - 1 - i][toX] != null) {
								count++;
							}
						} else {
							if (c.getChessboard()[y + 1 + i][toX] != null) {
								count++;
							}
						}
					} else {
						if (toX - x < 0) {
							if (c.getChessboard()[y][toX + 1 + i] != null) {
								count++;
							}
						} else {
							if (c.getChessboard()[y][toX - 1 - i] != null) {
								count++;
							}
						}
					}
				}
				if (count == 1) {
					c.isKingOrNot(c, toX, toY);
					return true;
				} else {
					return false;
				}
			}
			if (toX == x)// 只移動y軸座標
			{

				if (toY - y < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[y - 1 - i][toX] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[y + 1 + i][toX] != null) {
							return false;
						}
					}
				}
				return true;
			} else // 只移動x軸座標
			{

				if (toX - x < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toY][x - i - 1] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toY][x + i + 1] != null) {
							return false;
						}
					}
				}
				return true;
			}

		} else {// subName.equals("Pawn")
			if (color == 0)// 紅色
			{
				if (y > 4)// 只能往前走
				{
					if (y - toY == 1 && toX == x) {

						return true;
					}
				} else {// y <= 4
					if ((Math.abs(x - toX) + Math.abs(y - toY)) == 1) {
						if ((y - toY) != -1) {
							c.isKingOrNot(c, toX, toY);
							return true;
						}
					}
				}
			} else// 黑色
			{
				if (y < 5)// 只能往前走
				{
					if (toY - y == 1 && toX == x) {
						return true;
					}
				} else {// y >= 5
					if ((Math.abs(x - toX) + Math.abs(y - toY)) == 1) {
						if ((y - toY) != 1) {
							c.isKingOrNot(c, toX, toY);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
