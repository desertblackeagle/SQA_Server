import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.MessagePack;

public class Server {
	private ServerSocket serverSocket;
	private Socket server;
	private ArrayList<SocketPack> al;
	private ClientBridge bridge;
	private SocketPack s1, s2;

	public Server() {
		// TODO Auto-generated constructor stub
		al = new ArrayList<SocketPack>();
		new Thread(new Runnable() {
			public void run() {
				try {
					serverSocket = new ServerSocket(56);
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

	private boolean check(ObjectInputStream in) {
		try {
			MessagePack check = (MessagePack) in.readObject();
			if (check.getAction().equals("check")) {
				String apiToken = check.getObjectHashMap().get("API Token").toString();
				String userToken = check.getObjectHashMap().get("User Token").toString();
				System.out.println(apiToken + " " + userToken);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void waitForClient() {

		try {
			server = serverSocket.accept();
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
			System.out.println("Address : " + server.getInetAddress());
			if (check(in)) {
				SocketPack sp = new SocketPack(server, in, output);
				al.add(sp);
				System.out.println("Add suss ");
			} else {
				output.writeObject(new MessagePack("check fail"));
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
//						al.remove(s1);
//						al.remove(s2);
						System.out.println("bridge");
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								bridge = new ClientBridge(s1, s2);
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
