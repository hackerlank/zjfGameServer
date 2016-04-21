package byCodeGame.game.moudle.mail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.MailDao;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.moudle.mail.MailConstant;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.TaskConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;

public class MailServiceImpl implements MailService{
	private MailDao mailDao;
	private RoleService roleService;
	private PropDao propDao;
	private PropService propService;
	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}
	public void setPropDao(PropDao propDao) {
		this.propDao = propDao;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public void setPropService(PropService propService) {
		this.propService = propService;
	}
	

	/**
	 * 玩家发送邮件
	 */
	public Message sendMail(Role role, String targetName, String title,
			String context, IoSession ioSession) {
		Message message =new Message();
		message.setType(MailConstant.SEND_MAIL_PLAYER);
		//过滤等级
//		if(role.getLv() < MailConstant.MIN_LV_SEND_MAIL)
//		{
//			message.putShort(ErrorCode.NO_LV);
//			return message;
//		}
		//判定目标用户是否存在
		Role targetRole = roleService.getRoleByName(targetName);
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		if(title.length() > MailConstant.MAX_TILE_LENGTH)
		{
			message.putShort(ErrorCode.MAIL_TITLE_TOO_LONG);
			return message;
		}
		if(context.length() > MailConstant.MAX_CONTEXT_LENGTH)
		{
			message.putShort(ErrorCode.MAIL_CONTEXT_TOO_LONG);
			return message;
		}
		Mail mail = new Mail();
		mail.setSendName(role.getName());
		mail.setTargetName(targetName);
		mail.setType(MailConstant.MAIL_TYPE_PLAYER);
		mail.setTitle(title);
		mail.setContext(context);
		mail.setChecked(MailConstant.MAIL_CHECKED_NOT);
		mail.setTime(System.currentTimeMillis()/1000);
		mail.setAttached("");
		
		//不能给自己发邮件
		if(targetName.equals(role.getName()))
		{
			message.putShort(ErrorCode.MAIL_ID_ERR);
			return message;
		}
		int id = mailDao.insertMail(mail);
		mail.setId(id);
		targetRole.getRoleMail().add(mail);
		IoSession targetSession = 
				SessionCache.getSessionById(targetRole.getId());
		if(targetSession!=null)
		{
			Message msg =new Message();
			msg.setType(MailConstant.NEW_MAIL_COME);
			msg.putInt(mail.getId());
			msg.putString(mail.getSendName());
			msg.putString(mail.getTargetName());
			msg.put(mail.getType());
			msg.putString(mail.getTitle());
			msg.putString(mail.getContext());
			msg.putString(mail.getAttached());
			msg.put(mail.getChecked());
			msg.putString(Long.toString(mail.getTime()));
			targetSession.write(msg);
			targetRole.setChange(true);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 获取当前玩家的所有邮件
	 */
	public Message getAllMail(Role role, IoSession ioSession) {
		Message message = new Message();
		message.setType(MailConstant.GET_ALL_MAIL);
		this.checkMailTime(role);
		List<Mail> list = role.getRoleMail();
		message.putInt(list.size());
		for(Mail mail : list)
		{
			message.putInt(mail.getId());
			message.putString(mail.getSendName());
			message.putString(mail.getTargetName());
			message.put(mail.getType());
			message.putString(mail.getTitle());
			message.putString(mail.getContext());
			if(mail.getAttached() == null || mail.getAttached().equals("")){
				message.putString("");
			}else{
				message.putString(mail.getAttached());
			}
			message.put(mail.getChecked());
			message.putString(Long.toString(mail.getTime()));
			if(mail.getType() == MailConstant.MAIL_TYPE_REWARD)
			{
				long temp = MailConstant.TIME_MAIL_SAVE - (System.currentTimeMillis()/1000 - mail.getTime());
				message.putString(Long.toString(temp));
			}else {
				message.putString(MailConstant.STR_NUM);
			}
			
		}
		return message;
	}

	/**
	 * 提取附件
	 */
	public Message takeItemMail(Role role, int id , IoSession ioSession) {
		Message message = new Message();
		message.setType(MailConstant.TAKE_ITEM_MAIL);
		//获取邮件
		Mail mail = null;
		for(int i = 0 ; i < role.getRoleMail().size() ; i++)
		{
			if(role.getRoleMail().get(i).getId() == id)
			{
				mail = role.getRoleMail().get(i);
				break;
			}
		}
		if(mail == null)
		{
			return null;
		}
		//获取附件
		String attachedStr = mail.getAttached();
		String[] strs = null;
		if(attachedStr != null && !attachedStr.equals("")){
			strs = attachedStr.split(";");
		}
		if(strs == null)
		{
			return null;
		}
		HashMap<Integer, Prop> map = this.getAttached(strs,role);
		//判断玩家背包数量是否足够
//		int temp = role.getMaxBagNum() - role.getBackPack().size();
//		if(temp < this.countNeedSize(role, map))
//		{
//			message.putShort(ErrorCode.BACKPACK_ERR);
//			return message;
//		}
		/*
		 * 遍历map
		 * 判断道具种类
		 * 装备 放入背包插入数据库
		 * 道具 判断是否持有该道具 有增加数量 没有放入背包并插入数据库
		 */
		for(Integer i : map.keySet())
		{
			int x = map.get(i).getItemId();
			if(map.get(i).getFunctionType() == MailConstant.TYPE_1)
			{
				EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(map.get(i).getItemId());
				Prop newEquip = new Prop();
				newEquip.setRoleId(role.getId());
				newEquip.setFunctionType(PropConstant.FUNCTION_TYPE_1); 
				newEquip.setItemId(equipConfig.getId());
				newEquip.setNum(PropConstant.NUM_1);
				newEquip.setLv(PropConstant.LV_1);
				newEquip.setSlotId((byte)equipConfig.getSlot());
				int newEquipId = propDao.insertProp(newEquip);
				newEquip.setId(newEquipId);
				//加入缓存
				role.getPropMap().put(newEquipId, newEquip);
//				role.getBackPack().put(newEquipId, newEquip);
				propService.addProp(newEquip, ioSession);
			}else if (map.get(i).getFunctionType() == MailConstant.TYPE_2) {
				//是否有相同itemId的道具
				Prop sameProp = null;
				for(Map.Entry<Integer, Prop> propEntry : role.getPropMap().entrySet()){
					Prop userProp = propEntry.getValue();
					if(userProp.getItemId() == map.get(i).getItemId()){
						sameProp  = userProp;
						break;
					}
				}
				if(sameProp == null){	//缓存中没有相同itemId的消耗品
					Prop newProp = new Prop();
					newProp.setRoleId(role.getId());
					newProp.setFunctionType(map.get(i).getFunctionType());
					newProp.setItemId(x);
					newProp.setNum((short)map.get(i).getNum());
					int newPropId = propDao.insertProp(newProp);
					newProp.setId(newPropId);
					//加入缓存
					role.getPropMap().put(newPropId, newProp);
					role.getConsumePropMap().put(newProp.getItemId(),newProp);
					propService.addProp(newProp, ioSession);
				}else{		
					sameProp.setNum((short)(sameProp.getNum() + map.get(i).getNum()));
					sameProp.setChange(true);
					propService.addProp(sameProp, ioSession);
				}
			}
		}
		//检查是否有任务
		this.taskService.checkMailTask(role, TaskConstant.TYPE_BYTE_1);
		/*
		 *删除邮件 
		 */
		//从缓存中删除邮件
		for(int i = 0 ; i < role.getRoleMail().size() ; i++)
		{
			if(role.getRoleMail().get(i).getId() == id)
			{
				role.getRoleMail().remove(i);
				break;
			}
		}
		//数据库删除邮件
		mailDao.removeMail(id);
		
		mail.setChange(true);
		role.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 删除邮件
	 */
	public Message DeleteMailById(Role role, byte type, int id,
			IoSession ioSession) {
		Message message = new Message();
		message.setType(MailConstant.DELETE_MAIL);
		
		//判断 type
		if(type == 1)
		{
			/*
			 * 单一删除
			 */
			Mail mail = null;
			for(int i = 0 ; i < role.getRoleMail().size() ; i++)
			{
				if(role.getRoleMail().get(i).getId() == id)
				{
					mail = role.getRoleMail().get(i);
					break;
				}
			}
			
			if(mail == null)
			{
				message.putShort(ErrorCode.MIAL_DELETE_ID_ERR);
				return message;
			}
			List<Integer> list2 = new ArrayList<Integer>();
			//附件不为空，则返回错误代码
			if(mail.getAttached() != null && !mail.getAttached().equals(""))
			{
				message.putShort(ErrorCode.ITEM_MAIL_ERR);
				return message;
			}
		
			mailDao.removeMail(id);
			list2.add(id);
			//从缓存中删除邮件
			for(int i = 0 ; i < role.getRoleMail().size() ; i++)
			{
				if(role.getRoleMail().get(i).getId() == id)
				{
					role.getRoleMail().remove(i);
					break;
				}
			}
			message.putShort(ErrorCode.SUCCESS);
			message.putInt(list2.size());
			for(Integer i : list2)
			{
				message.putInt(i);
			}
			
		} else if (type == 2) {
			/*
			 * 全部删除
			 */
			List<Mail> list = role.getRoleMail();
			//存放删除成功的邮件id
			List<Integer> list2 = new ArrayList<Integer>();
			message.putShort(ErrorCode.SUCCESS);
			//迭代删除集合中所有可删除邮件
			for(Iterator<Mail> iter = list.iterator(); iter.hasNext();) 
			{
				Mail mail = (Mail)iter.next();
				if(mail == null)
				{
					continue;
				}
				if(mail.getAttached() == null || mail.getAttached().equals("")) 
				{
					list2.add(mail.getId());
					iter.remove();
					mailDao.removeMail(mail.getId());
				}
			}
			message.putInt(list2.size());
			for(Integer i : list2)
			{
				message.putInt(i);
			}
		}
		
		role.setChange(true);
		return message;
	}

	/**
	 * 设置邮件是否已读
	 */
	public Message SetMailChecked(Role role,int id, IoSession ioSession) {
		Message message =new Message();
		message.setType(MailConstant.MAIL_CHECK_TYPE);
		
		List<Mail> list=role.getRoleMail();
		if(list == null)
		{
			return null;
		}
		for(Mail mail : list)
		{
			if(mail.getId() == id){
				mail.setChecked(MailConstant.MAIL_CHECKED_ALREADY);
				mail.setChange(true);
				break;
			}
		}
		role.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 获取附件中的邮件
	 * @return
	 * @author xjd
	 */
	private HashMap<Integer, Prop> getAttached(String[] strs ,Role role)
	{
		HashMap<Integer, Prop> map = new HashMap<Integer, Prop>();
		
		for (int i = 0; i < strs.length; i++) {
			
			String[] itemStr = strs[i].split(",");
			if(Byte.parseByte(itemStr[0]) == MailConstant.TYPE_1)
			{
				EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(Integer.parseInt(itemStr[1]));
				Prop prop = new Prop();
				prop.setItemId(equipConfig.getId());
				prop.setFunctionType(MailConstant.TYPE_1);
				prop.setNum(Short.parseShort(itemStr[2]));
				map.put(prop.getItemId(), prop);
			}else if (Byte.parseByte(itemStr[0]) == MailConstant.TYPE_2) {
				Prop prop = new Prop();
				Item item = ItemCache.getItemById(Integer.parseInt(itemStr[1]));
				prop.setItemId(item.getId());
				prop.setFunctionType(MailConstant.TYPE_2);
				prop.setNum(Short.parseShort(itemStr[2]));
				map.put(prop.getItemId(), prop);
			}else if (Byte.parseByte(itemStr[0]) == MailConstant.TYPE_3) {
				roleService.addRoleGold(role, Integer.parseInt(itemStr[2]));
			}else if (Byte.parseByte(itemStr[0]) == MailConstant.TYPE_4) {
				roleService.addRoleMoney(role, Integer.parseInt(itemStr[2]));
			}else if (Byte.parseByte(itemStr[0]) == MailConstant.TYPE_5) {
				roleService.addRoleFood(role, Integer.parseInt(itemStr[2]));
			}
		}
		return map;
	}

	/**
	 * 服务器主动推送邮件(805)
	 * 传入需要发送的目标Name
	 * 传入Title
	 * 传入Context
	 * 传入附件
	 * 其他已经设置
	 * @param targetRole
	 * @param mail
	 * @author xjd
	 */
	public void sendSYSMail(Role targetRole , Mail mail)
	{
		mail.setTargetName(targetRole.getName());
		if(mail.getTime() == MailConstant.CHECK_TIME)
		{
			mail.setTime(System.currentTimeMillis()/1000);
		}
		mail.setSendName(MailConstant.SEND_NAME);
		mail.setType(MailConstant.MAIL_TYPE_REWARD);
		int id = mailDao.insertMail(mail);
		mail.setId(id);
		IoSession targetSession = 
				SessionCache.getSessionById(targetRole.getId());
		if(targetSession!=null)
		{
			targetRole.getRoleMail().add(mail);
			Message msg =new Message();
			msg.setType(MailConstant.NEW_MAIL_COME);
			msg.putInt(mail.getId());
			msg.putString(mail.getSendName());
			msg.putString(mail.getTargetName());
			msg.put(mail.getType());
			msg.putString(mail.getTitle());
			msg.putString(mail.getContext());
			msg.putString(mail.getAttached());
			msg.put(mail.getChecked());
			msg.putString(Long.toString(mail.getTime()));
			targetSession.write(msg);
			targetRole.setChange(true);
		}
	}
	
	/**
	 *	无奖励系统邮件（805）
	 */
	public void sendSYSMail2(Role targetRole, Mail mail) {
		mail.setTargetName(targetRole.getName());
		mail.setTime(System.currentTimeMillis()/1000);
		mail.setSendName(MailConstant.SEND_NAME);
		mail.setType(MailConstant.MAIL_TYPE_SYSTEM);
		int id = mailDao.insertMail(mail);
		mail.setId(id);
		IoSession targetSession = 
				SessionCache.getSessionById(targetRole.getId());
		if(targetSession!=null)
		{
			targetRole.getRoleMail().add(mail);
			Message msg =new Message();
			msg.setType(MailConstant.NEW_MAIL_COME);
			msg.putInt(mail.getId());
			msg.putString(mail.getSendName());
			msg.putString(mail.getTargetName());
			msg.put(mail.getType());
			msg.putString(mail.getTitle());
			msg.putString(mail.getContext());
			msg.putString("");
			msg.put(mail.getChecked());
			msg.putString(Long.toString(mail.getTime()));
			targetSession.write(msg);
			targetRole.setChange(true);
		}
	}
	
	private int countNeedSize(Role role , Map<Integer, Prop> map)
	{
		int need = 0;
		for(Prop x : map.values())
		{
			if(x.getFunctionType() == MailConstant.TYPE_1)
			{
				need++;
			}else if (x.getFunctionType() == MailConstant.TYPE_2) {
				if(!propService.checkItemInBackPack(role, x.getItemId()))
				{
					need++;
				}
			}
		}
		return need;
	}
	
	/**
	 * 检查过期邮件
	 * @param role
	 */
	private void checkMailTime(Role role)
	{
		List<Mail> list = role.getRoleMail();
		for(Iterator<Mail> iter = list.iterator(); iter.hasNext();) 
		{
			Mail mail = (Mail)iter.next();
			if(mail == null)
			{
				continue;
			}
			if((System.currentTimeMillis()/1000) - mail.getTime()
										> MailConstant.TIME_MAIL_SAVE) 
			{
				iter.remove();
				mailDao.removeMail(mail.getId());
			}
		}
	}
}
