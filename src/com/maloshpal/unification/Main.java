package com.maloshpal.unification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /**
     * @param args two strings of type "a, x, f(g(y))", "z, f(z), f(u)"
     */
    public static void main(String[] args) {
        List<Token> tokenSetE1 = Stream.of(args[0].split(", ")).map(TokenUtils::parse).collect(Collectors.toList());
        List<Token> tokenSetE2 = Stream.of(args[1].split(", ")).map(TokenUtils::parse).collect(Collectors.toList());
        List<Substitution> sigma = new ArrayList<>();

        // TODO consider adding more fail-safe
        if (tokenSetE1.isEmpty() || tokenSetE2.isEmpty()) {
            // TODO elaborate error message: Incorrect input
            System.out.println("false");
            return;
        }

        if (tokenSetE1.size() != tokenSetE2.size()) {
            // TODO elaborate error message: Es are of different sizes
            System.out.println("false");
            return;
        }

        for (int i = 0; i < tokenSetE1.size(); i++) {
            Token currentE1token = tokenSetE1.get(i);
            Token currentE2token = tokenSetE2.get(i);
            // either equal variables or equal functions:
            if (currentE1token.isEqualTo(currentE2token)) {
                // skip changes in this iteration
                continue;
            }

            // different functions:
            if (currentE1token.isFunction() && currentE2token.isFunction()) {
                // TODO elaborate error message: both tokens are functions
                System.out.println("false");
                return;
            }

            // one is a function and the other is a variable because underlying variable is the same
            if (currentE1token.getUnderlyingVariable().equals(currentE2token.getUnderlyingVariable())) {
                // TODO elaborate error message: one of the tokens is a function depending on the other token variable
                System.out.println("false");
                return;
            }

            if (isSubbed(currentE1token, sigma) || isSubbed(currentE2token, sigma)) {
                // TODO elaborate error message: the variable that needs substitution is already subbed in sigma
                System.out.println("false");
                return;
            }

            Substitution substitution;
            if (currentE1token.isFunction()) {
                substitution = new Substitution(currentE2token, currentE1token);
            }
            else if (currentE2token.isFunction()) {
                substitution = new Substitution(currentE1token, currentE2token);
            }
            else {
                substitution = new Substitution(currentE2token, currentE1token);
            }
            subst(tokenSetE1, substitution);
            subst(tokenSetE2, substitution);
            sigma.add(substitution);
        }

//        boolean check = true;
//        for (int i = 0; check && i < elemsE1.length; i++) {
//            if (elemsE1[i].equals(elemsE2[i])) {
//                E1sigma.append(elemsE1[i]);
//            }
//            else {
//                check = false;
//            }
//        }
//        if (!check) {
//            System.out.println("false");
//        }
//        else {
            System.out.println("result: (" + tokenSetE1 + ")");
            System.out.println("sigma: {" + sigma + "}");
//        }
    }

    private static boolean isSubbed(Token token, List<Substitution> sigma) {
        if (token.isFunction()) {
            return false;
        }

        for (Substitution substitution : sigma) {
            if (token.isEqualTo(substitution.getV())) {
                return true;
            }
        }

        return false;
    }

    private static void subst(List<Token> tokenList, Substitution substitution) {
        for (Token token : tokenList) {
            for (Token subToken = token; subToken != null; subToken = subToken.getChild()) {
                if (subToken.isEqualTo(substitution.getV())) {
                    subToken.substitute(substitution.getT());
                }
            }
        }
    }
}
