package byCodeGame.game.moudle.babel;

public class BabelConstant {
	public static enum Action{
		/** 获取通天塔信息	 */
		GET_INFO(22501),
		/** 挑战通天塔 	 	 */
		FIGHT_BABEL(22502),
		/** 选择关卡		 */
		CHOICE_USERID(22503),
		/** 更换阵型		 */
		CHANGE_TROOP(22504),
		/** 获取当前武将信息 */
		GET_HERO_INFO(22505),
		/** 重置通天塔		 */
		RESET_TOWER(22506),
		/** 复活部队		 */
		REVIVE_HERO(22507);
		
		private short val;
		Action(int val){
			this.val = (short)val;
		}
		public short getVal(){
			return val;
		}
	}
	
	/** 用于通天塔重置进度						*/
	public static final int NO_PROCESS = 0;
	/** 用于表示当前状态：重置后					*/
	public static final byte NEW_STATUS = 0;
	/** 用于表示当前状态：已经选择剧本				*/
	public static final byte CHOICS_USERID = 1;
	/** 用于表示当前状态：已经选定武将(正常状态)		*/
	public static final byte CHOICS_HERO = 1;
}
