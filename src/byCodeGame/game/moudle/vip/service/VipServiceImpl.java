package byCodeGame.game.moudle.vip.service;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.KeyDao;
import byCodeGame.game.entity.Key;
import byCodeGame.game.entity.bo.Recharge;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.moudle.vip.VipConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class VipServiceImpl implements VipService {
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	private ChatService chatService;
	public void setChatService(ChatService chatService){
		this.chatService = chatService;
	}
	
	private KeyDao keyDao;
	public void setKeyDao(KeyDao keyDao) {
		this.keyDao = keyDao;
	}
	
	public Message recharge(String account,int num ,int serverId ,String partnerId ,String orderNo){
		Message message = new Message();
		message.setType(VipConstant.RECHARGE);
		Role role = roleService.getRoleByAccount(account);
		if(role == null)
		{
			return null;
		}
		//判断是否首冲
		if(role.getSumGold() == VipConstant.NUM_0
				&& role.getTarget().getFirstRecharge() == VipConstant.NO_FIRST_RECHARGE)
		{
			//更改状态
			role.getTarget().setFirstRecharge(VipConstant.CAN_GET_FIRST_R);
			role.getTarget().setChange(true);
		}
		int goldNum = num * 10;
		roleService.addRoleGold(role, goldNum);
		role.setSumGold(role.getSumGold() + goldNum);
		role.setChange(true);
		IoSession is = SessionCache.getSessionById(role.getId());
		this.refreshVipLv(role ,is);
		this.taskService.checkVip(role);
		Recharge recharge = new Recharge();
		recharge.setAccount(role.getAccount());
		recharge.setNum(num);
		recharge.setRoleId(role.getId());
		recharge.setTime(Utils.getDataStr());
		recharge.setServerId(serverId);
		recharge.setPartnerId(Integer.parseInt(partnerId));
		recharge.setOrderNo(orderNo);
		
		this.keyDao.insterRecharge(recharge);
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(goldNum);
		return message;
	}
	
	/**
	 * 获取玩家VIP信息
	 * @author xjd
	 */
	public Message getVipInfo(Role role) {
		Message message = new Message();
		message.setType(VipConstant.GET_VIP_INFO);
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
		if(config == null)
		{
			return null;
		}
		message.put(role.getVipLv());
		message.putInt(role.getSumGold() - config.getBaseExp());
		message.putInt(config.getNextExp()-config.getBaseExp());
		
		return message;
	}
	
	/***
	 * 获取玩家Vip礼包信息
	 * @author xjd
	 */
	public Message getVipRawardInfo(Role role) {
		Message message = new Message();
		message.setType(VipConstant.GET_VIP_AWARD_INFO);
		Map<Integer, VipConfig> map = VipConfigCache.getVipConfigMap();
		message.put((byte)(map.size()));
		for(VipConfig x : map.values())
		{
//			if(x.getVipLv() == VipConstant.NUM_0)
//			{
//				continue;
//			}
			message.putInt(x.getVipLv());
			//判断礼包状态 1已领取 2未领取 3不可领取
			if(role.getVipLv() >= (byte)x.getVipLv() && 
					role.getAlreadyGetVipAwardSet().contains(x.getVipLv()))
			{
				message.put(VipConstant.VIP_AWARD_GET);
			}else if (role.getVipLv() >= (byte)x.getVipLv() && 
					!role.getAlreadyGetVipAwardSet().contains(x.getVipLv())) {
				message.put(VipConstant.VIP_AWARD_CAN_GET);
			}else if (role.getVipLv() < (byte)x.getVipLv()) {
				message.put(VipConstant.VIP_AWARD_CAN_NOT_GET);
			}
			message.put((byte)VipConfigCache.getAwardMap().get(x.getVipLv()).size());
			for(ChapterReward z : VipConfigCache.getAwardMap().get(x.getVipLv()))
			{
				message.put(z.getType());
				message.putInt(z.getItemId());
				message.putInt(z.getNum());
			}
			
		}
		
		
		return message;
	}
	
	/**
	 * 领取Vip礼包
	 * @author xjd
	 */
	public Message getVipRaward(Role role , int id) {
		Message message = new Message();
		message.setType(VipConstant.GET_VIP_AWARD);
		//过滤0级
//		if(id == VipConstant.NUM_0)
//		{
//			return null;
//		}
		//判断vip等级
		if(role.getVipLv() < (byte)id)
		{
			message.putShort(ErrorCode.NO_VIP_LV);
			return message;
		}
		Set<ChapterReward> set = VipConfigCache.getAwardMap().get(id);
		if(set == null)
		{
			return null;
		}
		//判断是否领取过该礼包
		if(role.getAlreadyGetVipAwardSet().contains(id))
		{
			message.putShort(ErrorCode.ALREADY_GET_AWARD);
			return message;
		}
		chapterService.getAward(role, set);
		role.getAlreadyGetVipAwardSet().add(id);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 刷新玩家VIP等级
	 * @param role
	 */
	private void refreshVipLv(Role role , IoSession is){
		byte temp = role.getVipLv();
		for (byte i = role.getVipLv(); i < VipConfigCache.getVipConfigMap().size(); i++) {
			VipConfig tempVipConfig = VipConfigCache.getVipConfigByVipLv((int)i);
			if(role.getSumGold() >= tempVipConfig.getBaseExp() && role.getSumGold() < tempVipConfig.getNextExp()){
				role.setVipLv((byte)tempVipConfig.getVipLv());
				break;
			}
		}
		VipConfig maxVipConfig = VipConfigCache.getVipConfigByVipLv(12);
		if(role.getSumGold() >= maxVipConfig.getNextExp()){
			role.setVipLv((byte)maxVipConfig.getVipLv());
		}
		
		if(role.getVipLv() > temp)
		{
			this.vipLvUp(role, is);
			if (chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_VIP, role.getVipLv())) {
				StringBuilder sb = this.getVIPStringBuilder(role);
				chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_VIP, sb.toString());
			}
		}
	}

	private StringBuilder getVIPStringBuilder(Role role) {
		StringBuilder sb = new StringBuilder();
		sb.append(role.getId()).append(",");
		sb.append(role.getName()).append(",");
		sb.append(role.getVipLv()).append(",");
		sb.append(Utils.getNationName(role.getNation())).append(",");
		return sb;
	}
	
	public void initAward() {
		Map<Integer, VipConfig> map = VipConfigCache.getVipConfigMap();
		for(Entry<Integer, VipConfig> x : map.entrySet())
		{
			Set<ChapterReward> temp = Utils.changStrToAward(x.getValue().getAward());
			VipConfigCache.getAwardMap().put(x.getKey(), temp);
		}
	}
	
	/**
	 * vip等级提升(服务器主动推送)
	 * @param role
	 * @param is
	 * @author xjd
	 */
	private void vipLvUp(Role role , IoSession is)
	{
		Message message = new Message();
		message.setType(VipConstant.VIP_LV_UP);
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
		if(config == null)
		{
			return;
		}
		message.put(role.getVipLv());
		message.putInt(role.getSumGold() - config.getBaseExp());
		message.putInt(config.getNextExp());
		if(is != null) is.write(message);
	}
}
