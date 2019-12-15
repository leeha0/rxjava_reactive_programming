package pattern.factory;

import pattern.factory.model.pizza.Pizza;

public abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza;
        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    // 팩토리 역할을 하는 추상 클래스
    protected abstract Pizza createPizza(String type);
}
