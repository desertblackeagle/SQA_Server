package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SocketPack {
	private Socket socket;
	private BufferedReader clientReader;
	private PrintStream clientWriter;

	public SocketPack(Socket socket, BufferedReader clientReader, PrintStream clientWriter) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.clientReader = clientReader;
		this.clientWriter = clientWriter;
	}

	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getClientReader() {
		return clientReader;
	}

	public PrintStream getClientWriter() {
		return clientWriter;
	}

}
