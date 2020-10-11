package com.maloshpal.unification;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Token {

    // Fields

    private @NotNull String mName;
    private @Nullable Token mChild;

    // Construction

    public Token(final @NotNull Token token) {
        mName = token.getName();
        mChild = defensiveChild(token.getChild());
    }

    public Token(final @NotNull String name, final @Nullable Token child) {
        mName = name;
        mChild = defensiveChild(child);
    }

    public void substitute(final @Nullable Token substitutionToken) {
        mName = substitutionToken.getName();
        mChild = defensiveChild(substitutionToken.getChild());
    }

    public static @Nullable Token defensiveChild(final @Nullable Token child) {
        if (child == null) {
            return null;
        }

        return new Token(child);
    }

    // Getters

    public @NotNull String getName() {
        return mName;
    }

    public @Nullable Token getChild() {
        return mChild;
    }

    // Methods

    public @NotNull String toString() {
        if (getChild() == null) {
            return getName();
        }
        else {
            return getName() + "(" + getChild().toString() + ")";
        }
    }

    public boolean isEqualTo(final @NotNull Token otherToken) {
        return toString().equals(otherToken.toString());
    }

    public @NotNull String getUnderlyingVariable() {
        Token currentLevelToken = this;
        while (currentLevelToken.getChild() != null) {
            currentLevelToken = currentLevelToken.getChild();
        }
        return currentLevelToken.getName();
    }

    public boolean isFunction () {
        return getChild() != null;
    }
}
