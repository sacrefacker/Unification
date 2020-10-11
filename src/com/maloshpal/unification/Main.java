package com.maloshpal.unification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /**
     * @param args two strings representing sets of tokens (no spaces), for example "a,x,f(g(a))", "z,f(z),y"
     */
    public static void main(String[] args) {
        List<Token> tokenSetE1 = Stream.of(args[0].split(",")).map(TokenUtils::parse).collect(Collectors.toList());
        List<Token> tokenSetE2 = Stream.of(args[1].split(",")).map(TokenUtils::parse).collect(Collectors.toList());
        List<Substitution> sigma = new ArrayList<>();

        if (tokenSetE1.isEmpty() || tokenSetE2.isEmpty() || tokenSetE1.contains(null) || tokenSetE2.contains(null)) {
            System.out.println("incorrect input");
            System.out.println("false");
            return;
        }

        if (tokenSetE1.size() != tokenSetE2.size()) {
            System.out.println("E sets are of different sizes");
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
                System.out.println("both tokens are functions");
                System.out.println("false");
                return;
            }

            // one is a function and the other is a variable because underlying variable is the same
            if (currentE1token.getUnderlyingVariable().equals(currentE2token.getUnderlyingVariable())) {
                System.out.println("one of the tokens is a function depending on the other token variable");
                System.out.println("false");
                return;
            }

            if (isSubbed(currentE1token, sigma) || isSubbed(currentE2token, sigma)) {
                System.out.println("the variable that needs substitution is already subbed in sigma");
                System.out.println("false");
                return;
            }

            // subbing the variable to the function or if both are variables then from E2 to E1
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

        System.out.println("result: " + tokenSetE1.toString());
        System.out.println("sigma: " + sigma.toString());
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
