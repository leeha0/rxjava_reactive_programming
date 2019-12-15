package pattern.decorator.decorator;

import pattern.decorator.component.Beverage;

public abstract class CondimentDecorator extends Beverage {
    public abstract String getDescription();
}
