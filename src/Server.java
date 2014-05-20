import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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

	public Server() {
		// TODO Auto-generated constructor stub
		al = new ArrayList<SocketPack>();
		logger = new Logger("c:/SQA_Server/server/");
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
		match();

	}

	private boolean check(BufferedReader clientReader) {
		JSONObject check;
		try {
			check = new JSONObject(clientReader.readLine());
			System.out.println("check : " + check);
			if (check.get("action").equals("check")) {
				String apiToken = check.get("API Token").toString();
				String userToken = check.get("User Token").toString();
				System.out.println(apiToken + " " + userToken);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void waitForClient() {
		JSONObject sendToClient = new JSONObject();
		try {
			server = serverSocket.accept();
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(server.getInputStream(), "utf-8"));
			PrintStream clientWriter = new PrintStream(server.getOutputStream(), true, "utf-8");
			System.out.println("Address : " + server.getInetAddress() + " is connecting");
			logger.log("Address : " + server.getInetAddress() + " is connecting");
			if (check(clientReader)) {
				SocketPack sp = new SocketPack(server, clientReader, clientWriter);
				al.add(sp);
				System.out.println(server.getInetAddress() + " Add suss");
				logger.log(server.getInetAddress() + " Add suss");
			} else {
				sendToClient.put("action", "check fail");
				clientWriter.println(sendToClient.toString());
			}
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
