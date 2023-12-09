package ca.arctechlabs.aoc.y2023.models;

public enum CamelCard {
    C_2('2', 2),
    C_3('3', 3),
    C_4('4', 4),
    C_5('5', 5),
    C_6('6', 6),
    C_7('7', 7),
    C_8('8', 8),
    C_9('9', 9),
    C_T('T', 10),
    C_J('J', 11),
    C_Q('Q', 12),
    C_K('K', 13),
    C_A('A', 14),
    C_JOKER('W', 1);


    private final char name;
    private final int strength;
    CamelCard(char name, int strength) {
        this.name = name;
        this.strength = strength;
    }

    public static CamelCard fromName(char name) {
        CamelCard[] var1 = values();
        int var2 = var1.length;

        for (CamelCard b : var1) {
            if (b.name == name) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected Camel Card name '" + name + "'");
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
