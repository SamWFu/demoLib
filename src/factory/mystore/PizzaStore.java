package factory.mystore;

import factory.Pizza;
import factory.SimplePizzaFactory;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName factory
 * @Date 16/7/11上午7:05
 * @Description 描述
 */
public class PizzaStore {

	SimplePizzaFactory factory;

	public Pizza orderPizza(String type) {

		Pizza pizza = factory.createPizza(type);

		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}

	public void setFactory(SimplePizzaFactory factory) {

		this.factory = factory;
	}
}
