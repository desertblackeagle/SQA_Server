import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置
	// 先利用?來做標示

	private String dropdbSQL = "DROP TABLE User ";

	private String createdbSQL = "CREATE TABLE User (" + "    id     INTEGER " + "  , name    VARCHAR(20) " + "  , passwd  VARCHAR(20))";

	private String insertdbSQL = "insert into User(id,name,passwd) " + "select ifNULL(max(id),0)+1,?,? FROM User";

	private String selectSQL = "select * from User ";

	public DataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 註冊driver
			con = DriverManager.getConnection("jdbc:mysql://192.168.1.239/chinese_game_server?useUnicode=true&characterEncoding=Big5", "michael", "123456");
			// 取得connection

			// jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
			// localhost是主機名,test是database名
			// useUnicode=true&characterEncoding=Big5使用的編碼
			System.out.println("資料庫連線成功");
		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		}// 有可能會產生sqlexception
		catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
			System.out.println("資料庫無法連線!!!連線至DataBase2");
			try {
				con = DriverManager.getConnection("jdbc:mysql://123.204.84.144/chinese_game_server?useUnicode=true&characterEncoding=Big5", "michael", "123456");
				System.out.println("資料庫連線成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("Exception :" + x.toString());
				System.out.println("資料庫無法連線!!!");
				System.out.println("請檢查帳號及密碼是否有誤，");
				System.out.println("或者mysql服務是否關閉。");
			}
		}

	}

	// 新增資料
	// 可以看看PrepareStatement的使用方式
	public void insertTable(String name, String passwd) {
		try {
			pst = con.prepareStatement(insertdbSQL);

			pst.setString(1, name);
			pst.setString(2, passwd);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("InsertDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	public String getPlayerWin(String userToken) {
		String SQL = "SELECT win FROM `PlayerInfo` WHERE UserToken=" + userToken + ";";
		String win = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 循環搜尋結果，請依你的資料庫來作提取資料欄位，以將下面的資料欄位對映你的資料欄位

		try {
			while (rs.next()) {
				win = rs.getString("win");
				System.out.println(win);
				count++;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count == 0) {
			return "";
		} else {
			return win;
		}
	}

	public String getPlayerLose(String userToken) {
		String SQL = "SELECT lose FROM `PlayerInfo` WHERE UserToken=" + userToken + ";";
		String lose = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 循環搜尋結果，請依你的資料庫來作提取資料欄位，以將下面的資料欄位對映你的資料欄位

		try {
			while (rs.next()) {
				lose = rs.getString("lose");
				System.out.println(lose);
				count++;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count == 0) {
			return "";
		} else {
			return lose;
		}
	}

	// 完整使用完資料庫後,記得要關閉所有Object
	// 否則在等待Timeout時,可能會有Connection poor的狀況
	private void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}

	public static void main(String[] args) {
		final DataBase m = new DataBase();
		m.getPlayerLose("123456789");
		m.getPlayerLose("987654321");
		m.getPlayerWin("123456789");
		m.getPlayerWin("987654321");
	}
}
