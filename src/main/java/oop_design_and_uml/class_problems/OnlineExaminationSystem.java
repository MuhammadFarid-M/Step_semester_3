package oop_design_and_uml.class_problems;

public class OnlineExaminationSystem {

    abstract static class Question {

        private final int questionNumber;
        private final String questionText;
        private final String correctAnswer;

        public Question(int questionNumber, String questionText, String correctAnswer) {
            this.questionNumber = questionNumber;
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
        }

        public abstract boolean isAnswerCorrect(String givenAnswer);

        public int getQuestionNumber() {
            return questionNumber;
        }

        public String getQuestionText() {
            return questionText;
        }

        protected String getCorrectAnswer() {
            return correctAnswer;
        }
    }

    static class MultipleChoiceQuestion extends Question {

        private final String[] options;

        public MultipleChoiceQuestion(int questionNumber, String questionText,
                String[] options, String correctAnswer) {
            super(questionNumber, questionText, correctAnswer);
            this.options = new String[options.length];
            for (int index = 0; index < options.length; index++) {
                this.options[index] = options[index];
            }
        }

        public int getOptionCount() {
            return options.length;
        }

        @Override
        public boolean isAnswerCorrect(String givenAnswer) {
            return givenAnswer != null && givenAnswer.trim().equalsIgnoreCase(getCorrectAnswer());
        }
    }

    static class TrueFalseQuestion extends Question {

        public TrueFalseQuestion(int questionNumber, String questionText, boolean correctAnswer) {
            super(questionNumber, questionText, correctAnswer ? "TRUE" : "FALSE");
        }

        @Override
        public boolean isAnswerCorrect(String givenAnswer) {
            return givenAnswer != null && givenAnswer.trim().equalsIgnoreCase(getCorrectAnswer());
        }
    }

    static class Student {

        private final String studentId;
        private final String name;

        public Student(String studentId, String name) {
            this.studentId = studentId;
            this.name = name;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getName() {
            return name;
        }
    }

    static class Examination {

        private final String title;
        private final Question[] questions;

        public Examination(String title, Question[] questions) {
            this.title = title;
            this.questions = new Question[questions.length];
            for (int index = 0; index < questions.length; index++) {
                this.questions[index] = questions[index];
            }
        }

        public String getTitle() {
            return title;
        }

        public int getQuestionCount() {
            return questions.length;
        }

        public Question getQuestion(int questionNumber) {
            for (Question question : questions) {
                if (question.getQuestionNumber() == questionNumber) {
                    return question;
                }
            }
            return null;
        }

        public Attempt startAttempt(Student student) {
            System.out.println("Examination '" + title + "' started by " + student.getName() + ".");
            return new Attempt(student, this);
        }
    }

    enum AttemptStatus {
        IN_PROGRESS, SUBMITTED
    }

    static class Attempt {

        private final Student student;
        private final Examination examination;
        private final String[] answers;
        private AttemptStatus status;
        private int correctCount;

        Attempt(Student student, Examination examination) {
            this.student = student;
            this.examination = examination;
            this.answers = new String[examination.getQuestionCount() + 1];
            this.status = AttemptStatus.IN_PROGRESS;
            this.correctCount = 0;
        }

        public void answer(int questionNumber, String givenAnswer) {
            if (status == AttemptStatus.SUBMITTED) {
                System.out.println("Cannot change answers: the attempt for '"
                        + examination.getTitle() + "' is already submitted.");
                return;
            }
            if (examination.getQuestion(questionNumber) == null) {
                System.out.println("No question numbered " + questionNumber + " in this examination.");
                return;
            }
            answers[questionNumber] = givenAnswer;
            System.out.println("Question " + questionNumber + " answered with '" + givenAnswer + "'.");
        }

        public void submit() {
            if (status == AttemptStatus.SUBMITTED) {
                System.out.println("Examination '" + examination.getTitle() + "' was already submitted.");
                return;
            }
            status = AttemptStatus.SUBMITTED;
            correctCount = 0;
            for (int questionNumber = 1; questionNumber <= examination.getQuestionCount(); questionNumber++) {
                Question question = examination.getQuestion(questionNumber);
                if (question != null && question.isAnswerCorrect(answers[questionNumber])) {
                    correctCount++;
                }
            }
            System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
            System.out.println("Result for '" + examination.getTitle() + "' attempt: "
                    + correctCount + "/" + examination.getQuestionCount() + " correct.");
        }

        public AttemptStatus getStatus() {
            return status;
        }

        public String getSummary() {
            return student.getStudentId() + " - " + examination.getTitle()
                    + " - " + status + " - " + correctCount + " correct";
        }
    }

    public static void main(String[] args) {
        Question firstQuestion = new MultipleChoiceQuestion(1, "2 + 2 = ?",
                new String[] { "A. 4", "B. 5", "C. 6", "D. 7" }, "A");
        Question secondQuestion = new MultipleChoiceQuestion(2, "Square root of 81 = ?",
                new String[] { "A. 7", "B. 9", "C. 11", "D. 13" }, "B");

        Examination mathQuiz = new Examination("Math Quiz",
                new Question[] { firstQuestion, secondQuestion });
        Student student = new Student("S001", "Student");

        Attempt attempt = mathQuiz.startAttempt(student);
        attempt.answer(1, "A");
        attempt.answer(2, "C");
        attempt.submit();

        System.out.println();
        attempt.answer(1, "B");
        attempt.submit();

        System.out.println();
        System.out.println("Attempt state -> " + attempt.getSummary());

        System.out.println();
        Question trueFalse = new TrueFalseQuestion(1, "Java is object oriented.", true);
        Examination quickCheck = new Examination("Quick Check", new Question[] { trueFalse });
        Attempt secondAttempt = quickCheck.startAttempt(student);
        secondAttempt.answer(1, "true");
        secondAttempt.submit();
        System.out.println("A new question type plugged in without changing Attempt or Examination.");
    }
}
