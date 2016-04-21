package byCodeGame.game.entity.file;


/**
 * 任务详情
 * @author 王君辉
 *
 */
public class TaskInfo {

	/** 任务ID	 */
	private int taskId;
	
	/**
	 * 任务类型
	 * 1建筑相关  2招募相关  3征收相关  4关卡相关  5科技升级
	 */
	private int functionType;
	
	private int type1;
	
	private int type2;
	
	/** 完成条件数量	 */
	private int completeNum;
	/** 完成该任务后触发的下一任务id	 */
	private String nextTaskId;
	/** 出发的任务ID数组	 */
	private int[] nextTaskIdArr;
	/** 奖励字符串		<br/>	 
	 * 	1经验	2银币	3金币	<br/>
	 * 	4战功	5粮草	6人口	<br/>
	 * 	7军令	8声望 	9装备	<br/>
	 *  10道具	11武将 	12积分	<br/>
	 * （其中1-8,12 道具ID为0）
	 * */
	private String awardStr;
	/** 任务名称			*/
	private String title;
	/** 任务简介			*/
	private String content;
	/** 任务类型			*/
	private int taskType;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getFunctionType() {
		return functionType;
	}
	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}
	public int getType1() {
		return type1;
	}
	public void setType1(int type1) {
		this.type1 = type1;
	}
	public int getType2() {
		return type2;
	}
	public void setType2(int type2) {
		this.type2 = type2;
	}
	public int getCompleteNum() {
		return completeNum;
	}
	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}
	public String getNextTaskId() {
		return nextTaskId;
	}
	public void setNextTaskId(String nextTaskId) {
		if(nextTaskId !=null && !nextTaskId.equals("")){
			String[] strs = nextTaskId.split(",");
			this.nextTaskIdArr = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				nextTaskIdArr[i] = Integer.parseInt(strs[i]);
			}
		}
	}
	public int[] getNextTaskIdArr() {
		return nextTaskIdArr;
	}
	public String getAwardStr() {
		return awardStr;
	}
	public void setAwardStr(String awardStr) {
		this.awardStr = awardStr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
}
