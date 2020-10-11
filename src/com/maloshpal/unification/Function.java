package com.maloshpal.unification;

@Deprecated
public class Function implements IToken {

    private final String mName;

    public Function(String name) {
        mName = name;
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public String getName() {
        return mName;
    }
}
