package byCodeGame.game.util;

public class SysManager {

	/** gm口令	 	*/
	private static String GM_PWD;
	/** 服务器入口开关	*/
	private static boolean SERVICE_FLAG;

	public static String getGM_PWD() {
		return GM_PWD;
	}

	public static void setGM_PWD(String gM_PWD) {
		GM_PWD = gM_PWD;
	}

	public static boolean isSERVICE_FLAG() {
		return SERVICE_FLAG;
	}

	public static void setSERVICE_FLAG(boolean sERVICE_FLAG) {
		SERVICE_FLAG = sERVICE_FLAG;
	}
	
}
