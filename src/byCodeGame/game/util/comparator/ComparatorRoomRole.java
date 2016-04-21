package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.RoomRole;

public class ComparatorRoomRole implements Comparator<Object> {

	private static ComparatorRoomRole _instance = null;

	public static ComparatorRoomRole getInstance() {
		if (_instance == null)
			_instance = new ComparatorRoomRole();
		return _instance;
	}

	private ComparatorRoomRole() {
	}

	public int compare(Object args1, Object args2) {
		RoomRole o1 = (RoomRole) args1;
		RoomRole o2 = (RoomRole) args2;
		int flag = o1.getWinTime() - o2.getWinTime();
		if (flag == 0) {
			return o1.getBattleTime() - o2.getBattleTime();
		} else {
			return flag;
		}
	}

}
