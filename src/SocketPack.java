
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketPack {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public SocketPack(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

}
