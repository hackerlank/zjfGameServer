package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.OwnCityRank;
import byCodeGame.game.entity.po.Rank;

/**
 * 
 * @author wcy 2016年2月25日
 *
 */
public class ComparatorOwnCityRank implements Comparator<Rank> {

	private static ComparatorOwnCityRank _instance = null;

	public static ComparatorOwnCityRank getInstance() {
		if (_instance == null)
			_instance = new ComparatorOwnCityRank();
		return _instance;
	}

	private ComparatorOwnCityRank() {

	}

	@Override
	public int compare(Rank o1, Rank o2) {
		OwnCityRank r1 = (OwnCityRank) o1;
		OwnCityRank r2 = (OwnCityRank) o2;
		if (r1.getOwnCityCount() != r2.getOwnCityCount())
			return r2.getOwnCityCount() - r1.getOwnCityCount();

		int time1 = r1.getEnterTime();
		int time2 = r2.getEnterTime();
		if (time1 != time2)
			return time1 - time2;
		return r1.getName().compareTo(r2.getName());
	}
}
