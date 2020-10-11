package com.maloshpal.unification;

@Deprecated
public class Variable implements IToken {

    private final String mName;

    public Variable(String name) {
        mName = name;
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public String getName() {
        return mName;
    }
}
