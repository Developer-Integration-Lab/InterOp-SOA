package net.aegis.mv.mockdata;

public class IdGenerator {
	
	private static int baseId = 50000;
	
	public static synchronized int getNextId() {
		return ++baseId;
	}

}
