package byCodeGame.game.moudle.sign.service;

import java.util.Calendar;







import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;








import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.AwardSignCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Sign;
import byCodeGame.game.entity.file.AwardSign;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.sign.SignConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.Utils;

public class SignServiceImpl implements SignService{
	
	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	private HeroService heroService;
	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}
	private HeroDao heroDao;
	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}
	
	/**
	 * 获取玩家的签到信息
	 * @author xjd
	 */
	public Message getSignInfo(Role role) {
		Message message = new Message();
		message.setType(SignConstant.GET_SIGN_INFO);
		this.checkMonth(role);
		Sign sign = role.getSign();
		byte temp = SignConstant.NO_SIGN_TODAY;
		if(sign.getSignDaysSet().contains(Utils.getDay()))
		{
			temp = SignConstant.IS_SIGN_TODAY;
		}
		
		//返回客户端信息
		message.put((byte)sign.getSignMonth());
		message.put((byte)Utils.getMDays());
		message.put((byte)sign.getSignDaysSet().size());
		message.putString(sign.getSignDays());
		message.put((byte)sign.getSignRetroactiveSet().size());
		message.putString(sign.getSignRetroactive());
		Map<Integer, AwardSign> tempMap = AwardSignCache.getSignAwardMap();
		message.put((byte)tempMap.size());
		for(Entry<Integer, AwardSign> x : tempMap.entrySet())
		{
			message.putInt(x.getValue().getNumber());
			message.putInt(x.getValue().getNeedTimes());
			if(x.getValue().getAward() == null || x.getValue().getAward().equals(""))
			{
				message.putString("");
			}else {
				message.putString(x.getValue().getAward());
			}
			
			if(sign.getSignAwardSet().contains(x.getKey()))
			{
				message.put(SignConstant.IS_SIGN_TODAY);
			}else {
				message.put(SignConstant.NO_SIGN_TODAY);
			}
		}
		message.put(temp);
		message.putInt(SignConstant.SIGN_HERO_ID);
		message.putInt(SignConstant.SIGN_DAYS_NEED);
		if(role.getHeroMap().containsKey(SignConstant.SIGN_HERO_ID))
		{
			message.put(SignConstant.IS_SIGN_TODAY);
		}else {
			message.put(SignConstant.NO_SIGN_TODAY);
		}
		return message;
	}
	
	/**
	 * 签到
	 * @author xjd
	 * 
	 */
	public Message signToday(Role role) {
		Message message = new Message();
		message.setType(SignConstant.SIGN_TODAY);
		this.checkMonth(role);
		Sign sign = role.getSign();
		int tempDay = Utils.getDay();
		//判断玩家今日是否签到
		if(sign.getSignDaysSet().contains(tempDay))
		{
			message.putShort(ErrorCode.ERR_AREADY_SIGN);
			return message;
		}
		//将今天的日期加入set中
		sign.getSignDaysSet().add(tempDay);
		sign.setChang(true);
		//任务判断
		this.taskService.checkSignTask(role);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 补签
	 * @author xjd
	 */
	public Message signRetroactive(Role role, byte day) {
		Message message = new Message();
		message.setType(SignConstant.SIGN_RETROACTIVE);
		Sign sign = role.getSign();
		//判断传入天数是否合法
		if(day < SignConstant.MIN_SIGN_DAY || day > SignConstant.MAX_SIGN_DAY)
		{
			message.putShort(ErrorCode.ERR_DAY_SIGN);
			return message;
		}
		if(day > Utils.getDay())
		{
			return null;
		}
		//判断传入的日期是否已经签到
		if(sign.getSignDaysSet().contains((int)day))
		{
			message.putShort(ErrorCode.ERR_DAY_SIGN);
			return message;
		}
		//判断补签时间是否是今天
		if(day == Utils.getDay())
		{
			message.putShort(ErrorCode.ERR_TODAY_SIGN);
			return message;
		}
		//扣除玩家金币
		int tempCost = SignConstant.SIGN_RETROACTIVE_GOLD + 
				SignConstant.SIGN_RETROACTIVE_GOLD2 * sign.getSignRetroactiveSet().size();
		//判断玩家金币数量是否足够
		if(role.getGold() < tempCost)
		{
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		roleService.addRoleGold(role, -tempCost);
		//补签
		Integer temp = (int)day;
		sign.getSignDaysSet().add(temp);
		sign.getSignRetroactiveSet().add(temp);
		sign.setChang(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 领取签到奖励
	 * @author xjd
	 */
	public Message getSignAward(Role role, int number) {
		Message message = new Message();
		message.setType(SignConstant.GET_AWARD_SIGN);
		this.checkMonth(role);
		AwardSign awardSign = AwardSignCache.getAwardSignByNumber(number);
		
		if(awardSign == null)
		{
			return null;
		}
		Sign sign = role.getSign();
		//判断本次操作的箱子编号是否本月已经领取
		if(sign.getSignAwardSet().contains(number))
		{
			message.putShort(ErrorCode.AWARD_SIGN_AREADY_GET);
			return message;
		}
		//判断玩家的签到次数是否足够
		if(sign.getSignDaysSet().size() < awardSign.getNeedTimes())
		{
			message.putShort(ErrorCode.NO_SIGN_TIMES);
			return message;
		}
		//将本次领取的编号加入集合
		sign.getSignAwardSet().add(number);
		sign.setChang(true);
		
		
		Set<ChapterReward> set = Utils.changStrToAward(awardSign.getAward());
		chapterService.getAward(role, set);
		role.setChange(true);
		//记录日志
//		String logStr = Utils.getLogString(role.getId(),number,getMoney,getFood,getExploit,getArmyToken,getGod);
//		signLog.info(logStr);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 获取签到武将
	 * @author xjd
	 */
	public Message getSignHero(Role role ,IoSession is) {
		Message message = new Message();
		message.setType(SignConstant.GET_SIGN_HERO);
		//判断是否达到领取条件
		if(role.getSign().getSignDaysSet().size() < SignConstant.SIGN_DAYS_NEED)
		{
			message.putShort(ErrorCode.NO_SIGN_TIMES);
			return message;
		}
		//判断是否拥有此武将
		if(role.getHeroMap().containsKey(SignConstant.SIGN_HERO_ID))
		{
			message.putShort(ErrorCode.HERO_AREADY_HAS);
			return message;
		}
		//可以领取
		HeroConfig config = HeroConfigCache.getHeroConfigById(SignConstant.SIGN_HERO_ID);
		if(config == null) return null;		
		int heroId = SignConstant.SIGN_HERO_ID;
		Hero hero = heroService.createHero(role, heroId);
		// 通知玩家
		heroService.addHero(is, hero);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
//	/**
//	 * 签到礼包1 的计算方法<br/>
//	 * 银币 = 500 * 玩家等级<br/>
//	 * 粮草 = 500 * 玩家等级<br/>
//	 * @param role
//	 * @author xjd
//	 */
//	private void getAwardTypeA(Role role , int getMoney , int getFood)
//	{
//		roleService.addRoleMoney(role, getMoney);
//		roleService.addRoleFood(role, getFood);
//	}
//	
//	/**
//	 * 签到礼包2 的计算方法<br/>
//	 * 银币 = 1000 * 玩家等级<br/>
//	 * 粮草 = 1000 * 玩家等级<br/>
//	 * @param role
//	 * @author xjd
//	 */
//	private void getAwardTypeB(Role role , int getMoney , int getFood)
//	{
//		roleService.addRoleMoney(role, getMoney);
//		roleService.addRoleFood(role, getFood);
//	}
//	
//	/**
//	 * 签到礼包3 的计算方法<br/>
//	 * 银币 = 1500 * 玩家等级<br/>
//	 * 粮草 = 1500 * 玩家等级<br/>
//	 * 战功 = 500 * 玩家等级<br/>
//	 * @param role
//	 * @author xjd
//	 */
//	private void getAwardTypeC(Role role , int getMoney , int getFood ,int getExploit)
//	{
//		roleService.addRoleMoney(role, getMoney);
//		roleService.addRoleFood(role,  getFood);
//		roleService.addRoleExploit(role, getExploit);
//	}
//	
//	/**
//	 * 签到礼包4 的计算方法<br/>
//	 * 银币 = 3000 * 玩家等级<br/>
//	 * 粮草 = 3000 * 玩家等级<br/>
//	 * 战功 = 1500 * 玩家等级<br/>
//	 * @param role
//	 * @author xjd
//	 */
//	private void getAwardTypeD(Role role , int getMoney , int getFood ,int getExploit)
//	{
//		roleService.addRoleMoney(role, getMoney);
//		roleService.addRoleFood(role,  getFood);
//		roleService.addRoleExploit(role, getExploit);
//	}
//	
//	/**
//	 * 签到礼包5 的计算方法<br/>
//	 * 银币 = 5000 * 玩家等级<br/>
//	 * 粮草 = 5000 * 玩家等级<br/>
//	 * 战功 = 1500 * 玩家等级<br/>
//	 * 军令 = 2  <br/>
//	 * 元宝 = 50 <br/>
//	 * @param role
//	 * @author xjd
//	 */
//	private void getAwardTypeE(Role role , int getMoney , int getFood ,int getExploit , int getArmyToken ,int getGod)
//	{
//		roleService.addRoleMoney(role, getMoney);
//		roleService.addRoleFood(role,  getFood);
//		roleService.addRoleExploit(role, getExploit);
//		roleService.getArmyToken(role, getArmyToken);
//		roleService.addRoleGold(role, getGod);
//	}
//	
//	/**
//	 * 签到礼包6 的计算方法<br/>
//	 * 银币 = 7500 * 玩家等级<br/>
//	 * 粮草 = 7500 * 玩家等级<br/>
//	 * 战功 = 4500 * 玩家等级<br/>
//	 * 军令 = 5  <br/>
//	 * 元宝 = 100 <br/>
//	 * @param role
//	 * @author xjd
//	 */
//	private void getAwardTypeF(Role role , int getMoney , int getFood ,int getExploit , int getArmyToken ,int getGod)
//	{
//		roleService.addRoleMoney(role, getMoney);
//		roleService.addRoleFood(role, getFood);
//		roleService.addRoleExploit(role, getExploit);
//		roleService.getArmyToken(role, getArmyToken);
//		roleService.addRoleGold(role, getGod);
//	}
	
	/**
	 * 检查是否更新月份
	 * 每次更新月份时 重置以下数据：<br/>
	 * 玩家签到累计次数,
	 * 月份,
	 * 玩家领取的签到礼包信息
	 * 
	 * @param role
	 * @author xjd
	 */
	private void checkMonth(Role role)
	{
		Sign sign = role.getSign();
		Calendar calendar = Calendar.getInstance(); 
		int temp = calendar.get(Calendar.MONTH) + 1;
		if(sign.getSignMonth() == temp)
		{
			return;
		}else {
			sign.setSignMonth(temp);
			sign.getSignAwardSet().clear();
			sign.getSignDaysSet().clear();
			sign.getSignRetroactiveSet().clear();
			sign.setChang(true);
		}
		
	}


}
