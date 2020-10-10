package com.maloshpal.unification;

public class Main {

    public static void main(String[] args) {
        String E1 = "a, x, f(g(y))";
        String E2 = "z, f(z), f(u)";

        String[] elemsE1 = E1.split(",");
        String[] elemsE2 = E2.split(",");

        StringBuilder E1sigma = new StringBuilder();
        StringBuilder sigma = new StringBuilder();

        if (elemsE1.length != elemsE2.length) {
            System.out.println("false");
            return;
        }

        for (int i = 0; i < elemsE1.length; i++) {
            String currentE1 = elemsE1[i];
            String currentE2 = elemsE2[i];

            if (!currentE1.equals(elemsE2[i])) {
                if (currentE1.contains("(")) { // same as default
                    if (sigma.length() != 0) sigma.append(",");
                    sigma.append(currentE1).append("//").append(currentE2);
                    i = substitution(elemsE1, elemsE2, currentE1, currentE2);
                }
                else if (elemsE2[i].contains("(")) {
                    if (sigma.length() != 0) sigma.append(",");
                    sigma.append(currentE2).append("//").append(currentE1);
                    i = substitution(elemsE1, elemsE2, currentE2, currentE1);
                }
                else {
                    if (sigma.length() != 0) sigma.append(",");
                    sigma.append(currentE1).append("//").append(currentE2);
                    i = substitution(elemsE1, elemsE2, currentE1, currentE2);
                }
            }
        }

        boolean check = true;
        for (int i = 0; check && i < elemsE1.length; i++) {
            if (elemsE1[i].equals(elemsE2[i])) {
                if (E1sigma.length() != 0) E1sigma.append(",");
                E1sigma.append(elemsE1[i]);
            }
            else {
                check = false;
            }
        }
        if (!check) {
            System.out.println("false");
        }
        else {
            System.out.println("result: (" + E1sigma + ")");
            System.out.println("sigma: {" + sigma + "}");
        }
    }

    private static int substitution(String[] elemsE1, String[] elemsE2, String t, String v) {
        int k = -1;
        for (int j = 0; j < elemsE1.length; j++) {
            if (elemsE1[j].contains(v)) {
                elemsE1[j] = elemsE1[j].replace(v, t);
                if (k == -1) {
                    k = j;
                }
            }
            if (elemsE2[j].contains(v)) {
                elemsE2[j] = elemsE2[j].replace(v, t);
                if (k == -1) {
                    k = j;
                }
            }
        }
        return k;
    }
}
