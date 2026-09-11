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
- Created `feature/session_4` from `develop` for Session 4. Session 4 topic: **constructors_and_keywords** — taken from the PDFs' own footer, "Constructors and Java Keywords": parameterized constructors as validation gates, `this(...)` chaining, `this` for field/parameter clashes, `final` at variable/method/class level, `static` initialiser blocks and `instanceof` dispatch.
- Solved all 5 live-session practice problems in `constructors_and_keywords/class_problems/`:
  - `BusTicketBookingValidator.java` — `BusTicket` with no usable no-arg constructor; invalid bookings fail at construction, duplicates are counted separately, and check-in is idempotent.
  - `RemainderFairFareSplitter.java` — three constructors linked by `this(...)`; the breakdown is computed in paise so the shares always sum back to the exact fare.
  - `BusRouteRankingEngine.java` — signed `compareTo` (priority, then code ignoring case, then name length) with a hand-written stable insertion sort.
  - `TieredBoardingPenaltyCalculator.java` — `final` class, `final` field and `final` method; closed-form bracket maths with a minimum floor that never applies at zero minutes late.
  - `NightlyFleetReconciliationEngine.java` — `static` block for class-level setup, constructor chaining, `instanceof` to settle sleeper accounts differently, and a batch that tolerates null entries.
- Solved all 5 take-home assignment problems in `constructors_and_keywords/assigment_problems/`:
  - `GhostOrderValidator.java` — blank, null and whitespace-only fields rejected at construction; a second `markDelivered()` warns instead of silently repeating.
  - `DeliverySlotBooking.java` — two constructors chained through `this(...)`, with "ASAP" written exactly once as a constant.
  - `CanteenTrustScoreRankingEngine.java` — trust-score ranking with the same deterministic tie-break chain and a hand-written stable sort.
  - `ExamWeekSurgeFeeCalculator.java` — tiered surge fee with a minimum floor, validated at the point of calculation.
  - `NightlyMultiKitchenReconciliationEngine.java` — `static` block, chained constructors, `instanceof` for premium accounts, null-safe batch processing.
- Compiled every file with `javac -Xlint:all` (zero warnings) and ran each program; output matches the sample input/output given in the problem PDFs.

**Next Session Plan:**
- Start Session 5 on a new `feature/session_5` branch created from `develop`.
- Revise interfaces and abstract classes, since these problems leaned on `instanceof` dispatch where polymorphism would normally be the cleaner answer.
- Practise more remainder/rounding problems, as the fare split was the easiest place this week to lose money to floating point.

**Issues Faced:**
- Problem 1 does not spell out what makes a name "meaningful", and the sample expects `Ravi123` to be rejected even though it is neither blank nor null. Settled on letters and spaces only, which is the only rule that reproduces the stated `Valid: 1 | Rejected: 3 | Duplicates skipped: 1`.
- The fare split loses paise if doubles are divided directly, so the whole calculation is done in integer paise and the leftover is handed to the last shares, which is what makes `100000 / 3` come out as `[33333.33, 33333.33, 33333.34]`.
- The ranking problems never state the default priority. Working backwards from the sample ordering showed it has to be 3, which the assignment PDF then confirms for the canteen version.
- For mismatched parallel array lengths the batch is rejected before anything is processed, rather than running over the shortest array, since a partly-settled batch would charge the wrong passenger.

---

## Date: 11-09-2026

**Today's Work:**
- Created `feature/session_3` from `develop` for Session 3. Session 3 topic: **oops** — both PDFs are the Week 3 OOP set, covering classes and objects, constructors, instance vs static members, inheritance, `instanceof` dispatch, object references and null safety, and composition.
- Solved all 5 live-session practice problems in `oops/class_problems/`:
  - `AttendanceSystem.java` — `SrmStudent` with a constructor, instance `isEligible()`, and static `classAverage()` over an array of students.
  - `FeeAccountExtension.java` — `HostelFeeAccount` and `ScholarshipFeeAccount` both extend `FeeAccount` without `FeeAccount` being edited; `instanceof` picks the right behaviour per account.
  - `HostelRoomAllotment.java` — `findAvailableRoom()` returns null when every room is full and `safeAllot()` checks for it, so no path can throw a NullPointerException.
  - `InstanceStaticBoundary.java` — reproduces the all-static bug where the second student overwrites the first, then the corrected instance/static split.
  - `FeeAndHostelMiniSystem.java` — capstone: one student object holding a fee account and a room as fields, with a static student counter.
- Solved all 5 take-home assignment problems in `oops/assigment_problems/`:
  - `LibraryFineSystem.java` — `BookIssue` with instance `fineAmount()` and static `totalFineCollected()`.
  - `EmployeeExtension.java` — `ManagerEmployee` and `InternEmployee` extend `Employee` without editing it.
  - `ParkingSlotAllotment.java` — null-safe parking allotment proving both the available and the full path.
  - `LibraryMembershipBoundary.java` — the all-static membership bug reproduced, then redesigned with `memberId` derived from a static `memberCount`.
  - `HrAndParkingMiniSystem.java` — capstone: `CompanyEmployeeRecord` holding an `Employee` and a `ParkingSlot` as fields, with a static record counter.
- Compiled every file with `javac -Xlint:all` (zero warnings) and ran each program; output matches the sample input/output given in the problem PDFs.

**Next Session Plan:**
- Start Session 4 on a new `feature/session_4` branch created from `develop`.
- Revise method overriding and abstract classes, since this week only used inheritance plus `instanceof` and never overrode a parent method.
- Read up on why `instanceof` chains are considered a design smell and how polymorphism replaces them.

**Issues Faced:**
- The capstone problems reuse classes defined by earlier problems in the same package. Keeping each class as a separate top-level class made `javac -Xlint:all` warn that an auxiliary class was being used from outside its own file, so the shared classes were made static nested classes of their problem's public class and imported by name. That removed all 36 warnings and keeps one `.java` file per problem.
- Three problems in each PDF ask for a written justification in a code comment, so those comments were added deliberately; the rest of the code has none.

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
