package arrays_and_strings.class_problems;

import java.util.Random;

public class RockPaperScissorsGame {

    private static final String ROCK = "Rock";
    private static final String PAPER = "Paper";
    private static final String SCISSORS = "Scissors";
    private static final String[] MOVES = { ROCK, PAPER, SCISSORS };

    private static final String PLAYER_WINS = "Player Wins";
    private static final String COMPUTER_WINS = "Computer Wins";
    private static final String DRAW = "Draw";
    private static final String INVALID_MOVE = "Invalid Move";

    private static final int ROUNDS_PER_MATCH = 5;
    private static final Random RANDOM = new Random();

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove == null || computerMove == null) {
            return INVALID_MOVE;
        }
        String player = normaliseMove(playerMove);
        String computer = normaliseMove(computerMove);
        if (!isValidMove(player) || !isValidMove(computer)) {
            return INVALID_MOVE;
        }
        if (player.equals(computer)) {
            return DRAW;
        }
        boolean playerBeatsComputer = (player.equals(ROCK) && computer.equals(SCISSORS))
                || (player.equals(PAPER) && computer.equals(ROCK))
                || (player.equals(SCISSORS) && computer.equals(PAPER));
        return playerBeatsComputer ? PLAYER_WINS : COMPUTER_WINS;
    }

    public static String generateComputerMove() {
        return MOVES[RANDOM.nextInt(MOVES.length)];
    }

    private static String normaliseMove(String move) {
        String trimmed = move.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }
        return Character.toUpperCase(trimmed.charAt(0)) + trimmed.substring(1).toLowerCase();
    }

    private static boolean isValidMove(String move) {
        for (String candidate : MOVES) {
            if (candidate.equals(move)) {
                return true;
            }
        }
        return false;
    }

    public static void playMatch(String[] playerMoves, String[] computerMoves) {
        if (playerMoves == null || computerMoves == null || playerMoves.length == 0
                || playerMoves.length != computerMoves.length) {
            System.out.println("Match cancelled: a move is needed from both the player and the computer for every round.");
            return;
        }

        String[] results = new String[playerMoves.length];
        int wins = 0;
        int losses = 0;
        int draws = 0;

        for (int round = 0; round < playerMoves.length; round++) {
            results[round] = playRound(playerMoves[round], computerMoves[round]);
            System.out.println("Round " + (round + 1) + " - Player: " + playerMoves[round]
                    + ", Computer: " + computerMoves[round]);
            System.out.println(results[round]);

            if (PLAYER_WINS.equals(results[round])) {
                wins++;
            } else if (COMPUTER_WINS.equals(results[round])) {
                losses++;
            } else if (DRAW.equals(results[round])) {
                draws++;
            }
        }

        System.out.println();
        System.out.printf("%-6s | %-11s | %-13s | %s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("-------------------------------------------------------");
        for (int round = 0; round < playerMoves.length; round++) {
            System.out.printf("%-6d | %-11s | %-13s | %s%n",
                    round + 1, playerMoves[round], computerMoves[round], results[round]);
        }

        double winPercentage = (wins * 100.0) / playerMoves.length;
        System.out.println();
        System.out.println("Final Summary (after " + playerMoves.length + " rounds)");
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                wins, losses, draws, winPercentage);
    }

    public static void main(String[] args) {
        System.out.println("=== Scripted Match (predefined moves, matches the sample in the problem statement) ===");
        String[] playerMoves = { ROCK, PAPER, SCISSORS, PAPER, ROCK };
        String[] computerMoves = { SCISSORS, PAPER, ROCK, ROCK, PAPER };
        playMatch(playerMoves, computerMoves);

        System.out.println();
        System.out.println("=== Random Match (computer move generated randomly each round) ===");
        String[] randomPlayerMoves = new String[ROUNDS_PER_MATCH];
        String[] randomComputerMoves = new String[ROUNDS_PER_MATCH];
        for (int round = 0; round < ROUNDS_PER_MATCH; round++) {
            randomPlayerMoves[round] = MOVES[RANDOM.nextInt(MOVES.length)];
            randomComputerMoves[round] = generateComputerMove();
        }
        playMatch(randomPlayerMoves, randomComputerMoves);
    }
}
