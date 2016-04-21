package byCodeGame.game.moudle.arena;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArenaConstant {

	public static enum Action{
		/** 获取竞技场信息	 */
		DATA(22101),
		/** 获取竞技场目标		 */
		GET_TARGET(22102),
		/** 挑战对手		 */
		DARE(22103),
		/** 设置出场武将		 */
		SET_HERO(22104),
		
		TEST(22105),
		/** 获取预加载资源		 */
		LOAD(22106),
		/**获取天梯排名 */
		Ladder(22107),
		/**获取当前品阶排名 */
		NowLadder(22108);
		
		
		
		private short val;
		Action(int val){
			this.val = (short)val;
		}
		public short getVal(){
			return val;
		}
	}
	
	public static enum State{
		/** 未出场		 */
		OUT_BATTLE(0),
		/** 待机		 */
		WAIT(3),
		/** 移动		 */
		MOVE(1),
		/** 攻击		 */
		ATK(2),
		/** 死亡		 */
		DEAD(4),
		/** 技能		 */
		SKILL(5),
		/** BUFF		 */
		BUFF(6);
		
		private byte val;
		
		State(int val){
			this.val = (byte)val;
		}
		public byte getVal(){
			return val;
		}
	}
	
	
	public static Set<Byte> NO_ROBOT_SET =  new HashSet<Byte>(){
		private static final long serialVersionUID = 1L;

		{
			add((byte)1);
			add((byte)2);
			add((byte)3);
			add((byte)4);
			add((byte)5);
			add((byte)6);
		}
	};
	
	public static Map<Integer, Double> POS_X =new HashMap<Integer, Double>(){
		private static final long serialVersionUID = 1L;
		{
			put(1,(double)50);	//步兵
			put(2,(double)80);	//骑兵
			put(3,(double)-80);	//弓兵
			put(4,(double)-50);	//策士
		}
	};
	
	/** 挑战间隔时间	 */
	public static long DART_TIME = 5 * 600;
	/** 所有竞技场战斗单位的Y轴间隔	 */
	public static double POS_Y_SPACING = 32;
	
	/** 角色攻击增加的士气量	 */
	public static int MORALE_ATK = 2;
	/** 角色手上增加的士气量	 */
	public static int MORALE_HURT = 1;
	/** 满士气值 进入技能状态	 */
	public static int FULL_MORALE = 100;
}
