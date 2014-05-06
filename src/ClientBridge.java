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
	private Socket clientA, clientB;
	private ObjectInputStream clientObjInputStreamA, clientObjInputStreamB;
	private ObjectOutputStream clientObjOutputStreamA, clientObjOutputStreamB;
	private Thread t;
	boolean flag = true;
	private MessagePack sendStart;
	private String APITokenA, APITokenB;
	private String userTokenA, userTokenB;
	private String playerAName, playerBName;
	private String playerPhotoA, playerPhotoB;

	public ClientBridge(SocketPack clientA, SocketPack clientB) {
		// TODO Auto-generated constructor stub
		System.out.println("Bridge ing");
		this.clientA = clientA.getSocket();
		this.clientB = clientB.getSocket();

		clientObjInputStreamA = clientA.getOis();
		clientObjOutputStreamA = clientA.getOos();
		clientObjInputStreamB = clientB.getOis();
		clientObjOutputStreamB = clientB.getOos();
		sendStart = new MessagePack("get info");
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					clientObjOutputStreamA.writeObject(sendStart);
					System.out.println("Send get info to A");
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
					System.out.println("Send get info to B");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		System.out.println("exe thread A");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Thread A start");
				talkToA();
			}
		}).start();
		System.out.println("exe thread B");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Thread B start");
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

				} else if (msgPack.getAction().equals("give info")) {
					APITokenB = msgPack.getObjectHashMap().get("API Token").toString();
					userTokenB = msgPack.getObjectHashMap().get("User Token").toString();
					playerBName = msgPack.getObjectHashMap().get("player name").toString();
					playerPhotoB = msgPack.getObjectHashMap().get("player photo").toString();
					System.out.println(APITokenB);
					System.out.println(userTokenB);
					System.out.println(playerBName);
					System.out.println(playerPhotoB);
					MessagePack sendToB = new MessagePack("rival info");
					sendToB.addData("player name", playerBName);
					sendToB.addData("player photo", playerPhotoB);
					sendToB.addData("team", 1);
					clientObjOutputStreamB.writeObject(sendToB);
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

				} else if (msgPack.getAction().equals("give info")) {
					APITokenA = msgPack.getObjectHashMap().get("API Token").toString();
					userTokenA = msgPack.getObjectHashMap().get("User Token").toString();
					playerAName = msgPack.getObjectHashMap().get("player name").toString();
					playerPhotoA = msgPack.getObjectHashMap().get("player photo").toString();
					System.out.println(APITokenA);
					System.out.println(userTokenA);
					System.out.println(playerAName);
					System.out.println(playerPhotoA);
					MessagePack sendToA = new MessagePack("rival info");
					sendToA.addData("player name", playerAName);
					sendToA.addData("player photo", playerPhotoA);
					sendToA.addData("team", 0);
					clientObjOutputStreamA.writeObject(sendToA);
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
