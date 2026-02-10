package com.loremv.umines.data;

public record OreEntry(String name, int rolls) {
    public ConcreteOreEntry toConcrete(DynamicContentManager dynamicContentManager) {
        return dynamicContentManager.getConcreteEntry(this);
    }
}
