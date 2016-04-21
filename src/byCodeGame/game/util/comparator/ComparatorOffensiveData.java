package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.OffensiveData;
import byCodeGame.game.entity.po.Rank;

/**
 * 征讨比较器
 * 
 * @author wcy 2016年1月18日
 *
 */
public class ComparatorOffensiveData implements Comparator<Rank> {

	public static ComparatorOffensiveData _instance = null;

	public static ComparatorOffensiveData getInstance() {
		if (_instance == null)
			_instance = new ComparatorOffensiveData();
		return _instance;
	}

	private ComparatorOffensiveData() {
	}

	@Override
	public int compare(Rank o1, Rank o2) {
		OffensiveData ofd1 = (OffensiveData) o1;
		OffensiveData ofd2 = (OffensiveData) o2;
		if (ofd1.getHurtValue() == ofd2.getHurtValue()) {
			return ofd1.getOffensiveTime() - ofd2.getOffensiveTime();
		}
		return ofd2.getHurtValue() - ofd1.getHurtValue();
	}
}
