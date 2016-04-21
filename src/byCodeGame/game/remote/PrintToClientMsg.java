package byCodeGame.game.remote;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;

public class PrintToClientMsg {
	// public void printMsg(IoBuffer buffer, IoSession session) {
	// if (null != session.getAttribute("roleId")) {
	// int roleId = (Integer) session.getAttribute("roleId");
	// Role user = RoleCache.getRole(roleId);
	// if (logger.isDebugEnabled())
	// if (null == user) {
	// ChatRole chatRole = ChatRoleCache.getRole(roleId);
	// logger.debug("role name =>" + chatRole.getName()
	// + ":role id =>" + chatRole.getId());
	// } else {
	//
	// logger.debug("role name =>" + user.getName()
	// + ":role id =>" + user.getId());
	// }
	//
	// }
	//
	// buffer.order(ByteOrder.LITTLE_ENDIAN);
	// short b = buffer.getShort();
	// short y = buffer.getShort();
	// short s = buffer.getShort();
	// short c = buffer.getShort();
	//
	// StringBuffer miyao = new StringBuffer();
	// miyao.append(b).append(y).append(s).append(c);
	// // 组装包长度
	// int baochang = buffer.getInt();
	// buffer.get();
	// // 组装请求指令
	// int zhiling = buffer.getShort();
	// if (logger.isDebugEnabled())
	// logger.debug("回写buffer. 秘钥:{}, 包长: {}, 发送指令: {}", new Object[] {
	// miyao.toString(), baochang, zhiling });
	// while (buffer.remaining() > 0) {
	// // 读取1个字节表示参数类型
	// byte type = buffer.get();
	//
	// if (type == RemoteAppConfig.getMsgInt()) {// int类型
	// int parmInt = buffer.getInt();
	// if (logger.isDebugEnabled())
	// logger.debug("整型参数:" + parmInt);
	// } else if (type == RemoteAppConfig.getMsgShort()) {
	// short parmShort = buffer.getShort();
	// if (logger.isDebugEnabled())
	// logger.debug("短整型参数:" + parmShort);
	// } else if (type == RemoteAppConfig.getMsgLong()) {
	// long parmLong = buffer.getLong();
	// if (logger.isDebugEnabled())
	// logger.debug("长整型参数:" + parmLong);
	// } else if (type == RemoteAppConfig.getMsgStr()) {// 字符串类型
	// int length = buffer.getInt();
	// byte[] strBytes = new byte[length];
	// buffer.get(strBytes);
	// try {
	// String msgStr = new String(strBytes, "UTF-8");
	// if (logger.isDebugEnabled())
	// logger.debug("字符参数长度: {}, 字符参数: {}", length, msgStr);
	// } catch (UnsupportedEncodingException e) {
	// }
	// }
	// }
	// }

	public static void printIOBuffer(Integer roleId, Message msg) {
		short type = msg.getType();	//指令号
		// logger.debug("服务器{}{}的消息，{}指令号为{}", new Object[] { sendType, roleId,
		// sendType, type });
		if(type == (short)21501||type == (short)20303)
		{
			return;
		}
		System.out.println("服务器回写" + roleId + "消息，指令号为:" + type);
		IoBuffer iob = msg.getData();
		while (iob.hasRemaining()) {
			byte paramType = iob.get();
			switch (paramType) {
			case 3:
				// logger.debug("roleId:{}--{}指令号为{},short型参数:{}", new Object[]
				// {
				// roleId, sendType, type, iob.getShort() });
				System.out.println("服务器回写" + roleId + "指令号："+ type+ ";" + "消息,short型参数:"
						+ iob.getShort());
				break;
			case 1:
				// logger.debug("roleId:{}--{}指令号为{},int型参数:{}", new Object[] {
				// roleId, sendType, type, iob.getInt() });
				System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,int型参数:"
						+ iob.getInt());
				break;
			case 4:
				// logger.debug("roleId:{}--{}指令号为{},long型参数:{}", new Object[] {
				// roleId, sendType, type, iob.getLong() });
				System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,long型参数:"
						+ iob.getLong());
				break;
			case 2:
				int length = iob.getInt();
				byte[] strBytes = new byte[length];
				iob.get(strBytes);
				try {
					String msgStr = new String(strBytes, "UTF-8");
					// logger.debug("roleId:{}--{}指令号为{},字符参数长度: {}, 字符参数: {}",
					// new Object[] { roleId, sendType, type, length,
					// msgStr });
					System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,字符参数长度:" + length
							+ ", 字符参数:" + msgStr);
				} catch (UnsupportedEncodingException e) {
					// logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
				break;
			case 5:
				byte bresult = iob.get();

				System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,boolean参数:" + bresult);
				break;
			case 6:
				byte bresultNum = iob.get();

				System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,byte参数:" + bresultNum);
				break;
			case 7:
				int blength = iob.getInt();
				byte[] bBytes = new byte[blength];
				iob.get(bBytes);
				System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,byte数组参数长度:"
						+ blength);
				break;
			case 8:
				double doubleNum = iob.getDouble();
				System.out.println("服务器回写" + roleId + "指令号："+ type + ";" +"消息,bdouble参数:"
						+ doubleNum);
				break;
			default:
				// logger.debug("没有对应参数类型！！！！");
				System.err.println("没有对应参数类型！！！！");
			}
		}
	}
}
