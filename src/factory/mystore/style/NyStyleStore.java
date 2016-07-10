package factory.mystore.style;

import factory.Pizza;
import factory.mystore.FactoryPizzaStore;
import factory.pizza.CheesePizza;
import factory.pizza.ClamPizza;
import factory.pizza.style.NyStyleCheesePizza;
import factory.pizza.style.NyStyleClamPizza;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName factory.mystore.style
 * @Date 16/7/11上午7:20
 * @Description 描述
 */
public class NyStyleStore extends FactoryPizzaStore {

	@Override
	public Pizza createPizza(String type) {

        switch (type) {
            case "cheese":
               return new NyStyleCheesePizza();

            case "clam":
                return new NyStyleClamPizza();

        }

        return null;
	}
}
