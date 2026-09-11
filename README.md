# Step_semester_3

STEP Semester 3 coursework repository — daily progress log.

Branch model:

| Branch | Purpose | Content |
| --- | --- | --- |
| `main` | Documentation & daily progress log | Only `README.md` |
| `develop` | Base/empty project skeleton | Only the empty Java project structure |
| `feature/session_n` | Actual coding work per session | Topic package with solved problems |

---

## Date: 11-09-2026

**Today's Work:**
- Set up the repository branching structure: `main` (documentation only), `develop` (empty Java project skeleton), and `feature/session_1` created from `develop`.
- Session 1 topic: **arrays_and_strings** — chosen because every problem in both PDFs is built on array traversal, string traversal with `charAt()`/`split()`, and frequency/streak counting over those two structures.
- Solved all 5 live-session practice problems in `arrays_and_strings/class_problems/`:
  - `RockPaperScissorsGame.java` — random move generation, per-round result table, win/loss/draw summary with win percentage.
  - `PalindromeChecker.java` — same input verified by three independent approaches (iterative, recursive, array reversal).
  - `BmiCalculatorForTeam.java` — BMI computed for a 10-person team with classification and a formatted wellness report.
  - `FirstNonRepeatingCharacter.java` — character frequency counting, then a left-to-right scan for the first count of 1.
  - `ReverseCustomerName.java` — name reversal that leaves the original string untouched.
- Solved all 5 take-home assignment problems in `arrays_and_strings/assigment_problems/`:
  - `ExamHallSeatDuplicationChecker.java` — duplicate detection with nested loops only, no Collections class used.
  - `TypingSpeedTestAccuracyChecker.java` — positional character comparison, accuracy percentage, first mismatch position.
  - `TrafficSignalStreakAnalyzer.java` — longest run of consecutive identical signal readings.
  - `WarehouseInventoryBalancer.java` — section totals, balance check, and highest quantity with its section and item number.
  - `MovieReviewWordLengthProfiler.java` — word splitting and Short/Medium/Long length classification.
- Compiled every file with `javac -Xlint:all` (zero warnings) and ran each program; output matches the sample input/output given in the problem PDFs.

**Next Session Plan:**
- Start Session 2 on a new `feature/session_2` branch created from `develop`.
- Revise the recursion approach used in the palindrome problem and practise more recursive string problems.
- Read up on `StringBuilder` and how it differs from repeated `String` concatenation inside loops.

**Issues Faced:**
- The Rock-Paper-Scissors and BMI problems ask for random values, which makes the output different on every run. Solved it by running a fixed scripted dataset first (so the printed output can be checked against the sample in the PDF) and a random dataset after it, so both requirements are met.
- Needed to be careful with the duplicate-seat checker so that a seat number repeated more than twice is reported only once instead of on every matching pair.

---
