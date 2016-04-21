package byCodeGame.game.moudle.chat.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;












import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.NotableCache;
//import byCodeGame.game.cache.file.LawlessCharsCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.InfoUtils;

public class ChatServiceImpl implements ChatService {

	
	public Message chat(Role role , byte messageType, String context,IoSession ioSession){
		Message message = new Message();
		message.setType(ChatConstant.SEND_CHAT);
		//判断语言是否需要过滤
//		if(LawlessCharsCache.getLawlessCharsByString(context) != null)
//		{
//			context.replaceAll("*");
//		}
//		role.getLegionId()
		
		//判断上次发言时间与本次间隔是否符合规则
		if(timeCount(role, role.getTime_1(),ChatConstant.TIME_WORLD))
		{
			role.setTime_1(System.currentTimeMillis());
		}else
		{
			message.putShort(ErrorCode.CHAT_TIME_ERR);
			return message;
		}
	
		this.worldChat(role,messageType, context);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 私聊
	 * @param role
	 * @param type
	 * @param context
	 * @param ioSession
	 * @param targetName
	 */
	public Message privateChat(Role role , byte messageType , String context,IoSession ioSession,String targetName)
	{
		
		Message message = new Message();
		message.setType(ChatConstant.PRIVATE_CHAT);
		
		
		Role targetRole	= RoleCache.getRoleByName(targetName);
		
		if(targetRole==null)
		{
			message.putShort(ErrorCode.CHAT_TARGETID_ERR);
			return message;
		}
		IoSession targetSession = SessionCache.getSessionById(targetRole.getId());
		if(targetSession==null )
		{
			message.putShort(ErrorCode.CHAT_TARGETID_ERR);
			return message;
		}
		if(role.getFriend().getBlackListSet().contains(targetRole.getId()) ||
				targetRole.getFriend().getBlackListSet().contains(role.getId() ))
		{
			message.putShort(ErrorCode.CHAT_ERR_BLACK);
			return message;
		}
		
		//判断上次发言时间与本次间隔是否符合规则
		if(timeCount(role, role.getTime_2(),ChatConstant.TIME_PRIVATE))
		{
			role.setTime_2(System.currentTimeMillis());
		}else {
			message.putShort(ErrorCode.CHAT_TIME_ERR);
			return message;
		}
		
		//推送消息
		Message message2=new Message();
		message2.setType(ChatConstant.GET_CHAT);
		message2.put(ChatConstant.PRIVATE_TYPE);
		message2.putInt(role.getId());
		message2.putInt(targetRole.getId());
		message2.putString(role.getName());
		message2.putString(targetRole.getName());
		message2.put(messageType);
		message2.putString(this.getStrInfo(role, messageType, context));
		message2.put(role.getVipLv());
		targetSession.write(message2);
		ioSession.write(message2);
		
		
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 系统推送
	 */
	public Message systemChat(Role role, byte messageType, String context) {
		Message message = new Message();
		message.setType(ChatConstant.SEND_CHAT);

		Message message2 = new Message();
		message2.setType(ChatConstant.GET_CHAT);
		message2.put(ChatConstant.SYSTEM_TYPE);
		message2.putInt(role.getId());
		message2.putInt(ChatConstant.NO_TARGET_ID);
		message2.putString(role.getName());
		message2.putString(ChatConstant.NO_TARGET_NAME);
		message2.put(messageType);
		message2.putString(this.getStrInfo(role, messageType, context));
		message2.put(ChatConstant.NO_VIP_LV);
		Collection<IoSession> allSession = SessionCache.getAllSession();
		if (allSession.size() > 0) {
			for (IoSession is : allSession) {
				if (is != null)
					is.write(message2);
			}
		}

		message.putShort(ErrorCode.SUCCESS);
		return message;
		
	}
	/**
	 * 军团聊天
	 */
	public Message legionChat(Role role, byte messageType, String context,
			IoSession ioSession) {
		Message message =new Message();
		message.setType(ChatConstant.LEGION_CHAT);
		if(role.getLegionId() == 0)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//判断发言时间间隔
		if(timeCount(role, role.getTime_3(),ChatConstant.TIME_OTHER))
		{
			role.setTime_3(System.currentTimeMillis());
		}else {
			message.putShort(ErrorCode.CHAT_TIME_ERR);
			return message;
		}
		
		Message message2=new Message();
		message2.setType(ChatConstant.GET_CHAT);
		message2.put(ChatConstant.LEGION_TYPE);
		message2.putInt(role.getId());
		message2.putInt(ChatConstant.NO_TARGET_ID);
		message2.putString(role.getName());
		message2.putString(ChatConstant.NO_TARGET_NAME);
		message2.put(messageType);
		message2.putString(this.getStrInfo(role, messageType, context));
		message2.put(role.getVipLv());	
		Legion legion=LegionCache.getLegionById(role.getLegionId());
		Set<Integer> set= legion.getMemberSet();
		for(Integer i: set)
		{
			IoSession session=SessionCache.getSessionById(i);
			if(session != null) session.write(message2);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
		
	}

	/**
	 * 国家聊天
	 */
	public Message countryChat(Role role, byte messageType, String context,
			IoSession ioSession) {
		Message message = new Message();
		message.setType(ChatConstant.COUNTRY_CHAT);
		//判断玩家国籍
		if(role.getNation() == ChatConstant.NO_TARGET_ID)
		{
			message.putShort(ErrorCode.CHAT_ERR_NATION);
			return message;
		}
		//判断发言时间间隔
		if(timeCount(role, role.getTime_3(),ChatConstant.TIME_OTHER))
		{
			role.setTime_3(System.currentTimeMillis());
		}else {
			message.putShort(ErrorCode.CHAT_TIME_ERR);
			return message;
		}		
		//根据国籍取出玩家map
		Map<Integer, Role> tempMap = null;
		if(role.getNation() == ChatConstant.NATION_SHU)
		{
			tempMap = RoleCache.getShuMap();
		}else if (role.getNation() == ChatConstant.NATION_WEI) {
			tempMap = RoleCache.getWeiMap();
		}else if (role.getNation() == ChatConstant.NATION_WU) {
			tempMap = RoleCache.getWuMap();
		}

		Message message2=new Message();
		message2.setType(ChatConstant.GET_CHAT);
		message2.put(ChatConstant.COUNTRY_TYPE);
		message2.putInt(role.getId());
		message2.putInt(ChatConstant.NO_TARGET_ID);
		message2.putString(role.getName());
		message2.putString(ChatConstant.NO_TARGET_NAME);
		message2.put(messageType);
		message2.putString(this.getStrInfo(role, messageType, context));
		message2.put(role.getVipLv());
		for(Integer x: tempMap.keySet())
		{
			IoSession session=SessionCache.getSessionById(x);
			if(session != null) session.write(message2);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 世界聊天
	 * @param role
	 * @param type
	 * @param context
	 */
	private void worldChat(Role role ,byte messageType ,String context){
		Message msg = new Message();
		msg.setType(ChatConstant.GET_CHAT);
		
		msg.put(ChatConstant.WORLD_TYPE);
		msg.putInt(role.getId());
		msg.putInt(ChatConstant.NO_TARGET_ID);
		msg.putString(role.getName());
		msg.putString(ChatConstant.NO_TARGET_NAME);
		msg.put(messageType);
		msg.putString(this.getStrInfo(role, messageType, context));
		msg.put(role.getVipLv());
		Collection<IoSession> allSession = SessionCache.getAllSession();
		if(allSession.size() > 0){
			for(IoSession is : allSession){
				if(is != null) is.write(msg);
			}
		}
	}

	/**
	 * 判断时间的方法
	 * @param role 	账号信息
	 * @param time 	对应的时间
	 * @param t	            判断的基数
	 * @return
	 */
	private boolean timeCount(Role role,long time,long t)
	{
		if( time == 0)
		{
			//无发言时间则将目前系统时间赋值给对应属性
			return true;
		}else
		{
			//有发言时间则判断间隔是否符合标准
			long l=System.currentTimeMillis() - time;
			if( l <= t)
			{
				return false;
			}else {
				return true;
			}
		}
	}
	
	/**
	 * 根据类型发送聊天窗口信息
	 * @param role
	 */
	private String getStrInfo(Role role ,byte messageType , String context)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(context);
		switch (messageType) {
		case ChatConstant.MESSAGE_TYPE_WORDS:
			break;
		case ChatConstant.MESSAGE_TYPE_ITEM:
			//判断玩家是否拥有道具
			try {
				int tempId = Integer.parseInt(context);
				//取出玩家的道具列表
				sb.delete(0, sb.length());
				if(role.getPropMap().containsKey(tempId))
				{
					Prop prop = role.getPropMap().get(tempId);
					EquipConfig config = EquipConfigCache.getEquipConfigById(prop.getItemId());
					
//					sb.append(prop.getItemId()).append(",").append(prop.getLv())
//						.append(",").append(role.getName()).append(",");
					String temp = this.getColorStr(config.getQuality());
					String temp2 = InfoUtils.getInfoEquip(config, prop);
					sb.append("<a href='event:#equip|").append(temp2)
						.append("'><font color='").append(temp)
						.append("'>").append(config.getName()).append("</font></a>");
				}
			} catch (Exception e) {
				sb.delete(0, sb.length());
			}
			break;
		case ChatConstant.MESSAGE_TYPE_HERO:
//			//判断玩家是否拥有英雄(格式未定)
//			try {
//				int tempId = Integer.parseInt(context);
//				//取出玩家的英雄列表
//				sb.delete(0, sb.length());
//				if(role.getHeroMap().containsKey(tempId))
//				{
//					Hero hero = role.getHeroMap().get(tempId);
//					sb.append(hero.getHeroId()).append(",").append(hero.getLv())
//						.append(",").append(role.getName()).append(",");
//				}
//			} catch (Exception e) {
//				sb.delete(0, sb.length());
//			}
			//取出玩家的道具列表
			sb.delete(0, sb.length());
			int tempId = Integer.parseInt(context);
			HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(tempId);
			sb.append(role.getName()).append(",").append(context).append(",")
				.append(heroConfig.getName()).append(",").append(heroConfig.getQuality());
			break;
		case ChatConstant.MESSAGE_TYPE_REPORT:
			// 此处需要等待战报存储格式完成后补满

			break;
		/** 消息类型 官邸等级：4 */
		case ChatConstant.MESSAGE_TYPE_ROLE_LV:
			break;
		/** 消息类型 VIP：5 */
		case ChatConstant.MESSAGE_TYPE_VIP:
			break;
		/** 消息类型 升星：6 */
		case ChatConstant.MESSAGE_TYPE_RANK:
			break;
		/** 消息类型 转职：7 */
		case ChatConstant.MESSAGE_TYPE_DUTY:
			break;
		/** 消息类型 转生：8 */
		case ChatConstant.MESSAGE_TYPE_REBIRTH:
			break;
		/** 消息类型 官职：9 */
		case ChatConstant.MESSAGE_TYPE_GRADERANK:
			break;
		/** 消息类型 开始行军：10 */
		case ChatConstant.MESSAGE_TYPE_MARCH:
			break;
		/** 消息类型 战斗结果：11 */
		case ChatConstant.MESSAGE_TYPE_FIGHT_RESULT:
			break;
		/** 消息类型 城市更替：12 */
		case ChatConstant.MESSAGE_TYPE_CHANGE_CITY:
			break;
		/** 消息类型 城池被攻 */
		case ChatConstant.MESSAGE_TYPE_UNDER_ATTACK:
			break;
		case ChatConstant.MESSAGE_TYPE_LEGION:
			break;
		case ChatConstant.MESSAGE_TYPE_RANKING:
			break;
		case ChatConstant.MESSAGE_TYPE_ACTIVITY_END:
			break;
		default:
			sb.delete(0, sb.length());
			break;
		}
		return sb.toString();
	}
	
	
	private String getColorStr(int color)
	{
		String str = null;
		switch (color) {
		case 1:
			str = ChatConstant.BAISE;
			break;
		case 2:
			str = ChatConstant.LVSE;
			break;
		case 3:
			str = ChatConstant.LANSE;
			break;
		case 4:
			str = ChatConstant.ZISE;
			break;
		case 5:
			str = ChatConstant.HONGSE;
			break;
		default:
			break;
		}
		
		return str;
	}

	@Override
	public boolean checkSystemChat(byte type, int value) {
		value = (int)value;
		boolean result = false;

		if (this.isSequence(type)) {
			if (value >= NotableCache.getSequenceNum(type)) {
				result = true;
			}
		} else {
			if (NotableCache.getSpecialMap(type).contains(value)) {
				result = true;
			}
		}

		return result;
	}
	
	private boolean isSequence(byte type){
		Map<Byte,Integer> typeMap = NotableCache.getTypeMap(type);
		Integer sequence = typeMap.get(type);		
		return sequence == 1;
	}

}
