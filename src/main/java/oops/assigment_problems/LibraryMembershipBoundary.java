package oops.assigment_problems;

class BrokenLibraryMember {

    // Every field below is static, which is precisely the bug being reproduced:
    // name        - a name belongs to one member, but a static field is shared by the whole class,
    //               so creating the second member silently overwrites the first member's name.
    // memberId    - a membership id has to be unique per member; as a static field only one exists
    //               for all objects, so every member ends up reporting the same id.
    // booksIssued - the number of books issued is counted per member; making it static merges all
    //               members into one shared tally and no individual borrowing record survives.
    static String name;
    static String memberId;
    static int booksIssued;

    BrokenLibraryMember(String memberName, String id, int issued) {
        name = memberName;
        memberId = id;
        booksIssued = issued;
    }

    String getName() {
        return name;
    }
}

class FixedLibraryMember {

    private static final int ID_BASE = 1000;

    static String libraryName = "SRM Central Library";
    static int memberCount = 0;

    private String name;
    private String memberId;
    private int booksIssued;

    FixedLibraryMember(String name, int booksIssued) {
        memberCount++;
        this.name = name;
        this.memberId = "LM-" + (ID_BASE + memberCount);
        this.booksIssued = booksIssued;
    }

    int getBooksIssued() {
        return booksIssued;
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class LibraryMembershipBoundary {

    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenLibraryMember firstBroken = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember secondBroken = new BrokenLibraryMember("Rohan", "LM-1002", 5);
        System.out.println(firstBroken.getName());
        System.out.println(secondBroken.getName());

        System.out.println();
        System.out.println("Fixed version:");
        FixedLibraryMember aditi = new FixedLibraryMember("Aditi", 2);
        FixedLibraryMember rohan = new FixedLibraryMember("Rohan", 5);
        aditi.printMemberCard();
        rohan.printMemberCard();
        FixedLibraryMember.printTotalMembers();
        System.out.println("Aditi books issued: " + aditi.getBooksIssued()
                + " | Rohan books issued: " + rohan.getBooksIssued());
    }
}
