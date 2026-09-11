package constructors_and_keywords.assigment_problems;

public class CanteenTrustScoreRankingEngine {

    static class Canteen {

        private static final int DEFAULT_TRUST_SCORE = 3;

        private final String canteenCode;
        private final String canteenName;
        private final int trustScore;

        public Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, DEFAULT_TRUST_SCORE);
        }

        String getCanteenCode() {
            return canteenCode;
        }

        int compareTo(Canteen other) {
            if (other == null) {
                return -1;
            }
            if (this.trustScore != other.trustScore) {
                return Integer.compare(other.trustScore, this.trustScore);
            }
            int codeOrder = this.canteenCode.compareToIgnoreCase(other.canteenCode);
            if (codeOrder != 0) {
                return codeOrder;
            }
            return Integer.compare(this.canteenName.length(), other.canteenName.length());
        }
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) {
            return new Canteen[0];
        }
        Canteen[] ranked = new Canteen[canteens.length];
        for (int index = 0; index < canteens.length; index++) {
            ranked[index] = canteens[index];
        }
        for (int index = 1; index < ranked.length; index++) {
            Canteen current = ranked[index];
            int position = index - 1;
            while (position >= 0 && ranked[position].compareTo(current) > 0) {
                ranked[position + 1] = ranked[position];
                position--;
            }
            ranked[position + 1] = current;
        }
        return ranked;
    }

    private static void printRanking(Canteen[] canteens) {
        Canteen[] ranked = rankCanteens(canteens);
        StringBuilder line = new StringBuilder("[");
        for (int index = 0; index < ranked.length; index++) {
            line.append(index == 0 ? "" : ", ").append("\"").append(ranked[index].getCanteenCode()).append("\"");
        }
        System.out.println(line.append("]").toString());
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };
        printRanking(canteens);

        Canteen[] tiedCanteens = {
            new Canteen("HB9-C", "Rice Bowl", 4),
            new Canteen("hb9-c", "Tiffin", 4),
            new Canteen("HB0-C", "Night Canteen", 2)
        };
        printRanking(tiedCanteens);
    }
}
