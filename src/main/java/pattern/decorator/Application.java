package pattern.decorator;

import pattern.decorator.component.Beverage;
import pattern.decorator.component.DarkRoast;
import pattern.decorator.component.Espresso;
import pattern.decorator.component.HouseBlend;
import pattern.decorator.decorator.Mocha;
import pattern.decorator.decorator.Soy;
import pattern.decorator.decorator.Whip;

public class Application {

    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        printDescription(beverage);

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        printDescription(beverage2);

        Beverage beverage3 = new HouseBlend();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        printDescription(beverage3);
    }

    private static void printDescription(Beverage beverage) {
        System.out.println(beverage.getDescription() + " $" + beverage.cost());
    }
}
