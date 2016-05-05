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
	// 注册信息不完整
	public static final short REGISTER_LACK_INFO = 30002;

	public static final short REGISTER_ACCOUNT_REPEAT = 30003;
	public static final short REGISTER_NAME_REPEAT = 30004;

	public static final short REGISTER_FAILED = 30005;
	public static final short NO_ROLE = 30006;

}
