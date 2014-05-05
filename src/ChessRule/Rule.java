package ChessRule;
public class Rule {
	
	public boolean move(String name, int x, int y, int toX, int toY, int color,Chessboard c ,int whoseTurn) {
		String subName = "";
		
		int count = 0;
		int grid = 0;
		
		if(whoseTurn != color)// whose turn 
		{
			return false;
		}
		if(c.getChessboard()[toX][toY] != null && c.getChessboard()[toX][toY].getColor() == color)
		{
			return false;
		}
		if (color == 0)// string start with red
		{// get chess name without red or black in beginning and end with number for chess
			subName = name.substring(3, name.length() - 1); 
		} else { // string start with black
			subName = name.substring(5, name.length() - 1);
		}
		if (subName.equals("Rook")) {
			
			if (toX == x)// 只移動y軸座標
			{
				grid = Math.abs(toY - y);
				if (toY - y < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toX][y + 1 + i] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toX][y - 1 - i] != null) {
							return false;
						}
					}
				}
				if(c.getChessboard()[toX][toY] != null)
				{
					isKingOrNot(c, toX, toY);
				}
				return true;
			} else if (toY == y)// 只移動x軸座標
			{
				grid = Math.abs(toX - x);
				if (toX - x < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[x - i - 1][toY] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[x + i + 1][toY] != null) {
							return false;
						}
					}
				}
				if(c.getChessboard()[toX][toY] != null)
				{
					isKingOrNot(c, toX, toY);
				}
				return true;
			}
		} else if (subName.equals("Horse")) {
			if (Math.abs(toX - x) == 2 && Math.abs(toY - y) == 1) {
				if (c.getChessboard()[x + ((toX - x) / 2)][y] != null) {
					return false;
				}
				if(c.getChessboard()[toX][toY] != null)
				{
					isKingOrNot(c, toX, toY);
				}
				return true;
			} else if (Math.abs(toY - y) == 2 && Math.abs(toX - x) == 1) {
				if (c.getChessboard()[x][y + ((toY - y) / 2)] != null) {
					return false;
				}
				if(c.getChessboard()[toX][toY] != null)
				{
					isKingOrNot(c, toX, toY);
				}
				return true;
			}
			return false;
		} else if (subName.equals("Elephant")) {
			if (color == 0) { // 假如為紅色
				if (toY > 4 && toY < 10 && toX > -1 && toX < 9) { // 假如沒過河或超過範圍

					if ((x + 2) == toX && y + 2 == toY) { // 只能斜向移動 往右邊移動
						if (c.getChessboard()[x + 1][y + 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else if ((x - 2) == toX && y + 2 == toY)// 往左邊移動
					{
						if (c.getChessboard()[x - 1][y + 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else if ((x - 2) == toX && y - 2 == toY) { // 只能斜向移動
						if (c.getChessboard()[x - 1][y - 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else if ((x + 2) == toX && y - 2 == toY) { // 只能斜向移動
						if (c.getChessboard()[x + 1][y - 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else if (color == 1) {// 假如為黑色
				if (toY > -1 && toY < 5 && toX > -1 && toX < 9) { // 假如沒過河或超過範圍
					if ((x + 2) == toX && y + 2 == toY) { // 只能斜向移動
						if (c.getChessboard()[x + 1][y + 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else if ((x - 2) == toX && y + 2 == toY)// 往左邊移動
					{
						if (c.getChessboard()[x - 1] [y + 1]== null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else if ((x - 2) == toX && y - 2 == toY) { // 只能斜向移動
						if (c.getChessboard()[x - 1][y - 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
						
						} else {
							return false;
						}
					} else if ((x + 2) == toX && y - 2 == toY) { // 只能斜向移動
						if (c.getChessboard()[x + 1][y - 1] == null) { // 如果沒有塞象眼
						{
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
							
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		} else if (subName.equals("Warrior")) {
			if (color == 0) { // 假如為紅色
				if (toX >= 3 && toX <= 5 && toY <= 9 && toY >= 7) { // 判斷是否在九宮格內
					if (Math.abs(toX - x) == 1 && Math.abs(toY - y) == 1) { // 只能斜向移動
					{
						if(c.getChessboard()[toX][toY] != null)
						{
							isKingOrNot(c, toX, toY);
						}
						return true;
					}
						
					}
				} else {
					return false;
				}
			} else if (color == 1) {// 假如為黑色
				if (toX >= 3 && toX <= 5 && toY <= 2 && toY >= 0) {// 判斷是否在九宮格內
					if (Math.abs(toX - x) == 1 && Math.abs(toY - y) == 1) { // 只能斜向移動
					{
						if(c.getChessboard()[toX][toY] != null)
						{
							isKingOrNot(c, toX, toY);
						}	
						return true;
					}
						
					}
				} else {
					return false;
				}
			}
		} else if (subName.equals("King")) {
			if (color == 0) { // 假如為紅色
				if (toX < 6 && toX > 2 && toY > 6 && toY < 10) {
					if (toX == x && Math.abs(toY - y) == 1) { // 只能移動Y軸
//						c.setLocation(x, y, toX, toY, c);
						return true;
					} else if (toY == y && Math.abs(toX - x) == 1) {
//						c.setLocation(x, y, toX, toY, c);
						return true;
					}
				} else {
					return false;
				}
			} else if (color == 1) { // 假如為黑色
				if (toX < 6 && toX > 2 && toY > -1 && toY < 5) {
					if (toX == x && Math.abs(toY - y) == 1) { // 只能移動Y軸
//						c.setLocation(x, y, toX, toY, c);
						return true;
					} else if (toY == y && Math.abs(toX - x) == 1) {
//						c.setLocation(x, y, toX, toY, c);
						return true;
					} else {
						return false;
					}
				}
			}
		} else if (subName.equals("Cannon")) {
			if (toX == x) {
				grid = Math.abs(toY - y);
			} else if (toY == y) {
				grid = Math.abs(toX - x);
			}
			if (c.getChessboard()[toX][toY] != null) {
				count = 0;
				for (int i = 0; i < grid - 1; i++) {
					if (toX == x) {
						if (toY - y < 0) {
							if (c.getChessboard()[toX] [y - 1 - i]!= null) {
								count++;
							}
						} else {
							if (c.getChessboard()[toX][y + 1 + i] != null) {
								count++;
							}
						}
					} else if (toY == y) {
						if (toX - x < 0) {
							if (c.getChessboard()[toX + 1 + i][y] != null) {
								count++;
							}
						} else {
							if (c.getChessboard()[toX - 1 - i][y] != null) {
								count++;
							}
						}
					}

				}
			
				if (count == 1 && (toX == x || toY == y)) {
					if(c.getChessboard()[toX][toY] != null)
					{
						isKingOrNot(c, toX, toY);
					}
					return true;
				} else {
					return false;
				}
			}
			if (toX == x)// 只移動y軸座標
			{

				if (toY - y < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toX][y - 1 - i] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[toX][y + 1 + i] != null) {
							return false;
						}
					}
				}
				return true;
			} else if (toY == y)// 只移動x軸座標
			{

				if (toX - x < 0) {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[x - i - 1][toY] != null) {
							return false;
						}
					}
				} else {
					for (int i = 0; i < grid - 1; i++) {
						if (c.getChessboard()[x + i + 1][toY] != null) {
							return false;
						}
					}
				}
				return true;
			}

		} else if (subName.equals("Pawn")) {
			if (color == 0)// 紅色
			{
				if (y > 4)// 只能往前走
				{
					if (y - toY == 1 && toX == x) {
						
						return true;
					}
				} else if (y <= 4) {
					if ((Math.abs(x - toX) + Math.abs(y - toY)) == 1) {
						if ((y - toY) != -1) {
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
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
				} else if (y >= 5) {
					if ((Math.abs(x - toX) + Math.abs(y - toY)) == 1) {
						if ((y - toY) != 1) {
							if(c.getChessboard()[toX][toY] != null)
							{
								isKingOrNot(c, toX, toY);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public static void isKingOrNot(Chessboard c , int toX, int toY)
	{
		if(c.getChessboard()[toX][toY].getName().equals("redKing")||c.getChessboard()[toX][toY].getName().equals("blackKing"))
		{
			c.getChessboard()[toX][toY].setDead();
		}
		
	}
}
