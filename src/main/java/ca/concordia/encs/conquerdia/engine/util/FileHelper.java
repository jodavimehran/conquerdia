package ca.concordia.encs.conquerdia.engine.util;

public class FileHelper {

	public static String getResourcePath(String resource) {
		return ClassLoader.getSystemClassLoader().getResource(resource).getPath();
	}

	public static String combinePath(String path1, String path2) {
		return path1 + "/" + path2;
	}
}
