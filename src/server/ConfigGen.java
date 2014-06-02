package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ConfigGen {
	private String filePath = null;
	private File file;

	public ConfigGen() {
		// TODO Auto-generated constructor stub
		java.net.URL url = ConfigGen.class.getProtectionDomain().getCodeSource().getLocation();

		try {
			filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		}
	}

	public void updateConfig(String fileName, String data) {
		file = new File(filePath + "/config");
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file.getPath() + "/" + fileName + ".txt"))));
			bw.write(data);
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getConfig(String fileName) {
		file = new File(filePath + "/config");
		file.mkdir();
		String data = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file.getPath() + "/" + fileName + ".txt"))));
			data = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigGen c = new ConfigGen();
		c.updateConfig("1", "123");
	}
}
