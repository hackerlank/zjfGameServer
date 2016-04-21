package byCodeGame.game.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类容器
 * 
 * @author ming 创建时间：2014-10-9
 */
public class SpringContext {
	// private static final String START_CONFIG_FILE = "ApplicationContext.xml";

	private static ClassPathXmlApplicationContext ctx;

	/**
	 * 加载spring配置
	 */
	public static void inizSpringCtx(String filePath) {
		ctx = new ClassPathXmlApplicationContext(filePath);
		ctx.registerShutdownHook();
	}

	/**
	 * 根据beanId获取bean
	 * 
	 * @param beanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		return (T) ctx.getBean(beanId);
	}

}
