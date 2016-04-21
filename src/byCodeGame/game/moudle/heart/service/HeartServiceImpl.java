package byCodeGame.game.moudle.heart.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class HeartServiceImpl implements HeartService {

	public final short HEART = 21501;
	public static final short BUY_ARMYTOKEN = 21508;
	public static final short GET_AWARD_FIGHT = 21509;
	private static final int MAX_ARMYTOKEN = 50;
	private static final int NUMBER = 1;
	private static final int PRICE_ARMYTOKEN = 20;
	private static final long NULL_AWARD = 0;
	private static final Logger armyTokenLog = LoggerFactory.getLogger("armyToken");
	
	private HeroService heroService;
	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private MailService mailService;
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Override
	public Message heart(Role role) {
		Message message = new Message();
		message.setType(HEART);
		message.putString(String.valueOf(System.currentTimeMillis() / 1000));
		return message;
	}
	
	
	/**
	 * 判断玩家军令增加
	 * 每半小时服务器自动调用（调度器）
	 * @author xjd
	 */
	public void addArmyToken(Role role) {
		role.setLastGetArmyToken(System.currentTimeMillis()/1000);
		//判断玩家是否可以增加军令（50级以下）
		if(role.getArmyToken() >= MAX_ARMYTOKEN && role.getLv() <= (short)MAX_ARMYTOKEN)
		{
			return ; 
		}
		//判断是否可以获得军令（50级以上）
		if(role.getArmyToken() >= (int)role.getLv() && role.getLv() > (short)MAX_ARMYTOKEN)
		{
			return ;
		}
		//增加玩家的军令
		roleService.getArmyToken(role, NUMBER);
		role.setChange(true);
		//记录日志
		String armyTokenStr = Utils.getLogString(role.getId(),1,NUMBER);
		armyTokenLog.info(armyTokenStr);
	}
	
	/**
	 * 玩家上线时检查是否获得军令
	 * @param role
	 * @author xjd
	 */
	public void updateArmyToken(Role role)
	{
		long nowTime = System.currentTimeMillis()/1000;
		//判断玩家是否可以增加军令（50级以下）
		if(role.getArmyToken() >= MAX_ARMYTOKEN && role.getLv() <= (short)MAX_ARMYTOKEN)
		{
			return ; 
		}
		//判断是否可以获得军令（50级以上）
		if(role.getArmyToken() >= role.getLv() && role.getLv() > (short)MAX_ARMYTOKEN)
		{
			return ;
		}
		int temp = this.checkArmyToken(role);
		if(temp < NUMBER)
		{
			return;
		}
		role.setLastGetArmyToken(nowTime);
		//判断玩家能获得多少军令
		if(role.getLv() <= (short)MAX_ARMYTOKEN)
		{
			if(role.getArmyToken() + temp > MAX_ARMYTOKEN)
			{
				temp = MAX_ARMYTOKEN - role.getArmyToken();
			}
		}else {
			if(role.getArmyToken() + temp > (int)role.getLv())
			{
				temp = (int)role.getLv() - role.getArmyToken();
			}
		}
		roleService.getArmyToken(role, temp);
		//记录日志
		String armyTokenStr = Utils.getLogString(role.getId(),2,temp);
		armyTokenLog.info(armyTokenStr);
	}
	
	
	
	/**
	 * 计算玩家下线后应该获得的军令数量
	 * @param role
	 * @return
	 * @author xjd
	 */
	private int checkArmyToken(Role role)
	{
		long nowTime = System.currentTimeMillis()/1000 ;
		long oldTime = role.getLastGetArmyToken();
		if(oldTime == NULL_AWARD )
		{
			oldTime = role.getRegisterTime();
		}
		int getNumber = (int)((nowTime - oldTime) / 1800);
		
		return getNumber;
	}

	/**
	 * 玩家购买军令 
	 * @author xjd 
	 */
	public Message buyArmyToken(Role role ,int num) {
		Message message = new Message();
		message.setType(BUY_ARMYTOKEN);
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
		if(config == null)
		{
			return null;
		}
		//是否可以购买
		if(role.getAreadyBuyArmyToken() + num > config.getArmyTokenLimit())
		{
			message.putShort(ErrorCode.MAX_ERR_ARMYTOKEN_BUY);
			return message;
		}
		//判断军令数量是否超过限定
		//判断玩家是否可以增加军令（50级以下）
		if(role.getArmyToken() >= MAX_ARMYTOKEN && role.getLv() <= (short)MAX_ARMYTOKEN)
		{
			message.putShort(ErrorCode.MAX_ERR_ARMYTOKEN_BUY);
			return message; 
		}
		//判断是否可以获得军令（50级以上）
		if(role.getArmyToken() >= role.getLv() && role.getLv() > (short)MAX_ARMYTOKEN)
		{
			message.putShort(ErrorCode.MAX_ERR_ARMYTOKEN_BUY);
			return message; 
		}
		//是否有足够的元宝
		int cost = num * PRICE_ARMYTOKEN;
		if(role.getGold() < cost)
		{
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		roleService.addRoleGold(role, -cost);
		
		roleService.getArmyToken(role, num);
		role.setAreadyBuyArmyToken(role.getAreadyBuyArmyToken() + num);
		role.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
}
