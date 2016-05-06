package byCodeGame.game.common;

/**
 * @author ming 创建时间：2014-10-17
 */

public class ErrorCode {
	public static final short ERROR = 0;
	public static final short SUCCESS = 1;
	public static final short SHORT_TWO = 2;

	/** 服务器正在维护 */
	public static final short SERVICE_CLOSED = 30001;
	/** 注册信息不完整*/
	public static final short REGISTER_LACK_INFO = 30102;
	public static final short REGISTER_ACCOUNT_REPEAT = 30103;
	public static final short REGISTER_NAME_REPEAT = 30104;
	public static final short REGISTER_FAILED = 30105;
	public static final short NO_ROLE = 30106;
	
	/**玩家名重复*/
	public static final short ROLE_NAME_REPEAT = 30201;
	
	
	/**商品不足*/
	public static final short MAKET_GOODS_LACK = 30402;

}
