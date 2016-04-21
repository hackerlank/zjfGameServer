package byCodeGame.game.entity.file;

import java.util.HashSet;
import java.util.Set;

/**
 * 兵营等级解锁兵种
 * @author 王君辉
 *
 */
public class BarrackUnLock {

	private int lv;
	/** 类型 	1步兵 2骑兵 3弓兵 4策士 */
	private int functionType;
	/** 这个等级解锁的兵种	(兵种ID,兵种ID,兵种ID,....) */
	private String openArmsId;
	
	private Set<Integer> openArmsIdSet = new HashSet<Integer>();

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getFunctionType() {
		return functionType;
	}

	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}

	public String getOpenArmsId() {
		return openArmsId;
	}

	public void setOpenArmsId(String openArmsId) {
		if(openArmsId != null && !openArmsId.equals("")){
			String[] strs = openArmsId.split(",");
			for(String str : strs){
				this.getOpenArmsIdSet().add(Integer.valueOf(str));
			}
		}
	}

	public Set<Integer> getOpenArmsIdSet() {
		return openArmsIdSet;
	}

	public void setOpenArmsIdSet(Set<Integer> openArmsIdSet) {
		this.openArmsIdSet = openArmsIdSet;
	}
}
