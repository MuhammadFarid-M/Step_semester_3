package constructors_and_keywords.class_problems;

public class BusRouteRankingEngine {

    static class BusRoute {

        private static final int DEFAULT_PRIORITY = 3;

        private final String routeCode;
        private final String routeName;
        private final int priority;

        public BusRoute(String routeCode, String routeName, int priority) {
            this.routeCode = routeCode;
            this.routeName = routeName;
            this.priority = priority;
        }

        public BusRoute(String routeCode, String routeName) {
            this(routeCode, routeName, DEFAULT_PRIORITY);
        }

        String getRouteCode() {
            return routeCode;
        }

        int compareTo(BusRoute other) {
            if (other == null) {
                return -1;
            }
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }
            int codeOrder = this.routeCode.compareToIgnoreCase(other.routeCode);
            if (codeOrder != 0) {
                return codeOrder;
            }
            return Integer.compare(this.routeName.length(), other.routeName.length());
        }
    }

    static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) {
            return new BusRoute[0];
        }
        BusRoute[] ranked = new BusRoute[routes.length];
        for (int index = 0; index < routes.length; index++) {
            ranked[index] = routes[index];
        }
        for (int index = 1; index < ranked.length; index++) {
            BusRoute current = ranked[index];
            int position = index - 1;
            while (position >= 0 && ranked[position].compareTo(current) > 0) {
                ranked[position + 1] = ranked[position];
                position--;
            }
            ranked[position + 1] = current;
        }
        return ranked;
    }

    private static void printRanking(BusRoute[] routes) {
        BusRoute[] ranked = rankRoutes(routes);
        StringBuilder line = new StringBuilder("[");
        for (int index = 0; index < ranked.length; index++) {
            line.append(index == 0 ? "" : ", ").append("\"").append(ranked[index].getRouteCode()).append("\"");
        }
        System.out.println(line.append("]").toString());
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };
        printRanking(routes);

        BusRoute[] tiedRoutes = {
            new BusRoute("RT400A", "Beach Line", 2),
            new BusRoute("rt400a", "Harbour", 2),
            new BusRoute("RT100B", "Ring Road", 5)
        };
        printRanking(tiedRoutes);
    }
}
