package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FallenderStein /* implements KeyListener */ {
    private JButton gelbesFeld = new JButton();
    private JButton nextTickButton = new JButton("Next Tick");
    private JButton autoTickButton = new JButton("Auto Tick");
    private JButton stopButton = new JButton("Stop");
    private JButton resetButton = new JButton("Reset");
    private JButton closeButton = new JButton("Schließen");

    private int counter = 0;

    public void startGUI() {


        JFrame frame = new JFrame("Der fallende Stein");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Timer timer1 = new Timer(500, null);




        // absolute Positionierung: eigentlich nicht empfohlen
        gelbesFeld.setBounds(10, 10, 50, 50);
        gelbesFeld.setBackground(Color.YELLOW);
        nextTickButton.setBounds(220, 10, 120, 25);
        autoTickButton.setBounds(220, 60, 120, 25);
        stopButton.setBounds(220, 110, 120, 25);
        resetButton.setBounds(220, 160, 120, 25);
        closeButton.setBounds(220, 210, 120, 25);

        ActionListener alCountUp = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gelbesFeld.getBounds().y < 600) {
                    gelbesFeld.setLocation(gelbesFeld.getBounds().x, gelbesFeld.getBounds().y + 40);
                }
                else timer1.stop();
            }
        };

        timer1.addActionListener(alCountUp);

        nextTickButton.addActionListener(alCountUp);

        ActionListener alClose = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        closeButton.addActionListener(alClose);
        ActionListener alTimerStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer1.start();
            }
        };
        autoTickButton.addActionListener(alTimerStart);
        ActionListener alTimerStop = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer1.stop();
            }
        };
        stopButton.addActionListener(alTimerStop);
        ActionListener alReset = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { gelbesFeld.setLocation(10, 10);}
        };
        resetButton.addActionListener(alReset);

        KeyListener keyJa = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tasteAbfrage(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        gelbesFeld.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                gelbesFeld.requestFocus();
            }
        });

        gelbesFeld.addKeyListener(keyJa);

        panel.add(gelbesFeld);
        panel.add(nextTickButton);
        panel.add(stopButton);
        panel.add(autoTickButton);
        panel.add(resetButton);
        panel.add(closeButton);

        frame.add(panel);
        frame.setSize(1280,700);
        frame.setVisible(true);
    }

    private void tasteAbfrage(int taste) {
        System.out.println("Taste" + taste);

        switch (taste) {
            //Pfeiltaste links
            case 37: moveLeft();
                break;
            //Pfeiltaste hoch
            case 38: moveUp();
            break;
            //Pfeiltaste rechts
            case 39: moveRight();
                break;
            //Pfeiltaste runter
            case 40: moveDown();
            break;
        }
    }

    /*
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        tasteAbfrage(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
     */

    private void moveUp() {
        if(getY() > 60) gelbesFeld.setLocation(getX(), getY() - 50);
    }

    private void moveDown() {
        if (getY() < 640) gelbesFeld.setLocation(getX(), getY() + 50);
    }

    private void moveLeft() {
        if (getX() > 60) gelbesFeld.setLocation(getX() - 50, getY());
    }

    private void moveRight() {
        if (getX() < 1220) gelbesFeld.setLocation(getX() + 50, getY());
    }

    private int getX() {
        return gelbesFeld.getBounds().x;
    }

    private int getY() {
        return gelbesFeld.getBounds().y;
    }

}
