/*
 * File: LiarsDiceGUI.java
 * Zachary Muranaka
 * Allows the user to play Liar's Dice in a GUI against three computer-controlled opponents
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class LiarsDiceGUI extends JPanel
{
   // Window components
   private JLabel lblPic = new JLabel(new ImageIcon(getClass().getResource("images/dice.jpg")));
   private JFrame window = new JFrame("Liar's Dice");
   private JLabel lblFace = new JLabel("Face:");
   private JLabel lblQuantity = new JLabel("Quantity:");
   private JLabel currentQuantity = new JLabel("");
   private JLabel currentFace = new JLabel("");
   private JLabel info = new JLabel("");
   private JLabel diceLeft = new JLabel("");
   private JLabel yourRolls = new JLabel("");
   private JTextField txtFace = new JTextField();
   private JTextField txtQuantity = new JTextField();
   private JButton btnBet = new JButton("Bet");
   private JButton btnChallenge = new JButton("Challenge");
   private JButton btnContinue = new JButton("Continue");
   private JButton btnStart = new JButton("Start Game");
   
   LiarsDicePlayer player1 = new LiarsDicePlayer(1);
   LiarsDicePlayer player2 = new LiarsDicePlayer(2);
   LiarsDicePlayer player3 = new LiarsDicePlayer(3);
   LiarsDicePlayer human = new LiarsDicePlayer(4);
   private int playerId; // Tracks who the current player is
   private int quantity; // The number of dice from the current bet
   private int face; // The face of the dice of the current bet (1 to 6)
   private int oldQuantity; // The number of dice from the previous bet
   private int oldFace; // The face of the dice from the previous bet (1 to 6)
   private int totalDice = 20; // Each player starts with 5 dice and there are 4 players
   private boolean betIsChallenged = false;
   private boolean betIsCertainlyALie = false;
   private boolean roundIsOver = false;
   private boolean gameIsOver = false;
   private boolean betWasLie = false;
   private Random generator = new Random(); // Allows us to generate random numbers

   // Constructor for the LiarsDiceGUI class
   public LiarsDiceGUI()
   {
      setLayout(null); // No layout is set
      
      // Setup window components by x, y, width, height
      lblPic.setBounds(20, 20, 200, 200);
      diceLeft.setBounds(20, 220, 200, 30);
      diceLeft.setFont(new Font("Serif", Font.PLAIN, 24));
      lblQuantity.setBounds(20, 300, 125, 40);
      lblQuantity.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
      lblFace.setBounds(20, 360, 125, 40);
      lblFace.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
      currentQuantity.setBounds(240, 60, 350, 40);
      currentQuantity.setFont(new Font("Serif", Font.PLAIN, 28));
      currentFace.setBounds(240, 110, 350, 40);
      currentFace.setFont(new Font("Serif", Font.PLAIN, 28));
      info.setBounds(240, 160, 350, 80);
      info.setFont(new Font("Serif", Font.PLAIN, 28));
      yourRolls.setBounds(240, 0, 350, 50);
      yourRolls.setFont(new Font("Serif", Font.PLAIN, 28));
      txtQuantity.setBounds(155, 300, 150, 40);
      txtQuantity.setFont(new Font("Serif", Font.PLAIN, 28));
      txtFace.setBounds(155, 360, 150, 40);
      txtFace.setFont(new Font("Serif", Font.PLAIN, 28));
      btnBet.setBounds(350, 270, 200, 40);
      btnBet.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
      btnChallenge.setBounds(350, 330, 200, 40);
      btnChallenge.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
      btnContinue.setBounds(350, 390, 200, 40);
      btnContinue.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
      btnStart.setBounds(400, 0, 180, 50);
      btnStart.setFont(new Font("Sans-Serif", Font.PLAIN, 28));

      // Add the window components
      add(lblPic);
      add(diceLeft);
      add(lblFace);
      add(lblQuantity);
      add(currentQuantity);
      add(currentFace);
      add(info);
      add(yourRolls);
      add(txtQuantity);
      add(txtFace);
      add(btnBet);
      add(btnChallenge);
      add(btnContinue);
      add(btnStart);
      window.add(this); // Add this LiarsDiceGUI to the window
         
      window.setSize(600, 500);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);
      // The next three buttons do not begin enabled
      btnBet.setEnabled(false);
      btnChallenge.setEnabled(false);
      btnContinue.setEnabled(false);
      
      btnStart.addActionListener(new ActionListener()
      {
         // Runs when btnStart is pushed 
         public void actionPerformed(ActionEvent e)
         {
            // Restarts the game if the player lost their last die
            if(gameIsOver)
            {
               window.dispose(); // Closes the window
               new LiarsDiceGUI(); // Creates a new LiarsDiceGUI
            }
            
            // Otherwise the game continues on and a new round has begun
            btnStart.setVisible(false);
            btnStart.setEnabled(false);
            info.setText("");
            diceLeft.setText("Total Dice Left: " + totalDice);
            
            // All of the bots still in the game roll
            if(player1!=null)
               player1.roll();
            if(player2!=null)
               player2.roll();
            if(player3!=null)
               player3.roll();
            
            // We do not need to check if the human is null or not because if they lose their last die the game is over
            human.roll();
            yourRolls.setText("Your rolls are: " + human.listOfRolls());
            
            int random = generator.nextInt(3) + 1; // Randomly determine which bot bets first
            
            // Player 1 gets to bet first if the random is a 1 and they exist
            if(random == 1 && player1 != null)
            {
               playerId = 1;
               int[] bet = player1.firstBet();
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player1.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
            // Player 2 gets to bet first if the random is a 2 and they exist, or if player 1 does not exist and they do
            else if((random == 2 && player2 != null) || (player1 == null && player2 != null))
            {
               playerId = 2;
               int[] bet = player2.firstBet();
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player2.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
            // Player 3 gets to bet first if the random is a 3 and they exist, or if player 1 and player 2 do not exist
            else if((random == 3 && player3 != null) || (player1 == null && player2 == null))
            {
               playerId = 3;
               int[] bet = player3.firstBet();
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player3.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
            
            btnContinue.setEnabled(true); // Now that the bot has bet, the continue button can be pushed
         }
      });
      
      btnBet.addActionListener(new ActionListener()
      { 
         // Runs when btnBet is pushed
         public void actionPerformed(ActionEvent e)
         {
            // Attempts to parse what the user entered as ints
            try
            {
               quantity = Integer.parseInt(txtQuantity.getText());
               face = Integer.parseInt(txtFace.getText());
            }
            // They entered something that could not be parsed to an int
            catch(NumberFormatException nfException)
            {
               info.setText("<html><em>This is not a legal bet.</em> Try again.</html>");
            }
            if(human.betIsLegal(oldQuantity, quantity, oldFace, face))
            {
               currentQuantity.setText(human.getCurrentPlayer() + " bet that there are " + quantity);
               currentFace.setText(face + "'s");
               btnBet.setEnabled(false);
               btnChallenge.setEnabled(false);
               btnContinue.setEnabled(true);
               info.setText("");
            }
            else
               info.setText("<html><em>This is not a legal bet.</em> Try again.</html>");
         }
      });
      
      btnContinue.addActionListener(new ActionListener()
      {
         // Runs when btnContinue is pushed
         public void actionPerformed(ActionEvent e)
         {
            // btnContinue can call a variety of methods depending on the situation
            if(betIsChallenged && betIsCertainlyALie)
               liarChallenged();
            else if(betIsChallenged)
               checkBet();
            else if(roundIsOver)
               roundOver();
            else
               passTurn();
         }
      });
      
      btnChallenge.addActionListener(new ActionListener()
      {
         // Runs when btnChallenge is pushed
         public void actionPerformed(ActionEvent e)
         {
            btnBet.setEnabled(false);
            btnChallenge.setEnabled(false);
            btnContinue.setEnabled(true);
            info.setText("");
            call();
         }
      });
   }

   // This method passes the turn to the next player and allows them to play
   private void passTurn()
   {
      if(playerId == 1)
      {
         // Change the playerId to be the next player who is still in the game
         if(player2 != null)
         {
            playerId++;
            if(player1.betIsLie(totalDice, quantity, face))
            {
               betIsCertainlyALie = true;
               call(); // The bots will always call a bet that is certainly a lie
            }
            else if(player1.betIsUnlikely(totalDice, quantity, face))
            {
               int random = generator.nextInt(10) + 1;
               // The bot will call 80% of the time if the bet is unlikely
               if(random > 2)
                  call(); 
            }
            else
            {
               int[] bet = player2.computerBet(totalDice, quantity, face);
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player2.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
         }
         else if(player3 != null)
         {
            playerId = 3;
            if(player1.betIsLie(totalDice, quantity, face))
            {
               betIsCertainlyALie = true;
               call(); // The bots will always call a bet that is certainly a lie
            }
            else if(player1.betIsUnlikely(totalDice, quantity, face))
            {
               int random = generator.nextInt(10) + 1;
               // The bot will call 80% of the time if the bet is unlikely
               if(random > 2)
                  call();
            }
            else
            {
               int[] bet = player3.computerBet(totalDice, quantity, face);
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player3.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
         }
         else
         {
            playerId = 4;
            oldQuantity = quantity;
            oldFace = face;
            btnContinue.setEnabled(false);
            btnBet.setEnabled(true);
            btnChallenge.setEnabled(true);
            info.setText("<html><em>Your turn!</em> Either make a bet or challenge the previous bet.</html>");
         }
         
      }
      else if(playerId == 2)
      {
         // Change the playerId to be the next player who is still in the game
         if(player3 != null)
         {
            playerId++;
            if(player2.betIsLie(totalDice, quantity, face))
            {
               betIsCertainlyALie = true;
               call(); // The bots will always call a bet that is certainly a lie
            }
            else if(player2.betIsUnlikely(totalDice, quantity, face))
            {
               int random = generator.nextInt(10) + 1;
               // The bot will call 80% of the time if the bet is unlikely
               if(random > 2)
                  call();
            }
            else
            {
               int[] bet = player3.computerBet(totalDice, quantity, face);
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player3.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
         }
         else
         {
            playerId = 4;
            oldQuantity = quantity;
            oldFace = face;
            btnContinue.setEnabled(false);
            btnBet.setEnabled(true);
            btnChallenge.setEnabled(true);
            info.setText("<html><em>Your turn!</em> Either make a bet or challenge the previous bet.</html>");
         } 
      }
      else if(playerId == 3)
      {
         playerId++;
         oldQuantity = quantity;
         oldFace = face;
         btnContinue.setEnabled(false);
         btnBet.setEnabled(true);
         btnChallenge.setEnabled(true);
         info.setText("<html><em>Your turn!</em> Either make a bet or challenge the previous bet.</html>");  
      }
      else
      {
         if(player1 != null)
         {
            playerId = 1;
            if(human.betIsLie(totalDice, quantity, face))
            {
               betIsCertainlyALie = true;
               call(); // The bots will always call a bet that is certainly a lie
            }
            else if(human.betIsUnlikely(totalDice, quantity, face))
            {
               int random = generator.nextInt(10) + 1;
               //The bot will call 80% of the time if the bet is unlikely
               if(random > 2)
                  call();
            }
            else
            {
               int[] bet = player1.computerBet(totalDice, quantity, face);
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player1.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
         }
         else if(player2 != null)
         {
            playerId = 2;
            if(human.betIsLie(totalDice, quantity, face))
            {
               betIsCertainlyALie = true;
               call(); // The bots will always call a bet that is certainly a lie
            }
            else if(human.betIsUnlikely(totalDice, quantity, face))
            {
               int random = generator.nextInt(10) + 1;
               //The bot will call 80% of the time if the bet is unlikely
               if(random > 2)
                  call();
            }
            else
            {
               int[] bet = player2.computerBet(totalDice, quantity, face);
               quantity = bet[0];
               face = bet[1];
               currentQuantity.setText(player2.getCurrentPlayer() + " bets that there are " + quantity);
               currentFace.setText(face + "'s");
            }
         }
         else
            playerId = 3;
      }
   }

   // This method is for when the bet may or may not be a lie
   private void call()
   {
      if(playerId == 1)
         info.setText("<html><em>" + player1.getCurrentPlayer() + " challenges this bet!</em></html>"); 
      else if(playerId == 2)
         info.setText("<html><em>" + player2.getCurrentPlayer() + " challenges this bet!</em></html>");
      else if(playerId == 3)
         info.setText("<html><em>" + player3.getCurrentPlayer() + " challenges this bet!</em></html>");
      else
         info.setText("<html><em>" + human.getCurrentPlayer() + " challenge this bet!</em></html>");
      
      betIsChallenged = true;
   }

   // This method checks if the bet was a lie or not
   private void checkBet()
   {
      // Total up the rolls for all the players (1 is a wildcard, meaning it always counts toward the current bet)
      int total = 0;
      for(int i = 0; i < player1.getNumberOfDice(); i++)
      {
         if(player1.getRoll(i) == 1 || player1.getRoll(i) == face)
            total++;
      }
      for(int i = 0; i < player2.getNumberOfDice(); i++)
      {
         if(player2.getRoll(i) == 1 || player2.getRoll(i) == face)
            total++;
      }
      for(int i = 0; i < player3.getNumberOfDice(); i++)
      {
         if(player3.getRoll(i) == 1 || player3.getRoll(i) == face)
            total++;
      }
      for(int i = 0; i < human.getNumberOfDice(); i++)
      {
         if(human.getRoll(i) == 1 || human.getRoll(i) == face)
            total++; 
      }
      if(total >= quantity)
         falseChallenge();    
      else
         liarChallenged();
   }

   // This method is for when a player challenges but the bet was not a lie
   private void falseChallenge()
   {
      info.setText("<html>This bet was <strong>not</strong> a lie!</html>");
      betIsChallenged = false;
      betIsCertainlyALie = false;
      roundIsOver = true;
   }

   // This method is for when a player challenges and the bet was a lie
   private void liarChallenged()
   {
      info.setText("<html>This bet <strong>was</strong> a lie!</html>");
      betWasLie = true;
      betIsChallenged = false;
      betIsCertainlyALie = false;
      roundIsOver = true;   
   }

   // This method is for when the round is over
   private void roundOver()
   {
      if(betWasLie)
      {
         if(playerId == 1)
         {
            int newDice=human.getNumberOfDice() - 1;
            if(newDice == 0)
               gameOver();
            else
            {
               human.newNumberOfDice(newDice);
               info.setText("<html>" + human.getCurrentPlayer() + " lose a die.</html>");
            }
         }
         else if(playerId == 2)
         {
            int newDice=player1.getNumberOfDice() - 1;
            if(newDice == 0)
               player1 = null;
            else
            {
               player1.newNumberOfDice(newDice);
               info.setText("<html>" + player1.getCurrentPlayer() + " loses a die.</html>");
            }
         }
         else if(playerId == 3)
         {
            int newDice = player2.getNumberOfDice() - 1;
            if(newDice == 0)
               player2 = null;
            else
            {
               player2.newNumberOfDice(newDice);
               info.setText("<html>" + player2.getCurrentPlayer() + " loses a die.</html>");
            }
         }
         else
         {
            int newDice = player3.getNumberOfDice() - 1;
            if(newDice == 0)
               player3 = null;
            else{
               player3.newNumberOfDice(newDice);
               info.setText("<html>" + player3.getCurrentPlayer() + " loses a die.</html>");
            }
         }
      }
      else
      {
         if(playerId == 1)
         {
            int newDice=player1.getNumberOfDice() - 1;
            if(newDice == 0)
               player1 = null;
            else
            {
               player1.newNumberOfDice(newDice);
               info.setText("<html>" + player1.getCurrentPlayer() + " loses a die.</html>");
            }
         }
         else if(playerId == 2)
         {
            int newDice = player2.getNumberOfDice() - 1;
            if(newDice == 0)
               player2 = null;
            else
            {
               player2.newNumberOfDice(newDice);
               info.setText("<html>" + player2.getCurrentPlayer() + " loses a die.</html>");
            }
         }
         else if(playerId == 3)
         {
            int newDice = player3.getNumberOfDice() - 1;
            if(newDice == 0)
               player3=null;
            else
            {
               player3.newNumberOfDice(newDice);
               info.setText("<html>" + player3.getCurrentPlayer() + " loses a die.</html>");
            }
         }
         else
         {
            int newDice = human.getNumberOfDice() - 1;
            if(newDice == 0)
               gameOver();
            else
            {
               human.newNumberOfDice(newDice);
               info.setText("<html>" + human.getCurrentPlayer() + " lose a die.</html>");
            }
         }

      }
      totalDice--;
      yourRolls.setText("");
      currentQuantity.setText("");
      currentFace.setText("");
      btnContinue.setEnabled(false);
      btnStart.setVisible(true);
      btnStart.setEnabled(true);
      roundIsOver = false;
      betWasLie = false;
   }

   // This method is for when the player loses their last die
   private void gameOver()
   {
      info.setText("<html>You lost your last die! &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <strong>Game over.</strong></html>");
      btnContinue.setEnabled(false);
      gameIsOver = true;
   }

}
