package byCodeGame.game.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {
	private static Properties properties = null;

	private static int bufferSize;
	private static int idleTime;
	private static String charSet;
	// tcp服务端口
	private static int wanServerPort;
	private static int compress;

	private static byte msgInt;
	private static byte msgStr;
	private static byte msgShort;
	private static byte msgLong;
	private static byte msgBoolean;
	private static byte msgByte;
	private static byte msgBytes;
	private static byte msgDouble;

	/**
	 * 初始加载config.properties数据
	 */
	public static void loadConfig() {
		String fileName = "conf/serverConfig.properties";
		try {
			InputStream input = new BufferedInputStream(new FileInputStream(
					new File(fileName)));
			properties = new Properties();
			properties.load(input);
			input.close();
			bufferSize = Integer.parseInt(properties.get("bufferSize")
					.toString());
			compress = Integer.parseInt(properties.get("compress").toString());
			wanServerPort = Integer.parseInt(properties.get("wanServerPort")
					.toString());
			idleTime = Integer.parseInt(properties.get("idleTime").toString());
			charSet = (String) properties.get("charSet");
			msgInt = Byte.parseByte(properties.get("msgInt").toString());
			msgStr = Byte.parseByte(properties.get("msgStr").toString());
			msgShort = Byte.parseByte(properties.get("msgShort").toString());
			msgLong = Byte.parseByte(properties.get("msgLong").toString());
			msgBoolean = Byte
					.parseByte(properties.get("msgBoolean").toString());
			msgByte = Byte.parseByte(properties.get("msgByte").toString());
			msgBytes = Byte.parseByte(properties.get("msgBytes").toString());
			msgDouble = Byte.parseByte(properties.get("msgDouble").toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return 获得缓冲区大小
	 */
	public static int getBufferSize() {
		return bufferSize;
	}

	/**
	 * 
	 * @return 获取读写通道进入空闲状态时间
	 */
	public static int getIdleTime() {
		return idleTime;
	}

	/**
	 * 获取服务启动端口
	 */
	public static int getWanServerPort() {
		return wanServerPort;
	}

	/**
	 * 
	 * @return 获得字符编码设置
	 */
	public static String getCharSet() {
		return charSet;
	}

	/**
	 * 获取整型参数标识
	 * 
	 * @return
	 */
	public static byte getMsgInt() {
		return msgInt;
	}

	/**
	 * 获取字符参数标识
	 * 
	 * @return
	 */
	public static byte getMsgStr() {
		return msgStr;
	}

	/**
	 * 获取短整型参数标识
	 * 
	 * @return
	 */
	public static byte getMsgShort() {
		return msgShort;
	}

	/**
	 * 获取长型参数标识
	 * 
	 * @return
	 */
	public static byte getMsgLong() {
		return msgLong;
	}

	/**
	 * 获取boolean参数标识
	 * 
	 * @return
	 */
	public static byte getMsgBoolean() {
		return msgBoolean;
	}

	public static byte getMsgByte() {
		return msgByte;
	}

	public static int getCompress() {
		return compress;
	}

	public static byte getMsgBytes() {
		return msgBytes;
	}

	public static byte getMsgDouble() {
		return msgDouble;
	}
}
