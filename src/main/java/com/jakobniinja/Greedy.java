package com.jakobniinja;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    dice[0] = new Die(1);
    dice[1] = new Die(2);
    dice[2] = new Die(3);
    dice[3] = new Die(4);
    dice[4] = new Die(5);
    dice[5] = new Die(6);

    for (int i = 0; i < 6; i++) {
      dicePanel.add(dice[i]);
    }

    // high score panel

    // button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLACK);
    add(buttonPanel, BorderLayout.PAGE_END);

    buttonPanel.add(rollButton);
    JButton endRoundButton = new JButton("End Round");
    buttonPanel.add(endRoundButton);
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
    // TODO: complete Listing 9-33
  }
}
