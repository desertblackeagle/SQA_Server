package ChessRule;
public class Chess {
	private String name;
	private int color;
	private boolean dead = false;
	private int x, y;

	public Chess(int x, int y, int color, String name) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;

	}

	public int getY() {
		return this.y;

	}
	public void setDead()
	{
		this.dead = true;
	}
	public boolean getDead()
	{
		return this.dead ;
	}
	public int getColor()
	{
		return this.color ;
	}
}
