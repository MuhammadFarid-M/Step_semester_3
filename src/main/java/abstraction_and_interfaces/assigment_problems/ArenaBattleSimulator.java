package abstraction_and_interfaces.assigment_problems;

public class ArenaBattleSimulator {

    interface Attackable {
        String attack();

        String attack(String weaponName);
    }

    interface Defendable {
        String defend();
    }

    abstract static class GameCharacter {

        private static final int CHARACTER_ID_BASE = 1000;
        private static int charactersCreated = 0;

        private final String characterId;

        public GameCharacter() {
            charactersCreated++;
            this.characterId = "CHR-" + (CHARACTER_ID_BASE + charactersCreated);
        }

        public abstract String getSpecialMove();

        public String getCharacterId() {
            return characterId;
        }
    }

    static class Warrior extends GameCharacter implements Attackable, Defendable {

        private final String name;

        public Warrior(String name) {
            this.name = name;
        }

        @Override
        public String attack() {
            return name + " strikes with a blade";
        }

        @Override
        public String attack(String weaponName) {
            return name + " strikes with " + articleFor(weaponName) + " " + weaponName;
        }

        @Override
        public String defend() {
            return name + " raises a shield";
        }

        @Override
        public String getSpecialMove() {
            return name + " unleashes Whirlwind Slash";
        }

        private static String articleFor(String word) {
            if (word == null || word.isEmpty()) {
                return "a";
            }
            return "AEIOUaeiou".indexOf(word.charAt(0)) >= 0 ? "an" : "a";
        }
    }

    static class Trap implements Defendable {

        private final String trapType;

        public Trap(String trapType) {
            this.trapType = trapType;
        }

        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }

    static void resolveDefense(Defendable[] combatants) {
        if (combatants == null) {
            return;
        }
        for (Defendable combatant : combatants) {
            if (combatant != null) {
                System.out.println(combatant.defend());
            }
        }
    }

    public static void main(String[] args) {
        Warrior warrior = new Warrior("Kael");
        System.out.println("w.attack()             -> " + warrior.attack());
        System.out.println("w.attack(\"Iron Sword\") -> " + warrior.attack("Iron Sword"));
        System.out.println("w.defend()             -> " + warrior.defend());
        System.out.println("w.getSpecialMove()     -> " + warrior.getSpecialMove());

        System.out.println();
        Trap trap = new Trap("Spike Pit");
        System.out.println("t.defend() -> " + trap.defend());

        System.out.println();
        System.out.println("resolveDefense over a mixed array:");
        resolveDefense(new Defendable[] { warrior, trap });

        System.out.println();
        System.out.println("Warrior " + warrior.getCharacterId()
                + " is a GameCharacter; Trap has no characterId and no special move,");
        System.out.println("because it only ever needed the one Defendable behaviour.");
    }
}
