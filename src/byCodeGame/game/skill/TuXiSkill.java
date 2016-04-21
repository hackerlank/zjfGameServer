package byCodeGame.game.skill;

import byCodeGame.game.cache.file.SkillConfigCache;
import byCodeGame.game.entity.file.SkillConfig;
import byCodeGame.game.moudle.arena.msg.BuffArenaFightMsg;
import byCodeGame.game.skill.buff.AddMoveSpeedBuff;
import byCodeGame.game.skill.buff.BaseBuff;
import byCodeGame.game.skill.buff.PosionBuff;

/**
 * 突袭
 * @author Lenovo
 *
 */
public class TuXiSkill extends BaseSkill {

	private boolean used;
	
	public TuXiSkill(){
		setUseType(1);
//		super(arenaFightData, objData);
	}
	
	@Override
	public boolean checkCondition() {
		//进场时直接使用
		if(used = false){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void getTarget() {
		
	}

	@Override
	public void use() {
		if(used == false){
			SkillConfig skillConfig = SkillConfigCache.getSkillConfig(this.skillId);
//			arenaFightData.setPause(true);
//			arenaFightData.setStartPauseTime(arenaFightData.getNowTime());
//			arenaFightData.setPauseTime((long)skillConfig.getCastTime());
			used = true;
			BaseBuff buff = new PosionBuff(skillConfig.getBuffId(),
					arenaFightData,objData,objData);
			getObjData().getBuffSet().add(buff);
			
			
		}
	}
	
	public void checkNowEffect(){
		
	}
}
