package byCodeGame.game.moudle.arena.fsm.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.skill.BaseSkill;

public class Test {

	public static Map<String, Control> controlMap = new HashMap<String, Control>();
	
	
	public static void main(String[] args) {
		Class<?> aa = null;
		BaseSkill skill = null;
//		System.out.println(123);
		try {
			aa = Class.forName("byCodeGame.game.skill.TuXiSkill");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			skill = (BaseSkill)aa.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		skill.use();
	}
	
}
