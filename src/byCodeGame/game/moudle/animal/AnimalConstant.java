package byCodeGame.game.moudle.animal;

public class AnimalConstant {

	public static enum Action{
		/** 获取神兽信息		 */
		INFO(22001),
		/** 喂养神兽		 */
		EAT(22002);
		
		private short val;
		Action(int val){
			this.val = (short)val;
		}
		public short getVal(){
			return val;
		}
	}
	
	
}
