package pattern.factory;

import pattern.factory.model.ingredient.PizzaIngredientFactory;
import pattern.factory.model.ingredient.cheese.Cheese;
import pattern.factory.model.ingredient.clams.Clams;
import pattern.factory.model.ingredient.dough.Dough;
import pattern.factory.model.ingredient.pepperoni.Pepperoni;
import pattern.factory.model.ingredient.sauce.Sauce;
import pattern.factory.model.ingredient.veggie.Veggies;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return null;
    }

    @Override
    public Sauce createSauce() {
        return null;
    }

    @Override
    public Cheese createCheese() {
        return null;
    }

    @Override
    public Veggies[] createVaggies() {
        return new Veggies[0];
    }

    @Override
    public Pepperoni createPepperoni() {
        return null;
    }

    @Override
    public Clams createClam() {
        return null;
    }
}
