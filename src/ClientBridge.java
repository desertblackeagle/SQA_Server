import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import net.MessagePack;

public class ClientBridge {
	Socket clientA, clientB;
	BufferedReader clientReaderA, clientReaderB;
	PrintStream clientWriterA, clientWriterB;
	private ObjectInputStream clientObjInputStreamA, clientObjInputStreamB;
	private ObjectOutputStream clientObjOutputStreamA, clientObjOutputStreamB;
	Thread t;
	boolean flag = true;
	MessagePack sendStart;

	public ClientBridge(SocketPack clientA, SocketPack clientB) {
		// TODO Auto-generated constructor stub
		System.out.println("Bridge ing");
		this.clientA = clientA.getSocket();
		this.clientB = clientB.getSocket();

		clientObjInputStreamA = clientA.getOis();
		clientObjOutputStreamA = clientA.getOos();
		clientObjInputStreamB = clientB.getOis();
		clientObjOutputStreamB = clientB.getOos();
		sendStart = new MessagePack("start game");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					clientObjOutputStreamA.writeObject(sendStart);
					System.out.println("Send start to A");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					clientObjOutputStreamB.writeObject(sendStart);
					System.out.println("Send start to B");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

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
					System.out.println(msgPack.getObjectHashMap().get("chess name"));
					System.out.println(msgPack.getObjectHashMap().get("chess X"));
					System.out.println(msgPack.getObjectHashMap().get("chess Y"));
					System.out.println(msgPack.getObjectHashMap().get("chess toX"));
					System.out.println(msgPack.getObjectHashMap().get("chess toY"));
					System.out.println(msgPack.getObjectHashMap().get("chess color"));
					//
					// 呼叫判斷副函式
					//
					MessagePack send = new MessagePack("move");
					send.addData("chess X", msgPack.getObjectHashMap().get("chess X"));
					send.addData("chess Y", msgPack.getObjectHashMap().get("chess Y"));
					send.addData("chess toX", msgPack.getObjectHashMap().get("chess toX"));
					send.addData("chess toY", msgPack.getObjectHashMap().get("chess toY"));
					clientObjOutputStreamA.writeObject(send);
					clientObjOutputStreamB.writeObject(send);
				} else if (msgPack.getAction().equals("chat")) {
					String chat = (String) msgPack.getObjectHashMap().get("chat msg");
					MessagePack sendToB = new MessagePack("chat");
					sendToB.addData("chat msg", chat);
					clientObjOutputStreamB.writeObject(sendToB);
				} else if (msgPack.getAction().equals("sayBye")) {

				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("A is close");
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
					System.out.println(msgPack.getObjectHashMap().get("chess name"));
					System.out.println(msgPack.getObjectHashMap().get("chess X"));
					System.out.println(msgPack.getObjectHashMap().get("chess Y"));
					System.out.println(msgPack.getObjectHashMap().get("chess toX"));
					System.out.println(msgPack.getObjectHashMap().get("chess toY"));
					System.out.println(msgPack.getObjectHashMap().get("chess color"));
					//
					// 呼叫判斷副函式
					//
					MessagePack send = new MessagePack("move");
					send.addData("chess X", msgPack.getObjectHashMap().get("chess X"));
					send.addData("chess Y", msgPack.getObjectHashMap().get("chess Y"));
					send.addData("chess toX", msgPack.getObjectHashMap().get("chess toX"));
					send.addData("chess toY", msgPack.getObjectHashMap().get("chess toY"));
					clientObjOutputStreamA.writeObject(send);
					clientObjOutputStreamB.writeObject(send);
				} else if (msgPack.getAction().equals("chat")) {
					String chat = (String) msgPack.getObjectHashMap().get("chat msg");
					MessagePack sendToA = new MessagePack("chat");
					sendToA.addData("chat msg", chat);
					clientObjOutputStreamA.writeObject(sendToA);
				} else if (msgPack.getAction().equals("sayBye")) {

				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B is close");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
