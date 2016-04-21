package byCodeGame.game.util.handler;

import java.util.ArrayList;
import java.util.List;

import byCodeGame.game.util.events.GoldChangEvent;

/**
 * 监听器（金币变化）
 * @author xjd
 *
 */
public class GoldChangHandler {
	private List<HandlerListener> listeners = new ArrayList<HandlerListener>();
	
	public GoldChangHandler() {
	}

	public void addDemoListener(HandlerListener listener) {
		listeners.add(listener);
	}

	public void notifyDemoEvent(int value ,int roleId) {
		for (HandlerListener eventListener : listeners) {
		
			GoldChangEvent evt = new GoldChangEvent(this, value , roleId);
			eventListener.processEvent(evt);
		}
	}
}
