package singleton;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName singleton
 * @Date 16/7/11上午7:39
 * @Description 描述
 */
public class Singleton {

	private static Singleton uniqueInstance;

    // 隐藏默认构造方法
	private Singleton() {
	}


	public static Singleton getInstance() {

		if (uniqueInstance == null)//若没有初始化则初始化
			uniqueInstance = new Singleton();

		return uniqueInstance;
	}
}
