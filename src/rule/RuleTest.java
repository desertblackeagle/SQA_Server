package rule;

public class RuleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rule r = new Rule();
		Chessboard c = new Chessboard();
		c.printChessBoard();
		System.out.println(r.move("redHorse2", 6, 7, 7, 5, 0, c, 0));
		c.printChessBoard();
	}

}
