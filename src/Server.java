import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {
	private ServerSocket serverSocket;
	private Socket server;
	private ArrayList<SocketPack> al;
	private ClientBridge bridge;
	private SocketPack s1, s2;
	private Logger logger;
	private CenterConnecter centerConnecter;
	private BufferedReader buf;

	public Server() {
		// TODO Auto-generated constructor stub
		al = new ArrayList<SocketPack>();
		logger = new Logger("c:/SQA_Server/server/");
		centerConnecter = new CenterConnecter();
		new Thread(new Runnable() {
			public void run() {
				try {
					serverSocket = new ServerSocket(56);
					System.out.println("server start");
					logger.log("server start");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true) {
					Thread.interrupted();
					waitForClient();
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				match();
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				initAdminControl();
			}
		}).start();
	}

	private void initAdminControl() {
		buf = new BufferedReader(new InputStreamReader(System.in));
		String adminMessage;
		try {
			while ((adminMessage = buf.readLine()) != null) {
				if (adminMessage.equals("shutdown")) {
					// Shutdown the multi chat server.
					System.exit(1);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkAPITokenAndSecretToken(String jsonString) {
		JSONObject player = new JSONObject(jsonString.toString().replaceAll(" ", ""));
		System.out.println("center response : " + player);
		logger.log("center response : " + player);
		if (!player.has("detail")) {
			return true;
		}
		return false;
	}

	private boolean check(BufferedReader clientReader) {
		JSONObject check;
		try {
			check = new JSONObject(clientReader.readLine());
			System.out.println("[ check ]");
			logger.log("[ check ]");
			if (check.get("action").equals("check")) {
				String apiToken = check.get("API Token").toString();
				String secreatToken = check.get("secreatToken").toString();
				System.out.println("client api token : " + apiToken + "\nclient secreat token : " + secreatToken);
				logger.log("client api token : " + apiToken + "\nclient secreat token : " + secreatToken);
				JSONObject toCenter = new JSONObject();
				toCenter.put("api_token", "83d25eaeb2cebf405adc604f9262c660b6ce2d8d83687dfb01d4226ff027a049dd15d102fda255f9d5c810f83f63b81aaa66");
				toCenter.put("secret_token", "48c8f0406516acc6d163af570722465578abdf14fa03dbd46850455aa1376f5f56809a8dabe2381718344f5ac6131050a283");
				toCenter.put("user_api_token", apiToken);
				toCenter.put("user_secret_token", secreatToken);
				String stringFromCenter = centerConnecter.doPost("https://sqa.swim-fish.info/steam/dev/api/steam_user_check?format=json", toCenter.toString(), null, null, "UTF-8");
				return checkAPITokenAndSecretToken(stringFromCenter);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private void connectClient(Socket subServer) {
		JSONObject sendToClient = new JSONObject();
		BufferedReader clientReader;
		try {
			clientReader = new BufferedReader(new InputStreamReader(subServer.getInputStream(), "utf-8"));
			PrintStream clientWriter = new PrintStream(subServer.getOutputStream(), true, "utf-8");
			System.out.println("Address : " + subServer.getInetAddress() + " is connecting");
			logger.log("Address : " + subServer.getInetAddress() + " is connecting");
			if (check(clientReader)) {
				SocketPack sp = new SocketPack(subServer, clientReader, clientWriter);
				al.add(sp);
				System.out.println(subServer.getInetAddress() + " Add suss\n");
				logger.log(subServer.getInetAddress() + " Add suss");
			} else {
				sendToClient.put("action", "check fail");
				clientWriter.println(sendToClient.toString());
				System.out.println(subServer.getInetAddress() + " fail to client \n");
				logger.log(subServer.getInetAddress() + " fail to client");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void waitForClient() {
		try {
			server = serverSocket.accept();
			System.out.println(server + " accept");
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					connectClient(server);
				}
			}).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void match() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					Thread.interrupted();
//					System.out.println("al is less 2 " + a);
					if (al.size() >= 2) {
						s1 = al.get(0);
						s2 = al.get(1);
						List<SocketPack> remove = new ArrayList<SocketPack>();
						remove.add(s1);
						remove.add(s2);
						al.removeAll(remove);
						new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								bridge = new ClientBridge(s1, s2);
								logger.log("bridge " + s1.getSocket() + " and " + s2.getSocket());
							}
						}).start();
					} else {
					}
				}
			}
		}).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server s = new Server();
	}
}
