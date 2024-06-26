package com.jakobniinja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;

public class Die extends JPanel {

  private static final long serialVersionUID = 1L;

  private static final int WIDTH = 60;

  private static final int HEIGHT = 60;

  private static final int AVAILABLE = 0;

  private static final int SELECTED = 1;

  private static final int HELD = 2;

  private int value = 1;

  private int state = AVAILABLE;

  private Random rand = new Random();

  public Die() {
    roll();

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        click();
      }
    });
  }

  public Die(int value) {
    this.value = value;

    addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        click();
      }
    });
  }

  public void click() {
    if (state == AVAILABLE) {
      state = SELECTED;
      repaint();
    } else if (state == SELECTED) {
      state = AVAILABLE;
      repaint();
    }
  }

  public Dimension getPreferredSize() {
    Dimension size = new Dimension(WIDTH, HEIGHT);
    return size;
  }

  public void paintComponent(Graphics g) {

    // fill the background
    switch (state) {
      case AVAILABLE:
        g.setColor(Color.WHITE);
        break;
      case SELECTED:
        g.setColor(Color.RED);
        break;
      case HELD:
        g.setColor(Color.LIGHT_GRAY);
    }
    g.fillRect(0, 0, WIDTH, HEIGHT);

    // draw a border
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

    // draw the dots
    switch (value) {
      case 5:
        drawDot(g, WIDTH / 4, HEIGHT / 4);
        drawDot(g, WIDTH * 3 / 4, HEIGHT * 3 / 4);
      case 3:
        drawDot(g, WIDTH / 4, HEIGHT * 3 / 4);
        drawDot(g, WIDTH * 3 / 4, HEIGHT / 4);
      case 1:
        drawDot(g, WIDTH / 2, HEIGHT / 2);
        break;
      case 6:
        drawDot(g, WIDTH / 4, HEIGHT / 2);
        drawDot(g, WIDTH * 3 / 4, HEIGHT / 2);
      case 4:
        drawDot(g, WIDTH / 4, HEIGHT / 4);
        drawDot(g, WIDTH * 3 / 4, HEIGHT * 3 / 4);
      case 2:
        drawDot(g, WIDTH * 3 / 4, HEIGHT / 4);
        drawDot(g, WIDTH / 4, HEIGHT * 3 / 4);
    }

    // TODO: break between each case?
  }

  private void drawDot(Graphics g, int x, int y) {
    g.fillOval(x - 5, y - 5, 10, 10);

  }

  public int roll() {
    value = rand.nextInt(6) + 1;
    repaint();
    return value;
  }

  public boolean isAvailable() {
    return state == AVAILABLE;
  }

  public boolean isSelected() {
    return state == SELECTED;
  }

  public boolean isHeld() {
    return state == HELD;
  }

  public int getValue() {
    return value;
  }

  public void hold() {
    state = HELD;
    repaint();
  }

  public void makeAvailable() {
    state = AVAILABLE;
    repaint();
  }
}
