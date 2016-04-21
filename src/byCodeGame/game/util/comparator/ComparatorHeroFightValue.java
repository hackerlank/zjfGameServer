package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.bo.Hero;

/**
 * 
 * @author wcy 2016年2月25日
 *
 */
public class ComparatorHeroFightValue implements Comparator<Hero> {
	private static ComparatorHeroFightValue _instance = null;

	public static ComparatorHeroFightValue getInstance() {
		if (_instance == null)
			_instance = new ComparatorHeroFightValue();
		return _instance;
	}

	private ComparatorHeroFightValue() {
		// TODO Auto-generated method st
	}

	@Override
	public int compare(Hero o1, Hero o2) {
		int f1 = o1.getFightValue();
		int f2 = o2.getFightValue();
		return f2 - f1;
	}

}
