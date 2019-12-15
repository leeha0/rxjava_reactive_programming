package pattern.decorator.decorator;

import pattern.decorator.component.Beverage;

public class Whip extends CondimentDecorator {

    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 휘핑 크림";
    }

    @Override
    public double cost() {
        return .10 + beverage.cost();
    }
}
