import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

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
			dos.writeBytes(data);

			BufferedReader rd = new BufferedReader(new InputStreamReader(URLConn.getInputStream(), charset));
			String line = "";
			while ((line = rd.readLine()) != null) {
//				System.out.println(line);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CenterConnecter c = new CenterConnecter();
		JSONObject j = new JSONObject();
		j.put("api_token", "83d25eaeb2cebf405adc604f9262c660b6ce2d8d83687dfb01d4226ff027a049dd15d102fda255f9d5c810f83f63b81aaa66");
		j.put("secret_token", "48c8f0406516acc6d163af570722465578abdf14fa03dbd46850455aa1376f5f56809a8dabe2381718344f5ac6131050a283");
//		j.put("api_token", "62cb1b250c6728e48adf5718abbbcd958d650d9ba9bde338527066d20f133b4d48837c72cfa641982136279050ca79fea6f4");
//		j.put("secret_token", "fc9ccc5a6fbb820d142220ed0f97ffbce27e91eb4041cefeb738391bdb2d3b1f5357227f3322cb16c4dcc6dd151adac94adc");
		String json = c.doPost("https://sqa.swim-fish.info/steam/dev/api/steam_user_list?format=json", j.toString(), null, null, "UTF-8");
		JSONArray jsonAry = new JSONArray(json);
		for (int i = 0; i < jsonAry.length(); i++) {
			System.out.println(jsonAry.get(i));
			JSONObject jsonO = new JSONObject(jsonAry.get(i).toString().replaceAll(" ", ""));
			for(String s: jsonO.keySet()){
				System.out.println(s+" : "+jsonO.get(s));
			}
			System.out.println();
		}

	}

}
