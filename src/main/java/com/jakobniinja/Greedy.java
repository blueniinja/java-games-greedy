package com.jakobniinja;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Greedy extends JFrame {

  private static final long serialVersionUID = 1L;

  private int points = 0;

  private int newPoints = 0;

  private int score = 0;

  private int round = 1;

  private JLabel pointsLabel = new JLabel("0");

  private JLabel scoreLabel = new JLabel("0");

  private JLabel roundLabel = new JLabel("1");

  private Font smallFont = new Font(Font.DIALOG, Font.PLAIN, 12);

  private Font bigFont = new Font(Font.DIALOG, Font.BOLD, 36);

  private JButton rollButton = new JButton("Roll");

  private Die[] dice = new Die[6];

  private int highScore = 0;

  private JLabel highScoreLabel = new JLabel();

  public static final String HIGH_SCORE_TEXT = "The previous highscore was: ";

  private static final String FILENAME = "highscore.txt";

  public Greedy() {
    initGUI();

    setTitle("Greedy");
    setResizable(false);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void initGUI() {
    TitleLabel titleLabel = new TitleLabel("Greedy");
    add(titleLabel, BorderLayout.PAGE_START);

    // main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GREEN);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    add(mainPanel, BorderLayout.CENTER);

    // score panel
    JPanel scorePanel = new JPanel();
    scorePanel.setBackground(Color.GREEN);
    mainPanel.add(scorePanel);

    JLabel roundTitleLabel = new JLabel("Round: ");
    roundTitleLabel.setFont(smallFont);
    scorePanel.add(roundTitleLabel);
    roundLabel.setFont(bigFont);
    scorePanel.add(roundLabel);

    JLabel scoreTitleLabel = new JLabel("Score: ");
    scoreTitleLabel.setFont(smallFont);
    scorePanel.add(scoreTitleLabel);
    scoreLabel.setFont(bigFont);
    scorePanel.add(scoreLabel);

    // dice score panel
    JPanel diceRowPanel = new JPanel();
    diceRowPanel.setBackground(Color.GREEN);
    mainPanel.add(diceRowPanel);

    // points panel
    JPanel pointsPanel = new JPanel();
    pointsPanel.setBackground(Color.green);
    pointsPanel.setLayout(new BoxLayout(pointsPanel, BoxLayout.Y_AXIS));
    Dimension size = new Dimension(70, 70);
    pointsPanel.setPreferredSize(size);
    diceRowPanel.add(pointsPanel);

    JLabel pointsTitleLabel = new JLabel("Points: ");
    pointsTitleLabel.setFont(smallFont);
    pointsTitleLabel.setAlignmentX(CENTER_ALIGNMENT);
    pointsPanel.add(pointsTitleLabel);

    pointsLabel.setFont(bigFont);
    pointsLabel.setAlignmentX(CENTER_ALIGNMENT);
    pointsPanel.add(pointsLabel);

    // dice panel
    JPanel dicePanel = new JPanel();
    dicePanel.setBackground(Color.GREEN);
    diceRowPanel.add(dicePanel);

    for (int i = 0; i < 6; i++) {
      dice[i] = new Die();
      dice[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
          clickedDie();
        }
      });
      dicePanel.add(dice[i]);
    }

    // high score panel

    // button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLACK);
    add(buttonPanel, BorderLayout.PAGE_END);

    rollButton.setEnabled(false);
    rollButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updatePoints();
        rollRemainingDice();
        rollButton.setEnabled(false);
      }
    });

    buttonPanel.add(rollButton);
    JButton endRoundButton = new JButton("End Round");

    endRoundButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        endRound();
      }
    });
    buttonPanel.add(endRoundButton);
  }

  private void endRound() {
    if (isValidSelection() && newPoints > 0) {
      score += points + newPoints;
    } else {
      String message = "Do you want to play again?";
      int option = JOptionPane.showConfirmDialog(this, message, "Play Again?", JOptionPane.YES_NO_OPTION);

      if (option == JOptionPane.YES_OPTION) {
        score = 0;
        points = 1;
        scoreLabel.setText("0");
        roundLabel.setText("1");
        rollAllDice();
      } else {
        System.exit(0);
      }
    }
    points = 0;
    newPoints = 0;
    pointsLabel.setText("0");
    scoreLabel.setText("" + score);
    if (round < 10) {
      round++;
      roundLabel.setText("" + round);
      rollAllDice();
    }
  }

  private void rollRemainingDice() {
    int count = 0;
    for (int i = 0; i < 6; i++) {
      if (dice[i].isSelected()) {
        dice[i].hold();
      } else if (!dice[i].isHeld()) {
        dice[i].roll();
        count++;
      }
    }
    if (count == 0) {
      rollAllDice();
    }
  }

  private void rollAllDice() {
    for (int i = 0; i < 6; i++) {
      dice[i].makeAvailable();
      dice[i].roll();
    }
    rollButton.setEnabled(false);
  }

  private void updatePoints() {
    points += newPoints;
    pointsLabel.setText("" + points);
    newPoints = 0;
  }

  private void clickedDie() {
    if (isValidSelection()) {
      rollButton.setEnabled(true);
    } else {
      rollButton.setEnabled(false);
    }

    pointsLabel.setText("" + (points + newPoints));
  }

  public boolean isValidSelection() {
    int[] count = {0, 0, 0, 0, 0, 0};
    int totalCount = 0;
    boolean valid = true;
    newPoints = 0;

    for (int i = 0; i < count.length; i++) {
      if (dice[i].isSelected()) {
        int value = dice[i].getValue();
        count[value - 1]++;
        totalCount++;
      }
    }

    if (totalCount == 0) {
      valid = false;
    } else if (count[0] == 1 && count[1] == 1 &&
        count[2] == 1 && count[3] == 1 &&
        count[4] == 1 && count[5] == 1) {
      newPoints += 250;
    } else {
      for (int i = 0; i < count.length; i++) {
        switch (count[i]) {
          case 1:
            if (i == 0) {
              newPoints += 10;
            } else if (i == 4) {
              newPoints += 5;
            } else {
              valid = false;
            }
            break;
          case 2:
            if (i == 0) {
              newPoints += 20;
            } else if (i == 4) {
              newPoints += 10;
            } else {
              valid = false;
            }
            break;

          case 3:
            if (i == 0) {
              newPoints += 100;
            } else {
              newPoints += 10 * (i + 1);
            }
            break;

          case 4:
            newPoints += 250;
            break;
          case 5:
            newPoints += 300;
            break;
          case 6:
            newPoints += 500;
            break;
        }
      }
    }
    return valid;
  }

  public static void main(String[] args) {
    String className = UIManager.getCrossPlatformLookAndFeelClassName();
    try {
      UIManager.setLookAndFeel(className);
    } catch (Exception e) {
      //
    }

    EventQueue.invokeLater(Greedy::new);
    // TODO: complete Listing 9-51
  }
}
