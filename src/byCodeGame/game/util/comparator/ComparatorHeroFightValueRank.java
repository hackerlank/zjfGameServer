package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.HeroFightValueRank;
import byCodeGame.game.entity.po.Rank;

/**
 * 
 * @author wcy 2016年2月25日
 *
 */
public class ComparatorHeroFightValueRank implements Comparator<Rank> {
	private static ComparatorHeroFightValueRank _instance = null;

	public static ComparatorHeroFightValueRank getInstance() {
		if (_instance == null)
			_instance = new ComparatorHeroFightValueRank();
		return _instance;
	}

	private ComparatorHeroFightValueRank() {
	}

	@Override
	public int compare(Rank o1, Rank o2) {
		HeroFightValueRank r1 = (HeroFightValueRank) o1;
		HeroFightValueRank r2 = (HeroFightValueRank) o2;
		if (r1.getFightValue() != r2.getFightValue())
			return r2.getFightValue() - r1.getFightValue();

		if (r1.getEnterTime() != r2.getEnterTime())
			return r1.getEnterTime() - r2.getEnterTime();

		String name1 = r1.getName();
		String name2 = r2.getName();

		return name1.compareTo(name2);
	}
}
