package ChessRule;

public class Chessboard {
	Chess[][] p = new Chess[10][9];// Y X

	private Chess redHorse1;
	private Chess redHorse2;
	private Chess blackHorse1;
	private Chess blackHorse2;
	private Chess redRook1;
	private Chess redRook2;
	private Chess blackRook1;
	private Chess blackRook2;
	private Chess redCannon1;
	private Chess redCannon2;
	private Chess blackCannon1;
	private Chess blackCannon2;
	private Chess redPawn1;
	private Chess redPawn2;
	private Chess redPawn3;
	private Chess redPawn4;
	private Chess redPawn5;
	private Chess blackPawn1;
	private Chess blackPawn2;
	private Chess blackPawn3;
	private Chess blackPawn4;
	private Chess blackPawn5;
	private Chess redKing;
	private Chess blackKing;
	private Chess redElephant1;
	private Chess redElephant2;
	private Chess blackElephant1;
	private Chess blackElephant2;
	private Chess redWarrior1;
	private Chess redWarrior2;
	private Chess blackWarrior1;
	private Chess blackWarrior2;

	public Chessboard() {
		initChess();
	}

	public void initChess() {
		redRook1 = new Chess(0, 9, 0, "redRook1");
		redRook2 = new Chess(8, 9, 0, "redRook2");
		blackRook1 = new Chess(0, 0, 1, "blackRook1");
		blackRook2 = new Chess(8, 0, 1, "blackRook2");
		redHorse1 = new Chess(1, 9, 0, "redHorse1");
		redHorse2 = new Chess(7, 9, 0, "redHorse2");
		blackHorse1 = new Chess(1, 0, 1, "blackHorse1");
		blackHorse2 = new Chess(7, 0, 1, "blackHorse2");
		redElephant1 = new Chess(2, 9, 0, "redElephant1");
		redElephant2 = new Chess(6, 9, 0, "redElephant2");
		blackElephant1 = new Chess(2, 0, 1, "blackElephant1");
		blackElephant2 = new Chess(6, 0, 1, "blackElephant2");
		redWarrior1 = new Chess(3, 9, 0, "redWarrior1");
		redWarrior2 = new Chess(5, 9, 0, "redWarrior2");
		blackWarrior1 = new Chess(3, 0, 1, "blackWarrior1");
		blackWarrior2 = new Chess(5, 0, 1, "blackWarrior2");
		redKing = new Chess(4, 9, 0, "redKing");
		blackKing = new Chess(4, 0, 1, "blackKing");
		redCannon1 = new Chess(1, 7, 0, "redCannon1");
		redCannon2 = new Chess(7, 7, 0, "redCannon2");
		blackCannon1 = new Chess(1, 2, 1, "blackCannon1");
		blackCannon2 = new Chess(7, 2, 1, "blackCannon2");
		redPawn1 = new Chess(0, 6, 0, "redPawn1");
		redPawn2 = new Chess(2, 6, 0, "redPawn2");
		redPawn3 = new Chess(4, 6, 0, "redPawn3");
		redPawn4 = new Chess(6, 6, 0, "redPawn4");
		redPawn5 = new Chess(8, 6, 0, "redPawn5");
		blackPawn1 = new Chess(0, 3, 1, "blackPawn1");
		blackPawn2 = new Chess(2, 3, 1, "blackPawn2");
		blackPawn3 = new Chess(4, 3, 1, "blackPawn3");
		blackPawn4 = new Chess(6, 3, 1, "blackPawn4");
		blackPawn5 = new Chess(8, 3, 1, "blackPawn5");
		p[9][0] = redRook1;
		p[9][8] = redRook2;
		p[0][0] = blackRook1;
		p[0][8] = blackRook1;
		p[9][1] = redHorse1;
		p[9][7] = redHorse2;
		p[0][1] = blackHorse1;
		p[0][7] = blackHorse2;
		p[9][2] = redElephant1;
		p[9][6] = redElephant2;
		p[0][2] = blackElephant1;
		p[0][6] = blackElephant2;
		p[9][3] = redWarrior1;
		p[9][5] = redWarrior2;
		p[0][3] = blackWarrior1;
		p[0][5] = blackWarrior2;
		p[9][4] = redKing;
		p[0][4] = blackKing;
		p[6][0] = redPawn1;
		p[6][2] = redPawn2;
		p[6][4] = redPawn3;
		p[6][6] = redPawn4;
		p[6][8] = redPawn5;
		p[3][0] = blackPawn1;
		p[3][2] = blackPawn2;
		p[3][4] = blackPawn3;
		p[3][6] = blackPawn4;
		p[3][8] = blackPawn5;
		p[7][1] = redCannon1;
		p[7][7] = redCannon2;
		p[2][1] = blackCannon1;
		p[2][7] = blackCannon2;
	}

	public Chess[][] getChessboard() {
		return p;
	}

	public Chess getChess(int x, int y) {
		return p[y][x];
	}

	public void setLocation(int x, int y, int toX, int toY, Chessboard c) {
		p[toY][toX] = c.getChess(x, y);
		p[y][x] = null;
	}

	public boolean kingDead() {
		if (redKing.getDead() || blackKing.getDead()) {
			return true;
		}
		return false;
	}

	public boolean isKingOrNot(Chessboard c, int toX, int toY) {
		if (c.getChessboard()[toY][toX] == null) {
			return false;
		}

		else if (c.getChessboard()[toY][toX].getName().equals("redKing")
				|| c.getChessboard()[toY][toX].getName().equals("blackKing")) {
			c.getChessboard()[toY][toX].setDead();
			return true;

		} else
			return false;

	}
}