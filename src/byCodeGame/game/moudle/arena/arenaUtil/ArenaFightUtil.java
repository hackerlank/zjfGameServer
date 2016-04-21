package byCodeGame.game.moudle.arena.arenaUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.util.Utils;

/**
 * 竞技场战斗工具方法
 * @author Lenovo
 *
 */
public class ArenaFightUtil {

	/**
	 * 获取战场上的目标</br>
	 * 在攻击范围中并且离自己最近的目标
	 * @param arenaFightData
	 * @param objData
	 */
	public static ArenaObjFightData checkTarget(ArenaFightData arenaFightData,
			ArenaObjFightData objData){
		
		Map<String, ArenaObjFightData> targetMap = arenaFightData.getAllObjDataMap();
		ArenaObjFightData target = null;
		for(Map.Entry<String, ArenaObjFightData> entry : targetMap.entrySet()){
			ArenaObjFightData tempObj = entry.getValue();
			if(tempObj.getCamp() != objData.getCamp() && tempObj.getState() 
					!= ArenaConstant.State.DEAD.getVal()){
				
				double distance = getDistanc(objData, tempObj);
				if(distance <= (double)objData.getAtkDistance()){//对方目标进入范围
					//判断离自己最近的目标
					if(target == null){
						target = tempObj;
					}else{			
						ArenaObjFightData newTarget = tempObj;
						double oldDistance = getDistanc(objData, target);
						double newDistance = getDistanc(objData, newTarget);
						if(newDistance < oldDistance){
							target = newTarget;
						}
					}
				}
			}
		}
		
		return target;
	}
	
	/**
	 * 计算攻击伤害
	 * @param objData
	 * @param targetData
	 * @return
	 */
	public static int countHurt(ArenaObjFightData objData,ArenaObjFightData targetData){
		int hurt = objData.getAtk() - targetData.getDef();
//		hurt = Utils.getRandomNum(40, 50);
		if(hurt <= 0 )
			hurt = 1;
		
		return hurt;
	}
	
	/**
	 * 获取目标之间的x轴的距离
	 * @param objData
	 * @param targetData
	 * @return
	 */
	public static double getDistanc(ArenaObjFightData objData,ArenaObjFightData targetData){
		double distance = 0;
		distance = objData.getPosX() - targetData.getPosX();
		distance = Math.abs(distance);
		return distance;
	}
	
	/**
	 * 计算移动速度
	 * @param objData
	 * @return
	 */
	public static double countMoveSpeed(ArenaObjFightData objData){
		return objData.getMoveSpeed();
	}
}
