package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import rule.Chessboard;
import rule.Rule;

public class ClientBridge {
	private BufferedReader clientAReader, clientBReader;
	private PrintStream clientAWriter, clientBWriter;
	private String APITokenA, APITokenB;
	private String playerAName, playerBName;
	private String playerAWin, playerBWin;
	private String playerALose, playerBLose;
	private Rule rule;
	private Chessboard chessBoard;
	private int whoseTurn = 0;
	private boolean gameIsOver = false;
	private DataBase playerDataBase;
	private Logger logger;
	private CenterConnecter center;

	public ClientBridge(SocketPack clientA, SocketPack clientB) {
		// TODO Auto-generated constructor stub
		logger = new Logger("ClientBridge");
		System.out.println("Bridge");
		logger.log("Bridge" + clientA.getSocket() + " and " + clientB.getSocket());
		chessBoard = new Chessboard();
		rule = new Rule();
		playerDataBase = new DataBase();
		center = new CenterConnecter();
		clientAReader = clientA.getClientReader();
		clientAWriter = clientA.getClientWriter();
		clientBWriter = clientB.getClientWriter();
		clientBReader = clientB.getClientReader();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				talkToA();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				talkToB();
			}
		}).start();
	}

	private void talkToA() {
		System.out.println("start talk to A");
		logger.log("start talk to A");
		getClientInfo("A");
		JSONObject clientMsg;
		String clientInput;
		try {
			while ((clientInput = clientAReader.readLine()) != null) {
				Thread.interrupted();
				System.out.println("msg from client A : " + clientInput);
				logger.log("msg from client A : " + clientInput);
				clientMsg = new JSONObject(clientInput);
				if (clientMsg.get("action").equals("move")) {
					clientMoveAction(clientMsg, clientAWriter, clientBWriter);
				} else if (clientMsg.get("action").equals("chat")) {
					clientChatAction(clientMsg, clientBWriter);
				} else if (clientMsg.get("action").equals("give info")) {
					clientGiveInfoAction(clientMsg, clientAWriter, clientBWriter, 1);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (gameIsOver == true) {
				tellClientRivalIsExit(clientBWriter);
				System.out.println("client A is disconnect");
				logger.log("client A is disconnect");
			} else {
				System.out.println("tell B is win because A is exit");
				logger.log("tell B is win because A is exit");
				JSONObject sendToClient = new JSONObject();
				sendToClient.put("action", "win");
				clientBWriter.println(sendToClient);
				tellClientRivalIsExit(clientBWriter);
				System.out.println("client A is disconnect");
				logger.log("client A is disconnect");
				playerBWin = String.valueOf((Integer.valueOf(playerBWin) + 1));
				playerDataBase.updatePlayerWinAndLose(APITokenB, playerBWin, playerBLose);
				playerALose = String.valueOf((Integer.valueOf(playerALose) + 1));
				playerDataBase.updatePlayerWinAndLose(APITokenA, playerAWin, playerALose);
				gameIsOver = true;
				JSONObject toCenter = new JSONObject();
				toCenter.put("api_token", "cd791e1d192a6f9090a0c89f5605bbea005a33c9e684aafa87668510ac188c5f0d803fc13c9b4497fb78f94a7dca526eb508");
				toCenter.put("name", playerBName);
				toCenter.put("chess", "None");
				toCenter.put("action", "Win");
				toCenter.put("eaten", "None");
				toCenter.put("fromx", "None");
				toCenter.put("fromy", "None");
				toCenter.put("tox", "None");
				toCenter.put("toy", "None");
				center.doPost("https://sqa.swim-fish.info/steam/game_info/update/?format=json", toCenter.toString(), null, null, "UTF-8");
			}
		}
	}

	private void talkToB() {
		System.out.println("start talk to B");
		logger.log("start talk to B");
		getClientInfo("B");
		JSONObject clientMsg;
		String clientInput;
		try {
			while ((clientInput = clientBReader.readLine()) != null) {
				Thread.interrupted();
				System.out.println("msg from client B : " + clientInput);
				logger.log("msg from client B : " + clientInput);
				clientMsg = new JSONObject(clientInput);
				if (clientMsg.get("action").equals("move")) {
					clientMoveAction(clientMsg, clientBWriter, clientAWriter);
				} else if (clientMsg.get("action").equals("chat")) {
					clientChatAction(clientMsg, clientAWriter);
				} else if (clientMsg.get("action").equals("give info")) {
					clientGiveInfoAction(clientMsg, clientBWriter, clientAWriter, 0);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (gameIsOver == true) {
				tellClientRivalIsExit(clientAWriter);
				System.out.println("client B is disconnect");
				logger.log("client B is disconnect");
			} else {
				System.out.println("tell A is win because B is exit");
				logger.log("tell A is win because B is exit");
				JSONObject sendToClient = new JSONObject();
				sendToClient.put("action", "win");
				clientAWriter.println(sendToClient);
				tellClientRivalIsExit(clientAWriter);
				System.out.println("client B is disconnect");
				logger.log("client B is disconnect");
				playerAWin = String.valueOf((Integer.valueOf(playerAWin) + 1));
				playerDataBase.updatePlayerWinAndLose(APITokenA, playerAWin, playerALose);
				playerBLose = String.valueOf((Integer.valueOf(playerBLose) + 1));
				playerDataBase.updatePlayerWinAndLose(APITokenB, playerBWin, playerBLose);
				gameIsOver = true;
				JSONObject toCenter = new JSONObject();
				toCenter.put("api_token", "cd791e1d192a6f9090a0c89f5605bbea005a33c9e684aafa87668510ac188c5f0d803fc13c9b4497fb78f94a7dca526eb508");
				toCenter.put("name", playerAName);
				toCenter.put("chess", "None");
				toCenter.put("action", "Win");
				toCenter.put("eaten", "None");
				toCenter.put("fromx", "None");
				toCenter.put("fromy", "None");
				toCenter.put("tox", "None");
				toCenter.put("toy", "None");
				center.doPost("https://sqa.swim-fish.info/steam/game_info/update/?format=json", toCenter.toString(), null, null, "UTF-8");
			}
		}
	}

	private void tellClientRivalIsExit(PrintStream to) {
		JSONObject sendToClient = new JSONObject();
		sendToClient.put("action", "sayBye");
		to.println(sendToClient);
	}

	private void getClientInfo(String aOrB) {
		if (aOrB.equals("A")) {
			JSONObject getInfoFromClient = new JSONObject();
			getInfoFromClient.put("action", "get info");
			clientAWriter.println(getInfoFromClient);
			System.out.println("tell A to give info " + getInfoFromClient);
			logger.log("tell A to give info " + getInfoFromClient);
		} else {
			JSONObject getInfoFromClient = new JSONObject();
			getInfoFromClient.put("action", "get info");
			clientBWriter.println(getInfoFromClient);
			System.out.println("tell B to give info " + getInfoFromClient);
			logger.log("tell B to give info " + getInfoFromClient);
		}
	}

	private void clientMoveAction(JSONObject clientMsg, PrintStream from, PrintStream to) {
		System.out.println("move detail from client");
		System.out.println("chess name : " + clientMsg.get("chess name"));
		System.out.println("chess from x : " + clientMsg.get("chess X"));
		System.out.println("chess from y : " + clientMsg.get("chess Y"));
		System.out.println("chess to x : " + clientMsg.get("chess toX"));
		System.out.println("chess to y : " + clientMsg.get("chess toY"));
		System.out.println("chess color : " + clientMsg.get("chess color"));
		logger.log("move detail from client : color " + clientMsg.get("chess color") + " " + clientMsg.get("chess name") + " move from " + clientMsg.get("chess X") + " , " + clientMsg.get("chess Y") + " to " + clientMsg.get("chess toX") + " , " + clientMsg.get("chess toY"));
		String chessName = (String) clientMsg.get("chess name");
		int x = (Integer) clientMsg.get("chess X");
		int y = (Integer) clientMsg.get("chess Y");
		int toX = (Integer) clientMsg.get("chess toX");
		int toY = (Integer) clientMsg.get("chess toY");
		int color = (Integer) clientMsg.get("chess color");
		if (rule.move(chessName, x, y, toX, toY, color, chessBoard, whoseTurn)) {
			chessBoard.setLocation(x, y, toX, toY, chessBoard);
			chessBoard.printChessBoard();
			whoseTurn = whoseTurn ^ 1;
			System.out.println("whoseTurn : " + whoseTurn);
			logger.log("turm " + whoseTurn + " to play");
			JSONObject sendToClient = new JSONObject();
			sendToClient.put("action", "move");
			sendToClient.put("chess X", clientMsg.get("chess X"));
			sendToClient.put("chess Y", clientMsg.get("chess Y"));
			sendToClient.put("chess toX", clientMsg.get("chess toX"));
			sendToClient.put("chess toY", clientMsg.get("chess toY"));
			from.println(sendToClient);
			to.println(sendToClient);
			JSONObject toCenter = new JSONObject();
			toCenter.put("api_token", "cd791e1d192a6f9090a0c89f5605bbea005a33c9e684aafa87668510ac188c5f0d803fc13c9b4497fb78f94a7dca526eb508");
			if (from.equals(clientAWriter)) {
				toCenter.put("name", playerAName);
			} else {
				toCenter.put("name", playerBName);
			}
			toCenter.put("chess", transferChessName(chessName));
			toCenter.put("action", "Move");
			toCenter.put("eaten", "None");
			toCenter.put("fromx", x);
			toCenter.put("fromy", y);
			toCenter.put("tox", toX);
			toCenter.put("toy", toY);
			center.doPost("https://sqa.swim-fish.info/steam/game_info/update/?format=json", toCenter.toString(), null, null, "UTF-8");
			System.out.println("tell client A&B move : " + sendToClient);
			logger.log("tell client A&B move : " + sendToClient);
			if (chessBoard.kingDead()) {
				System.out.println("tell from win ");
				sendToClient = new JSONObject();
				sendToClient.put("action", "win");
				from.println(sendToClient);
				sendToClient = new JSONObject();
				sendToClient.put("action", "lose");
				to.println(sendToClient);
				gameIsOver = true;
				if (from.equals(clientAWriter)) {
					playerAWin = String.valueOf((Integer.valueOf(playerAWin) + 1));
					playerDataBase.updatePlayerWinAndLose(APITokenA, playerAWin, playerALose);
					playerBLose = String.valueOf((Integer.valueOf(playerBLose) + 1));
					playerDataBase.updatePlayerWinAndLose(APITokenB, playerBWin, playerBLose);
					toCenter = new JSONObject();
					toCenter.put("api_token", "cd791e1d192a6f9090a0c89f5605bbea005a33c9e684aafa87668510ac188c5f0d803fc13c9b4497fb78f94a7dca526eb508");
					toCenter.put("name", playerAName);
					toCenter.put("chess", "None");
					toCenter.put("action", "Win");
					toCenter.put("eaten", "None");
					toCenter.put("fromx", "None");
					toCenter.put("fromy", "None");
					toCenter.put("tox", "None");
					toCenter.put("toy", "None");
					center.doPost("https://sqa.swim-fish.info/steam/game_info/update/?format=json", toCenter.toString(), null, null, "UTF-8");
				} else {
					playerBWin = String.valueOf((Integer.valueOf(playerBWin) + 1));
					playerDataBase.updatePlayerWinAndLose(APITokenB, playerBWin, playerBLose);
					playerALose = String.valueOf((Integer.valueOf(playerALose) + 1));
					playerDataBase.updatePlayerWinAndLose(APITokenA, playerAWin, playerALose);
					toCenter = new JSONObject();
					toCenter.put("api_token", "cd791e1d192a6f9090a0c89f5605bbea005a33c9e684aafa87668510ac188c5f0d803fc13c9b4497fb78f94a7dca526eb508");
					toCenter.put("name", playerBName);
					toCenter.put("chess", "None");
					toCenter.put("action", "Win");
					toCenter.put("eaten", "None");
					toCenter.put("fromx", "None");
					toCenter.put("fromy", "None");
					toCenter.put("tox", "None");
					toCenter.put("toy", "None");
					center.doPost("https://sqa.swim-fish.info/steam/game_info/update/?format=json", toCenter.toString(), null, null, "UTF-8");
				}
			}
		} else {
			JSONObject sendToClient = new JSONObject();
			sendToClient.put("action", "move");
			sendToClient.put("chess X", clientMsg.get("chess X"));
			sendToClient.put("chess Y", clientMsg.get("chess Y"));
			sendToClient.put("chess toX", clientMsg.get("chess X"));
			sendToClient.put("chess toY", clientMsg.get("chess Y"));
			from.println(sendToClient);
			System.out.println("ilegal move !! tell client move back : " + sendToClient);
		}
	}

	private void clientChatAction(JSONObject clientMsg, PrintStream to) {
		String chat = (String) clientMsg.get("chat msg");
		JSONObject sendToClient = new JSONObject();
		sendToClient.put("action", "chat");
		sendToClient.put("chat msg", chat);
		to.println(sendToClient);
		System.out.println("tell client chat msg : " + sendToClient);
		logger.log("tell client chat msg : " + sendToClient);
	}

	private void clientGiveInfoAction(JSONObject clientMsg, PrintStream from, PrintStream to, int team) {
		String APIToken = clientMsg.get("API Token").toString();
		String secreatToken = clientMsg.get("secreatToken").toString();
		String playerName = clientMsg.get("player name").toString();
		if (from.equals(clientAWriter)) {
			playerAName = playerName;
			System.out.println("I am " + from + " : " + playerName);
		} else {
			playerBName = playerName;
			System.out.println("I am " + from + " : " + playerName);
		}
		String playerPhoto = clientMsg.get("player photo").toString();
		String playerWin = playerDataBase.getPlayerWin(APIToken);
		String playerLose = playerDataBase.getPlayerLose(APIToken);
		if (playerWin.equals("nodata") || playerLose.equals("nodata")) {
			playerWin = "0";
			playerLose = "0";
			playerDataBase.insertPlayerWinAndLose(APIToken, playerWin, playerLose);
			System.out.println(from + " first insert player info");
		}
		if (from.equals(clientAWriter)) {
			APITokenA = APIToken;
			playerAWin = playerWin;
			playerALose = playerLose;
			System.out.println("player A\n" + APITokenA + "\n" + playerAWin + "\n" + playerALose + "\n");
		} else {
			APITokenB = APIToken;
			playerBWin = playerWin;
			playerBLose = playerLose;
			System.out.println("player B\n" + APITokenB + "\n" + playerBWin + "\n" + playerBLose + "\n");
		}
		JSONObject sendToFrom = new JSONObject();
		sendToFrom.put("action", "winAndLose");
		sendToFrom.put("player win", playerWin);
		sendToFrom.put("player lose", playerLose);
		from.println(sendToFrom);
		System.out.println("from player win and lose " + playerWin + " " + playerLose);
		System.out.println(playerName);
		System.out.println(APIToken);
		System.out.println(secreatToken);
		System.out.println(playerPhoto + "\n");
		JSONObject sendTo = new JSONObject();
		sendTo.put("action", "rival info");
		sendTo.put("player name", playerName);
		sendTo.put("player photo", playerPhoto);
		sendTo.put("player win", playerWin);
		sendTo.put("player lose", playerLose);
		sendTo.put("team", team);
		to.println(sendTo);
		System.out.println("tell client rival info : " + sendTo);
	}

	private static String transferChessName(String chessName) {
		if (chessName.equals("redKing") || chessName.equals("blackKing")) {

		} else {
			chessName = chessName.substring(0, chessName.length() - 1);
		}
		switch (chessName) {
		case "redPawn":
			chessName = "兵";
			break;
		case "blackPawn":
			chessName = "卒";
			break;
		case "redHorse":
			chessName = "傌";
			break;
		case "blackHorse":
			chessName = "馬";
			break;
		case "redKing":
			chessName = "帥";
			break;
		case "blackKing":
			chessName = "將";
			break;
		case "redRook":
			chessName = "俥";
			break;
		case "blackRook":
			chessName = "車";
			break;
		case "redCannon":
			chessName = "炮";
			break;
		case "blackCannon":
			chessName = "包";
			break;
		case "redElephant":
			chessName = "相";
			break;
		case "blackElephant":
			chessName = "象";
			break;
		case "redWarrior":
			chessName = "仕";
			break;
		case "blackWarrior":
			chessName = "士";
			break;
		}
		System.out.println("transfor name " + chessName);
		return chessName;
	}
}
