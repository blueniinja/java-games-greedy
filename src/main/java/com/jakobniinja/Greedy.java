package com.jakobniinja;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.util.Queue;
import java.util.jar.JarFile;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Greedy extends JFrame {

  private static final long serialVersionUID = 1L;

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
    add(mainPanel, BorderLayout.CENTER);

    Die die = new Die();
    mainPanel.add(die);

    // score panel

    // points panel

    // dice panel

    // high score panel

    // button panel
  }

  public static void main(String[] args) {
    String className = UIManager.getCrossPlatformLookAndFeelClassName();
    try {
      UIManager.setLookAndFeel(className);
    } catch (Exception e) {
      //
    }

    EventQueue.invokeLater(Greedy::new);
  // TODO: complete Listing 9-20
  }
}
