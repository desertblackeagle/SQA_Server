import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import net.MessagePack;

public class ClientBridge {
	Socket clientA, clientB;
	BufferedReader clientReaderA, clientReaderB;
	PrintStream clientWriterA, clientWriterB;
	private ObjectInputStream clientObjInputStreamA, clientObjInputStreamB;
	private ObjectOutputStream clientObjOutputStreamA, clientObjOutputStreamB;
	Thread t;
	boolean flag = true;

	public ClientBridge(SocketPack clientA, SocketPack clientB) {
		// TODO Auto-generated constructor stub
		System.out.println("Bridge ing");
		this.clientA = clientA.getSocket();
		this.clientB = clientB.getSocket();

		clientObjInputStreamA = clientA.getOis();
		clientObjOutputStreamA = clientA.getOos();
		clientObjInputStreamB = clientB.getOis();
		clientObjOutputStreamB = clientB.getOos();

//			clientReaderA = new BufferedReader(new InputStreamReader(clientA.getInputStream(), "utf-8"));
//			clientWriterA = new PrintStream(clientA.getOutputStream(), true, "utf-8");
//			clientReaderB = new BufferedReader(new InputStreamReader(clientB.getInputStream(), "utf-8"));
//			clientWriterB = new PrintStream(clientB.getOutputStream(), true, "utf-8");
		System.out.println("exe thread A");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Thread A strt");
				talkToA();
			}
		}).start();
		System.out.println("exe thread B");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Thread B strt");
				talkToB();
			}
		}).start();

	}

	private void talkToA() {
		MessagePack msgPack;
		try {
			while ((msgPack = (MessagePack) clientObjInputStreamA.readObject()) != null) {
				Thread.interrupted();
				System.out.println("A : " + msgPack);
				if (msgPack.getAction().equals("move")) {

				} else if (msgPack.getAction().equals("chat")) {
					String chat = (String) msgPack.getObjectHashMap().get("chat");
					MessagePack sendToB = new MessagePack("chat");
					sendToB.addData("chat", chat);
					clientObjOutputStreamB.writeObject(sendToB);
				} else if (msgPack.getAction().equals("sayBye")) {

				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void talkToB() {
		MessagePack msgPack;
		try {
			while ((msgPack = (MessagePack) clientObjInputStreamB.readObject()) != null) {
				Thread.interrupted();
				System.out.println("B : " + msgPack);
				if (msgPack.getAction().equals("move")) {

				} else if (msgPack.getAction().equals("chat")) {
					String chat = (String) msgPack.getObjectHashMap().get("chat");
					MessagePack sendToA = new MessagePack("chat");
					sendToA.addData("chat", chat);
					clientObjOutputStreamA.writeObject(sendToA);
				} else if (msgPack.getAction().equals("sayBye")) {

				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private void talkToA() {
//		String serverMessage;
//		try {
//			while ((serverMessage = clientReaderA.readLine()) != null) {
//				System.out.println("A : " + serverMessage);
//				clientWriterB.println(serverMessage);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void talkToB() {
//		String serverMessage;
//		try {
//			while ((serverMessage = clientReaderB.readLine()) != null) {
//				System.out.println("B : " + serverMessage);
//				clientWriterA.println(serverMessage);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private void unPackMessage() {

	}
}
