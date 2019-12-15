package pattern.factory.model.pizza;

import pattern.factory.model.ingredient.cheese.Cheese;
import pattern.factory.model.ingredient.clams.Clams;
import pattern.factory.model.ingredient.dough.Dough;
import pattern.factory.model.ingredient.pepperoni.Pepperoni;
import pattern.factory.model.ingredient.sauce.Sauce;
import pattern.factory.model.ingredient.veggie.Veggies;

import java.util.Arrays;

public abstract class Pizza {
    private String name;
    Dough dough;
    Sauce sauce;
    Veggies vaggies[];
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clams;

    // 피자 클래스는 어떠한 재료를 사용하는지 신경 쓰지 않음
    // 추상 팩토리 패턴에 팩토리 메소드 패턴 결합
    abstract public void prepare();

    public void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    public void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    public void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", dough=" + dough +
                ", sauce=" + sauce +
                ", vaggies=" + Arrays.toString(vaggies) +
                ", cheese=" + cheese +
                ", pepperoni=" + pepperoni +
                ", clams=" + clams +
                '}';
    }
}
