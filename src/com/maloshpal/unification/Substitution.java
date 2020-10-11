package com.maloshpal.unification;

import com.sun.istack.internal.NotNull;
import javafx.util.Pair;

public class Substitution {

    // Fields

    private final @NotNull Pair<Token, Token> mFromTo;

    // Construction

    public Substitution(final @NotNull Token from, final @NotNull Token to) {
        mFromTo = new Pair<>(new Token(from), new Token(to));
    }

    // Getters

    public @NotNull Token getV() {
        return mFromTo.getKey();
    }

    public @NotNull Token getT() {
        return mFromTo.getValue();
    }

    // Methods

    public @NotNull String toString() {
        return mFromTo.getValue().toString() + "//" + mFromTo.getKey().toString();
    }
}
