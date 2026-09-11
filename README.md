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
- Created `feature/session_2` from `develop` for Session 2. Session 2 topic: **string** — every problem in both PDFs works directly on the String API (`charAt()`, `split()`, `substring()`, `lastIndexOf()`, `trim()`, `equalsIgnoreCase()`, `StringBuilder`, `Character.isLetter()`/`isDigit()`), so the topic package is named `string` exactly as the guide's own worked example shows.
- Solved all 5 live-session practice problems in `string/class_problems/`:
  - `VowelAndConsonantCounter.java` — case-insensitive vowel/consonant count with `charAt()`, spaces ignored.
  - `CsvStudentRecordParser.java` — `split(",")` with a 3-field check before printing the formatted record.
  - `FileExtensionValidator.java` — `lastIndexOf('.')` + `substring()` + `equalsIgnoreCase()` against pdf/docx/zip.
  - `MaskedPhoneNumberFormatter.java` — 10-digit validation, then `StringBuilder.insert()` to place the dash before the last 4 digits.
  - `BankTransactionReferenceValidator.java` — trim, uppercase only the 3-letter bank code, then a 14-character multi-stage validation with a specific reason for each failure.
- Solved all 5 take-home assignment problems in `string/assigment_problems/`:
  - `AtmPinLengthValidator.java` — `length()` with a single if/else and no loop, as the problem requires.
  - `WordReversalEncoder.java` — each word reversed with `StringBuilder` while the word order is kept.
  - `ProductInventoryCsvParser.java` — CSV split with field-count validation.
  - `LibraryIsbnNormalizer.java` — 13-character ISBN-style code normalised and validated with `Character.isLetter()`/`isDigit()` in a loop, no regex.
  - `StopWordFilteredWordFrequencyReport.java` — punctuation stripped with `replace()`, stop words skipped, word counts sorted by frequency descending.
- Compiled every file with `javac -Xlint:all` (zero warnings) and ran each program; output matches the sample input/output given in the problem PDFs.

**Next Session Plan:**
- Start Session 3 on a new `feature/session_3` branch created from `develop`.
- Practise more `StringBuilder` problems, especially `insert()` and `reverse()`, since both appeared this session.
- Revise how `split()` behaves with trailing empty fields and with a whitespace regex versus a single space.

**Issues Faced:**
- Two sample outputs contain an em dash (`Invalid PIN — must be exactly 4 digits.` and `Rejected — invalid file type`). Wrote it as the Unicode escape `\u2014` in the source so the file compiles to the same output on any machine regardless of the editor's file encoding.
- The word-frequency report has to list ties in first-appearance order to match the sample, so used a `LinkedHashMap` with a stable sort instead of a plain `HashMap`, whose iteration order is not predictable.

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
