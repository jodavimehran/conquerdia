package ca.concordia.encs.conquerdia.engine.util;

public class FileHelper {

	public static String GetResourcePath(String resource) {
		return ClassLoader.getSystemClassLoader().getResource(resource).getPath();
	}
}
