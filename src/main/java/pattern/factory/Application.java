package pattern.factory;

import pattern.factory.model.pizza.Pizza;

public class Application {
    public static void main(String[] args) {
        PizzaStore nyPizzaStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        Pizza nyCheesePizza = nyPizzaStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + nyCheesePizza.getName() + "\n");

        Pizza chicagoCheesePizza = chicagoStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + chicagoCheesePizza.getName() + "\n");
    }
}
