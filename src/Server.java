import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;
import java.util.ArrayList;

public class Server {
	private ServerSocket serverSocket;
	private Socket server;
	private ArrayList<SocketPack> al;
	private ClientBridge bridge;

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

	private void waitForClient() {

		try {
			server = serverSocket.accept();
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
			System.out.println("Address : " + server.getInetAddress());
			SocketPack sp = new SocketPack(server, in, output);
			al.add(sp);
			System.out.println("Add suss ");
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
						SocketPack s1, s2;
						s1 = al.get(0);
						s2 = al.get(1);
						al.remove(s1);
						al.remove(s2);
						System.out.println("bridge");
						bridge = new ClientBridge(s1, s2);
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
