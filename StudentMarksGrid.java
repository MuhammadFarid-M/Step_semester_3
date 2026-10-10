package data_structures.class_problems;

/**
 * Problem 3: Student Marks Grid.
 *
 * Marks are held in a jagged 2D array - one row per student, each row only as
 * long as the subjects that student actually sat. A student who missed a
 * subject simply has a shorter row, so no sentinel value is needed and a
 * missing mark can never be mistaken for a real score of zero.
 */
public class StudentMarksGrid {

    private final String[] subjects;
    private final String[] students;
    private final int[][] marks;

    StudentMarksGrid(String[] subjects, String[] students, int[][] marks) {
        this.subjects = subjects;
        this.students = students;
        this.marks = marks;
    }

    int totalFor(int studentIndex) {
        int total = 0;
        for (int mark : marks[studentIndex]) {
            total += mark;
        }
        return total;
    }

    void printTotals() {
        StringBuilder line = new StringBuilder("Totals ");
        for (int i = 0; i < students.length; i++) {
            if (i > 0) {
                line.append(", ");
            }
            line.append(students[i]).append(' ').append(totalFor(i));
        }
        System.out.println(line);
    }

    void printToppers() {
        StringBuilder line = new StringBuilder("toppers ");
        for (int subject = 0; subject < subjects.length; subject++) {
            int bestMark = -1;
            String bestStudent = "-";

            for (int student = 0; student < students.length; student++) {
                // A short row means this student did not sit this subject,
                // so they are skipped rather than counted as zero.
                if (subject < marks[student].length && marks[student][subject] > bestMark) {
                    bestMark = marks[student][subject];
                    bestStudent = students[student];
                }
            }

            if (subject > 0) {
                line.append(", ");
            }
            line.append(subjects[subject]).append(' ')
                .append(bestStudent).append(' ').append(bestMark);
        }
        System.out.println(line);
    }

    public static void main(String[] args) {
        String[] subjects = {"Math", "Sci", "Eng"};
        String[] students = {"Anu", "Ravi", "Meena"};
        int[][] marks = {
            {80, 90, 70},
            {60, 85},          // Ravi has no English mark
            {95, 75, 88}
        };

        StudentMarksGrid grid = new StudentMarksGrid(subjects, students, marks);
        grid.printTotals();
        grid.printToppers();
    }
}
