package com.maloshpal.unification;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class TokenUtils {

    public static @Nullable Token parse(final @NotNull String tokenString) {
        // ([A-Za-z])(\([A-Za-z])*(\))*
        String inputCheck = "([A-Za-z])(\\([A-Za-z])*(\\))*";
        if (!tokenString.matches(inputCheck)) {
            return null;
        }

        Token result = null;
        String letters = tokenString.replace("(", "").replace(")", "");
        for (int i = letters.length() - 1; i >= 0; i--) {
            result = new Token(letters.substring(i, i + 1), result);
        }

        return result;
    }
}
