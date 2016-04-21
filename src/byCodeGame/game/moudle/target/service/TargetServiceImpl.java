package byCodeGame.game.moudle.target.service;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;






import byCodeGame.game.cache.file.TargetConfigCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.bo.Target;
import byCodeGame.game.entity.file.TargetConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.TargetBean;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.target.TargetConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class TargetServiceImpl implements TargetService{
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	public Message getTargetInfo(Role role) {
		if(role == null) return null;
		Message message = new Message();
		message.setType(TargetConstant.GET_TARGET_INFO);
		Target target = role.getTarget();
		message.put((byte)target.getStage());
		if(target.getStage() == TargetConstant.NO_TARGET) return message;
		TargetConfig config = TargetConfigCache.getTargetConfigByNationAndStage(target.getStage(), role.getNation());		
		TargetBean bean = target.getAllTargetMap().get(config.getId());
		//条件
		message.putInt(bean.getNowStutsMap().size());
		for(Entry<Byte, Integer> x :bean.getNowStutsMap().entrySet())
		{
			message.putInt(x.getKey());
			message.putInt(x.getValue());
			//需求进度
			message.putInt(config.getTypeMap().get(x.getKey()));
		}
		//奖励
		message.putString(config.getAward());
		//是否完成
		message.put(bean.getIsComplete());
		return message;
	}
	
	public Message getAwardTarget(Role role, int stage) {
		Message message = new Message();
		message.setType(TargetConstant.GET_TARGET_AWARD);
		TargetConfig config = TargetConfigCache.getTargetConfigByNationAndStage(stage, role.getNation());
		TargetBean bean = role.getTarget().getAllTargetMap().get(config.getId());
		if(bean.getIsComplete() == TargetConstant.NO_COMP)
		{
			message.putShort(ErrorCode.CAN_NOT_GET_TARGET);
			return message;
		}
		if(role.getTarget().getStage() == TargetConstant.NO_COMP)
		{
			return null;
		}
		//处理奖励
		Set<ChapterReward> set = Utils.changStrToAward(config.getAward());
		this.chapterService.getAward(role, set);
		
		//更新目标
		if(config.getNextId() != TargetConstant.NO_TARGET)
		{
			role.getTarget().getAllTargetMap().remove(stage);
			TargetConfig newConf = TargetConfigCache.getTargetConfigById(config.getNextId());
			TargetBean newBean = new TargetBean(newConf);
			role.getTarget().getAllTargetMap().put(newBean.getId(), newBean);
			role.getTarget().setStage((byte)newBean.getId());
		}else {
			role.getTarget().setStage(TargetConstant.NO_COMP);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
		
	/**
	 * 领取首冲活动
	 */
	public Message getFirstRecharge(Role role) {
		Message message = new Message();
		message.setType(TargetConstant.GET_ACTIVE_AWARD);
		//判断是否可领取
		Target target = role.getTarget();
		if(target.getFirstRecharge() != TargetConstant.IS_COMP)
		{
			message.putShort(ErrorCode.CAN_NOT_GET_FIRST_R);
			return message;
		}
		//可以领取
		target.setFirstRecharge(TargetConstant.ALREADY_GET);
		target.setChange(true);
		//奖励发放
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	
	public void checkLvTarget(Role role) {
		Target target = role.getTarget();
		for(TargetBean temp :target.getAllTargetMap().values())
		{
			if(temp.getNowStutsMap().containsKey(TargetConstant.LV_TYPE))
			{
				temp.getNowStutsMap().put(TargetConstant.LV_TYPE,(int)role.getLv());
			}
		}
		
		//判断是否完成
		for(TargetBean temp :target.getAllTargetMap().values())
		{
			TargetConfig config = TargetConfigCache.getTargetConfigById(temp.getId());
			for(Entry<Byte, Integer> x :temp.getNowStutsMap().entrySet())
			{
				if(x.getValue() < config.getTypeMap().get(x.getKey()))
				{
					temp.setIsComplete(TargetConstant.NO_COMP);
					break;
				}else {
					temp.setIsComplete(TargetConstant.IS_COMP);
					continue;
				}
			}
			this.upTargetInfo(temp, role);
		}
	}

	public void upTargetInfo(TargetBean target, Role role) {
		IoSession is = SessionCache.getSessionById(role.getId());
		if(is == null) return;
		Message message = new Message();
		message.setType(TargetConstant.UP_TARGET_INFO);
		message.put((byte)target.getId());
		message.putInt(target.getNowStutsMap().size());
		TargetConfig config = TargetConfigCache.getTargetConfigById(target.getId());
		for(Entry<Byte, Integer> x:target.getNowStutsMap().entrySet())
		{
			message.putInt(x.getKey());
			message.putInt(x.getValue());
			message.putInt(config.getTypeMap().get(x.getKey()));
		}
		role.getTarget().setChange(true);
		is.write(message);
	}
	
	/***
	 * 获取活动信息
	 */
	public Message getActiveInfo(int type, Role role) {
		Message message = new Message();
		message.setType(TargetConstant.GET_ACTIVE_INFO);
		
		switch (type) {
		case TargetConstant.ACTIVE_FIRST_RECHARGE:
			message.putInt(role.getTarget().getFirstRecharge());
			break;
		case TargetConstant.ACTIVE_KEEP_LOGIN:
			Date date = new Date(ServerCache.getServer().getStartTime()*1000);
			break;
		default:
			break;
		}
		
		return message;
	}
	
	public Message getCheckInAward(Role role) {
		Message message = new Message();
		message.setType(TargetConstant.GET_ACTIVE_AWARD);
		//TODO 此处需要业务逻辑
		role.getLastOffLineTime();
		return message;
	}
}
