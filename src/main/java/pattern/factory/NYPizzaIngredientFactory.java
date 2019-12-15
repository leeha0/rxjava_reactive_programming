package pattern.factory;

import pattern.factory.model.ingredient.clams.Clams;
import pattern.factory.model.ingredient.clams.FreshClams;
import pattern.factory.model.ingredient.veggie.*;
import pattern.factory.model.ingredient.cheese.Cheese;
import pattern.factory.model.ingredient.cheese.ReggianoCheese;
import pattern.factory.model.ingredient.dough.Dough;
import pattern.factory.model.ingredient.dough.ThinCrustDough;
import pattern.factory.model.ingredient.PizzaIngredientFactory;
import pattern.factory.model.ingredient.pepperoni.Pepperoni;
import pattern.factory.model.ingredient.pepperoni.SlicedPepperoni;
import pattern.factory.model.ingredient.sauce.MarinaraSauce;
import pattern.factory.model.ingredient.sauce.Sauce;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThinCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    @Override
    public Veggies[] createVaggies() {
        Veggies vaggies[] = {
                new Garlic(),
                new Onion(),
                new Mushroom(),
                new RedPepper()
        };
        return vaggies;
    }

    @Override
    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    @Override
    public Clams createClam() {
        return new FreshClams();
    }
}
