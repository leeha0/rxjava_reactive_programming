package pattern.factory.model.ingredient;

import pattern.factory.model.ingredient.cheese.Cheese;
import pattern.factory.model.ingredient.clams.Clams;
import pattern.factory.model.ingredient.dough.Dough;
import pattern.factory.model.ingredient.pepperoni.Pepperoni;
import pattern.factory.model.ingredient.sauce.Sauce;
import pattern.factory.model.ingredient.veggie.Veggies;

public interface PizzaIngredientFactory {
    public Dough createDough();
    public Sauce createSauce();
    public Cheese createCheese();
    public Veggies[] createVaggies();
    public Pepperoni createPepperoni();
    public Clams createClam();
}
