package byCodeGame.game.entity.file;

/**
 * 子资源建筑解锁条件
 * 
 * @author wcy
 *
 */
public class BuildingUnlock {
	private int type;
	private int unlockLoc;
	private int lvLimit;
	private int troopData;
	private int unLockHero;
	private int exp;

	public byte getType() {
		return (byte) type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte getUnlockLoc() {
		return (byte) unlockLoc;
	}

	public void setUnlockLoc(int unlockLoc) {
		this.unlockLoc = unlockLoc;
	}

	public int getLvLimit() {
		return lvLimit;
	}

	public void setLvLimit(int lvLimit) {
		this.lvLimit = lvLimit;
	}

	public int getTroopData() {
		return troopData;
	}

	public void setTroopData(int troopData) {
		this.troopData = troopData;
	}

	public byte getUnLockHero() {
		return (byte) unLockHero;
	}

	public void setUnLockHero(int unLockHero) {
		this.unLockHero = unLockHero;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

}
