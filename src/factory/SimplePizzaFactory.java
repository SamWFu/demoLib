package factory;

import factory.pizza.CheesePizza;
import factory.pizza.ClamPizza;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName factory
 * @Date 16/7/11上午7:09
 * @Description 描述
 */
public class SimplePizzaFactory {

	public Pizza createPizza(String type) {

		Pizza pizza = null;

		switch (type) {
		case "cheese":
			pizza = new CheesePizza();
			break;
		case "clam":
			pizza = new ClamPizza();
			break;
		}
		return pizza;
	}
}
