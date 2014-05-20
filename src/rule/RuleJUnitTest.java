package rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RuleJUnitTest {

	@Test
	public void test() {
		Chessboard ch = new Chessboard();
		Rule r = new Rule();

		// whoseTurn
		assertEquals(false, r.move("redRook1", 0, 9, 0, 7, 0, ch, 1));// y==toY and toX < X
		// rook
		assertEquals(false, r.move("redRook1", 0, 9, 0, 5, 0, ch, 0));// toY==y and toX < x 越過6,0兵
		assertEquals(true, r.move("redRook1", 0, 9, 0, 7, 0, ch, 0));// y==toY and toX < X
		ch.setLocation(0, 9, 0, 7, ch);
		assertEquals(false, r.move("redRook1", 0, 7, 4, 7, 0, ch, 0));// x==toX and toY > y 越過7,1炮
		assertEquals(true, r.move("redRook1", 0, 8, 4, 8, 0, ch, 0));// x==toX and toY > y
		assertEquals(true, r.move("redRook1", 4, 8, 2, 8, 0, ch, 0));// x==toX and toY < y
		assertEquals(true, r.move("blackRook2", 8, 0, 8, 2, 1, ch, 1));// toY==y and toX > x
		ch.setLocation(0, 8, 2, 8, ch);
		assertEquals(false, r.move("blackRook2", 8, 2, 6, 2, 1, ch, 1));// toX==x and toY < y 越過包
		assertEquals(false, r.move("redRook2", 8, 9, 8, 6, 0, ch, 0));// toY==y and toX < x 吃掉兵
		assertEquals(false, r.move("blackRook2", 8, 2, 8, 5, 1, ch, 1));// toY==y and toX > x 越過3,8卒

		// King
		assertEquals(false, r.move("blackKing", 4, 0, 4, 2, 1, ch, 1));// abs(toX-x ) != 1
		assertEquals(false, r.move("blackKing", 4, 0, 4, 4, 1, ch, 1));// toY==y, toX-x ==1, toY >=3
		assertEquals(true, r.move("blackKing", 4, 1, 3, 1, 1, ch, 1));// toX==x, toY-y==1, 2< toY <6
		assertEquals(false, r.move("blackKing", 3, 1, 2, 1, 1, ch, 1));// toX==x, toY-y==1, 2 >= toY
		assertEquals(false, r.move("redKing", 4, 9, 4, 5, 0, ch, 0));// Y <= 6
		assertEquals(true, r.move("redKing", 4, 9, 4, 8, 0, ch, 0));// Y not <= 6
		ch.setLocation(0, 7, 0, 9, ch);
		ch.setLocation(2, 9, 0, 7, ch);
		ch.setLocation(6, 9, 4, 7, ch);
		assertEquals(false, r.move("redKing", 4, 9, 2, 9, 0, ch, 0));// toX < 3
		assertEquals(false, r.move("redKing", 4, 9, 6, 9, 0, ch, 0));// toX > 5
		ch.setLocation(4, 7, 6, 9, ch);
		ch.setLocation(0, 7, 2, 9, ch);
		ch.setLocation(0, 9, 0, 7, ch);

		// warrior
		assertEquals(true, r.move("redWarrior1", 3, 9, 4, 8, 0, ch, 0));// abs.x==1 && abs.y==1
		assertEquals(false, r.move("redWarrior1", 3, 9, 3, 8, 0, ch, 0));// abs.x!=1 && abs.y==1
		assertEquals(false, r.move("redWarrior1", 3, 9, 4, 5, 0, ch, 0));// toY<=6
		assertEquals(false, r.move("redWarrior1", 3, 9, 2, 8, 0, ch, 0));// toX < 3,
		assertEquals(false, r.move("blackWarrior1", 3, 0, 4, 4, 1, ch, 1));// toY >=3
		assertEquals(false, r.move("blackWarrior1", 3, 0, 4, 2, 1, ch, 1));// toY not >=3
		assertEquals(false, r.move("blackWarrior1", 3, 1, 6, 1, 1, ch, 1));// toX>5

		// horse

		assertEquals(true, r.move("redHorse1", 1, 9, 2, 7, 0, ch, 0));// abs(x)==1 && abs(Y)==2
		assertEquals(false, r.move("blackHorse1", 1, 0, 4, 2, 1, ch, 1));// abs(x)==3 && abs(Y)==2
		ch.setLocation(0, 9, 0, 8, ch);
		ch.setLocation(0, 7, 0, 8, ch);
		ch.setLocation(0, 8, 1, 8, ch);
		assertEquals(false, r.move("redHorse1", 1, 9, 2, 7, 0, ch, 0));// 拐馬腳 1,8 has redRook1
		assertEquals(false, r.move("redHorse1", 1, 9, 3, 8, 0, ch, 0));// 拐馬腳 2,9 has redElephant1
		ch.setLocation(2, 9, 4, 7, ch); // move redElephant1 to 4,7
		assertEquals(true, r.move("redHorse1", 1, 9, 3, 8, 0, ch, 0));// abs(x)==2 && abs(Y)==1
		assertEquals(false, r.move("redHorse1", 4, 7, 2, 7, 0, ch, 0));

		// pawn
		assertEquals(false, r.move("redPawn1", 0, 6, 1, 6, 0, ch, 0));// y>4, y-toY!=1, x!=toX
		assertEquals(false, r.move("redPawn1", 0, 6, 1, 5, 0, ch, 0));// y>4, y-toY==1 ,x!=toX
		assertEquals(true, r.move("redPawn1", 0, 6, 0, 5, 0, ch, 0));// y>4 , y-toY==1 , x==toX
		ch.setLocation(0, 6, 0, 5, ch);
		ch.setLocation(0, 5, 0, 4, ch);
		assertEquals(true, r.move("redPawn1", 0, 4, 1, 4, 0, ch, 0));// y<=4 , x+y==1
		assertEquals(false, r.move("redPawn1", 0, 4, 1, 2, 0, ch, 0));// y<=4 , x+y!=1
		ch.setLocation(0, 4, 0, 3, ch);
		assertEquals(false, r.move("redPawn1", 0, 3, 0, 4, 0, ch, 0));// y<=4 , y-toY!=-1
		ch.setLocation(0, 3, 0, 4, ch);
		assertEquals(false, r.move("blackPawn2", 2, 3, 3, 3, 1, ch, 1));// y<5, toY-y!=1, x!=toX
		assertEquals(false, r.move("blackPawn2", 2, 3, 3, 4, 1, ch, 1));// y<5, toY-y==1 ,x!=toX
		assertEquals(true, r.move("blackPawn2", 2, 3, 2, 4, 1, ch, 1));// y<5 , toY-y==1 , x==toX
		ch.setLocation(2, 3, 2, 4, ch);
		ch.setLocation(2, 4, 2, 5, ch);
		assertEquals(true, r.move("blackPawn2", 2, 5, 3, 5, 1, ch, 1));// y>=5 , x+y==1
		assertEquals(false, r.move("blackPawn2", 2, 5, 3, 6, 1, ch, 1));// y>=5 , x+y!=1
		ch.setLocation(2, 5, 2, 6, ch);
		assertEquals(false, r.move("blackPawn2", 2, 6, 2, 5, 1, ch, 1));// y>=5 , toY-y!=1
		ch.setLocation(2, 6, 2, 5, ch);

		// cannon

		// toX toY != NULL
		// toX==x
		assertEquals(true, r.move("redCannon2", 7, 7, 7, 0, 0, ch, 0));// eat 7,0 blackHorse2 , count==1 ,Y > toY
		assertEquals(false, r.move("blackCannon1", 1, 2, 1, 9, 1, ch, 1));// eat 1,9 redHorse1 , count==2, Y < toY
		// toY==y
		ch.setLocation(4, 3, 4, 4, ch);
		ch.setLocation(7, 2, 7, 4, ch);
		assertEquals(true, r.move("blackCannon2", 7, 4, 0, 4, 1, ch, 1));// eat 0,4 redPawn1 , count==1 , toX < x
		ch.setLocation(4, 4, 4, 3, ch);
		ch.setLocation(7, 7, 3, 3, ch);
		assertEquals(false, r.move("redCannon2", 3, 3, 8, 3, 0, ch, 0));// eat 8,3 blackPawn5 , count==2 ,toX > x
		ch.setLocation(1, 7, 1, 6, ch);
		// toX toY==NULL
		// toX==x
		assertEquals(true, r.move("blackCannon1", 1, 2, 1, 5, 1, ch, 1));// toY > y ,nothing on the way
		assertEquals(false, r.move("blackCannon1", 1, 2, 1, 7, 1, ch, 1));// toY > y , has 1,6 redCannon1
		assertEquals(true, r.move("redCannon1", 1, 6, 1, 3, 0, ch, 0));// toY < y ,nothing on the way
		assertEquals(false, r.move("redCannon1", 1, 6, 1, 1, 0, ch, 0));// toY < y , has 1,2 blackCannon1
		ch.setLocation(1, 6, 1, 7, ch);
		// toY==y
		assertEquals(true, r.move("blackCannon1", 1, 2, 5, 2, 1, ch, 1));// toX > x ,nothing on the way
		ch.setLocation(3, 3, 2, 3, ch);
		assertEquals(false, r.move("redCannon2", 2, 3, 7, 3, 0, ch, 0));// toX > x ,has 4,3 blackPawn3 and 6,3 blackPawn4
		ch.setLocation(2, 3, 2, 4, ch);
		assertEquals(true, r.move("blackCannon2", 7, 4, 4, 4, 1, ch, 1));// toX < x ,nothing on the way
		assertEquals(false, r.move("blackCannon2", 7, 4, 1, 4, 1, ch, 1));// toX < x ,has 3,4 redCannon2
		// elephant
		ch.setLocation(4, 7, 2, 9, ch);
		assertEquals(true, r.move("redElephant2", 6, 9, 8, 7, 0, ch, 0));// x+2, y-2
		ch.setLocation(1, 8, 3, 8, ch);
		assertEquals(false, r.move("redElephant1", 2, 9, 4, 7, 0, ch, 0));// x+2 , y-2, but has redRook1 at 3,8
		assertEquals(false, r.move("redElephant2", 6, 5, 4, 3, 0, ch, 0));// x-2 , y-2, but toY < 4
		ch.setLocation(2, 0, 0, 2, ch);
		assertEquals(false, r.move("blackElephant1", 2, 4, 4, 6, 1, ch, 1));// x+2, y+2 , toY > 5
		assertEquals(false, r.move("blackElephant1", 2, 4, 3, 3, 1, ch, 1));// abs(x)!=2 && abs(y)!=2
		assertEquals(false, r.move("blackElephant1", 2, 4, 4, 1, 1, ch, 1));// abs(x)==2, abs(y)!=2

		// KingDead
		ch.printChessBoard();
		assertEquals(false, ch.kingDead());
		ch.setLocation(7, 4, 4, 4, ch);
		assertEquals(true, r.move("blackCannon2", 4, 4, 4, 9, 1, ch, 1));// redKing at 4,9
		assertEquals(true, ch.kingDead());
		ch.setLocation(4, 4, 7, 4, ch);
		ch.setLocation(2, 4, 4, 4, ch);
		assertEquals(true, r.move("redCannon2", 4, 4, 4, 0, 0, ch, 0));// blackKing at 4,0
		assertEquals(true, ch.kingDead());
	}

}
