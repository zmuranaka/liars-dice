/*
 * File: LiarsDicePlayer.java
 * Zachary Muranaka
 * Represents an individual player, their dice, and the actions they can take in the game
 */

import java.util.Random;

public class LiarsDicePlayer
{
   private Random generator = new Random();
   private final int MAX_NUMBER_OF_DICE = 5;
   private int numberOfDice;
   private int id;
   int rolls[];

   // This constructor is for when the game starts
   public LiarsDicePlayer(int playerNumber)
   {
      numberOfDice = MAX_NUMBER_OF_DICE;
      rolls = new int[numberOfDice];
      id = playerNumber;
   }

   // This is for when a player loses a die
   public void newNumberOfDice(int numberOfDice)
   {
      this.numberOfDice = numberOfDice;
      rolls = new int[numberOfDice];
   }

   // Rolls all of the player's dice
   public void roll()
   {
      for(int i = 0; i < numberOfDice; i++)
         rolls[i] = generator.nextInt(6) + 1;
   }

   // The first bet is always going to be that there is a single 1
   public int[] firstBet()
   {
      int firstQuantity = 1;
      int firstFace = 1;
      int[] quantityAndFace = {firstQuantity, firstFace};
      return quantityAndFace;
   }

   // Allows the bots to bet
   public int[] computerBet(int totalDice, int quantity, int face)
   {
      int newQuantity = quantity;
      int newFace = face;
      int random = generator.nextInt(10) + 1;
      // 70% of the time the bot will try to raise the quantity of the die
      if(random > 0 && random < 8)
      {
         // Checks if the quantity of dice can be raised
         if(newQuantity < totalDice)
            newQuantity++;
         // The bot will raise the face of the die if they are forced to
         else
            newFace++;
      }
      // 30% of the time the bot will raise the face of the die and reset the quantity to 1
      else
      {
         // Checks if the face of the dice can be raised
         if(newFace < 6)
         {
            newFace++;
            newQuantity = 1;
         }
         // The bot will resort to raising the quantity of the dice if the face is 6
         else
            newQuantity++;
      }
      int[] quantityAndFace = {newQuantity, newFace};
      // Returns the bet for the bot
      return quantityAndFace;
   }

   // Returns who the current player is
   public String getCurrentPlayer()
   {
      if (id == 1)
         return "Player 1";
      else if (id == 2)
         return "Player 2";
      else if (id == 3)
         return "Player 3";
      else
         return "You";
   }

   // Returns your rolls in a String
   public String listOfRolls()
   {
      String yourRolls = "";
      for(int i = 0; i < rolls.length; i++)
      {
         yourRolls += rolls[i];
         if(i < rolls.length - 1)
            yourRolls += ", ";
      }
      return yourRolls;
   }

   // Accessor for roll i
   public int getRoll(int i){ return rolls[i]; }

   // Accessor for the numberOfDice
   public int getNumberOfDice(){ return numberOfDice; }

   // The quantity or the face must be greater than the previous bet for it to be a legal bet
   public boolean betIsLegal(int oldQuantity, int newQuantity, int oldFace, int newFace)
   {
      if(newQuantity > oldQuantity || newFace > oldFace)
         return true;
      return false;
   }

   // The bet is certainly a lie if the quantity is negative or greater than the total number of dice, or if the face is not legal on a die
   public boolean betIsLie(int totalDice, int quantity, int face)
   {
      if(quantity > totalDice || 0 > quantity || face > 6 || 1 > face)
         return true;
      return false;
   }

   // Determines if the bet is unlikely to happen within reasonable variance
   public boolean betIsUnlikely(int totalDice, int quantity, int face)
   {
      int expectedQuantity;
      // Ones are wild, counting toward whatever the current bet is, meaning two faces count toward the bet if the bet is anything but one
      if(face == 1)
         expectedQuantity = (int)(totalDice / 6);
      else
         expectedQuantity = (int)(totalDice / 3);
      int reasonableVariance = (int)(expectedQuantity + (0.2 * totalDice));
      // The bet is unlikely if the quantity is higher than reasonable variance
      if(quantity > reasonableVariance)
         return true;
      return false;
   }
}
