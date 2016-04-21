package byCodeGame.game.entity.file;

/**
 * 章节奖励
 * @author xjd
 *
 */
public class ChapterAward {
	/** 章节号			*/
	private int chapterId;
	/** 需求的星星数量		*/
	private int needStars;
	/** 阶段				*/
	private int process;
	/** 总共阶段			*/
	private int toltalProcess;
	/** 奖励				*/
	private String awardStr;
	/** 记录是否领取的编号	*/
	private int recordNo;
	
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getNeedStars() {
		return needStars;
	}
	public void setNeedStars(int needStars) {
		this.needStars = needStars;
	}
	public String getAwardStr() {
		return awardStr;
	}
	public void setAwardStr(String awardStr) {
		this.awardStr = awardStr;
	}
	public int getProcess() {
		return process;
	}
	public void setProcess(int process) {
		this.process = process;
	}
	public int getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}
	public int getToltalProcess() {
		return toltalProcess;
	}
	public void setToltalProcess(int toltalProcess) {
		this.toltalProcess = toltalProcess;
	}
}
