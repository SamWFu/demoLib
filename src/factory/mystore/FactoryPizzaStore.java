package factory.mystore;

import factory.Pizza;
import factory.SimplePizzaFactory;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName factory
 * @Date 16/7/11上午7:15
 * @Description 描述
 */
public abstract class FactoryPizzaStore {

	public Pizza orderPizza(String type) {

        Pizza pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

	public abstract Pizza createPizza(String type);

}
