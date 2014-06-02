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

	public Logger(String dir) {
		// TODO Auto-generated constructor stub

		java.net.URL url = Logger.class.getProtectionDomain().getCodeSource().getLocation();

		try {
			path = java.net.URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (path.endsWith(".jar")) {
			path = path.substring(0, path.lastIndexOf("/") + 1);
		}
		path = path + "/log/" + dir + "/";
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
		msg = msg.replaceAll("\n", "\r\n");
		String now = sdFormat.format(new Date());
		try {
			fw.write("[" + now + "] " + msg + "\r\n");
			fw.close();
			fw = new FileWriter(path + strDate + ".txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
