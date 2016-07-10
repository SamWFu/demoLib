package factory.mystore.style;

import factory.Pizza;
import factory.mystore.FactoryPizzaStore;
import factory.pizza.style.ChicagoStyleCheesePizza;
import factory.pizza.style.ChicagoStyleClamPizza;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName factory.mystore.style
 * @Date 16/7/11上午7:24
 * @Description 描述
 */
public class ChicagoStyleStore extends FactoryPizzaStore {
    @Override
    public Pizza createPizza(String type) {

        switch (type) {
            case "cheese":
                return new ChicagoStyleCheesePizza();

            case "clam":
                return new ChicagoStyleClamPizza();

        }
		return null;
    }
}
