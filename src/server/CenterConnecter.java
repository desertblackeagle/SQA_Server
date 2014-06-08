package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class CenterConnecter {

	public String doPost(String sURL, String data, String cookie, String referer, String charset) {
		String jsonData = null;
		BufferedWriter wr = null;
		try {
			TrustManager[] trustMyCerts = new TrustManager[] { new MyX509TrustManager() };
			URL url = new URL(sURL);

			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					if (true) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
					}
					return urlHostName.equalsIgnoreCase(session.getPeerHost());
				}
			};

			// Initial SSLContext
			SSLContext sc = null;
			try {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustMyCerts, new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpsURLConnection URLConn = (HttpsURLConnection) url.openConnection();
			URLConn.setDoOutput(true);
			URLConn.setDoInput(true);
			((HttpsURLConnection) URLConn).setRequestMethod("POST");
			HttpsURLConnection.setFollowRedirects(true);

			if (cookie != null) {
				URLConn.setRequestProperty("Cookie", cookie);
			}
			if (referer != null) {
				URLConn.setRequestProperty("Referer", referer);
			}

			URLConn.setRequestProperty("Content-Type", "application/json");
			URLConn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
			DataOutputStream dos = new DataOutputStream(URLConn.getOutputStream());
			dos.write(data.getBytes("UTF-8"));
			BufferedReader rd = new BufferedReader(new InputStreamReader(URLConn.getInputStream(), charset));
			String line = "";
			while ((line = rd.readLine()) != null) {
				jsonData = line;
			}
			rd.close();
		} catch (IOException e) {
			jsonData = null;
			System.out.println(e);
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (java.io.IOException ex) {
					System.out.println(ex);
				}
				wr = null;
			}
		}
		return jsonData;
	}
}
