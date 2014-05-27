package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private FileWriter fw;
	private File file;
	private String path;
	private String strDate;
	private SimpleDateFormat sdFormat;
	private Date date;

	public Logger(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
		file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		sdFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		date = new Date();
		strDate = sdFormat.format(date);
		System.out.println(strDate);
		try {
			fw = new FileWriter(path + strDate + ".txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void log(String msg) {
		try {
			fw.write("[" + strDate + "] " + msg + "\r\n");
			fw.close();
			fw = new FileWriter(path + strDate + ".txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger l = new Logger("c:/SQA_Server/server/");
		l.log("123456789");

	}

}
