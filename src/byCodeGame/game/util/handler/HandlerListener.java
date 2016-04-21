package byCodeGame.game.util.handler;

import java.util.EventListener;

import byCodeGame.game.util.events.GoldChangEvent;

public interface HandlerListener extends EventListener{

	public void processEvent(GoldChangEvent event);
}
