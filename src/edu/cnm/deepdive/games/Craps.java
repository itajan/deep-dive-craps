package edu.cnm.deepdive.games;

import java.util.Random;

public class Craps {

  public static final int DEFAULT_NUM_PLAYS = 100000;
  private static final String OUTCOME_MESSAGE = "Plays: %d; wins: %d; losses: %d; winning percentage: %.2f%%";

  public static void main(String[] args) {
    int plays = (args.length > 0) ? Integer.parseInt(args[0]) : DEFAULT_NUM_PLAYS;
    int wins = 0;
    int losses = 0;
    Random rng = new Random();
    for (int i = 0; i < plays; i++) {
      State state = State.COME_OUT;
      int point = 0;
      while (state != State.WIN && state != State.LOSE) {
        int die1 = 1 + rng.nextInt(6); // (6) shifting 0-5 to the right to be 1-6 because of the nature of a die
        int die2 = 1 + rng.nextInt(6);
        int total = die1 + die2;
        state = state.newState(total, point);
        if (state == State.POINT && point == 0) {
          point = total;
        }
      }
      if (state == State.WIN) {
        wins++;
      } else {
        losses++;
      }

    }
    System.out.printf(OUTCOME_MESSAGE, // "%.2f%% = placeholder for 2 decimal places and a percentage of it.
        plays, wins, losses, 100.0 * wins / plays); // double divided by a double


  }

  public enum State {


    // "{}" body of a class and I can override methods inside of them. look below
    // Anonymous class
    COME_OUT {
      @Override
      public State newState(int roll) {
        switch (roll) {
          case 7:
          case 11:
            return WIN;
          case 2:
          case 3:
          case 12:
            return LOSE;
          default:
            return POINT;
        }
      }
      @Override
      public State newState(int roll, int point) {
        return newState(roll);
      }
    },

    POINT {
      @Override // "Code" in pulldown menu above and then "Override" while carriage is in between curly braces.
      public State newState(int roll)
          throws IllegalArgumentException
      {
        if (roll != 7) {
          // DO something bad
          throw new IllegalArgumentException();
        }
        return LOSE;
      }

      @Override
      public State newState(int roll, int point) {
        if (roll == point) {
          return WIN;
        } else if (roll == 7) {
          return LOSE;
        }
        return POINT;

      }
    },

    WIN,
    LOSE; // add a semi-colon if I'm adding members to each enum, enums are also always static.

    public State newState(int roll) {
      return this;
    }
    // overloading the state method
    public State newState(int roll, int point) {
      return this;

    }
  }

}
