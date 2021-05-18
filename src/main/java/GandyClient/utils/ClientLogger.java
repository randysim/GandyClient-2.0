package GandyClient.utils;

public class ClientLogger {
	public static String clientName = "[GandyClient]";
	
	public static void info (String info) {
		System.out.println(ClientLogger.clientName + "[INFO] " + info);
	}
	public static void error (String error) {
		System.out.println(ClientLogger.clientName + "[ERROR] " + error);
	}
}
