package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SpielfeldALT {
    private int x;
    private int y;
    private String titel;
    private JFrame spielfeld;
    private JPanel paneel;
    private Block bl;
    private List<Block> blockList;
    private Timer timer1 = new Timer(220, null);
    private int keyId;
    private int counter = 0;
    private boolean threadRunning = true;
    //Versucht die Spielgeschwindigkeit zu optimieren
    private Thread threadCount = new Thread(() -> {
        counter = 0;
        threadRunning = true;

        while (threadRunning) {
            counter++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private KeyListener tastenDruck = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            tasteGedrueckt(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    FocusListener focusBtn = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            bl.requestFocus();
        }
    };

    private ActionListener alGameStart = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            threadRunning = true;

            //Beende Spiel und frage um neustart
            if (!isPositionFree('d') && bl.getPosY() == 50) {
                timer1.stop();
                int result = JOptionPane.showConfirmDialog(null, "Du hast verloren, HAHAHA!!! \n \n Möchtest du nochmal spielen?");
                if (result == JOptionPane.YES_OPTION) {
                    for(Block b: blockList) {
                        paneel.remove(b);
                    }
                    spielfeld.revalidate();
                    spielfeld.repaint();
                    blockList = new ArrayList<>();
                    addBlock();
                    bl = blockList.get(0);
                    paneel.add(bl);
                    bl.addKeyListener(tastenDruck);
                    bl.requestFocus();
                    timer1.start();
                }
                else spielfeld.dispose();
            }
            // Wenn Block in Spielfeld und Position darunter frei ist
            else if (bl.getPosY() < 550 && isPositionFree('d'))
            {
                bl.setLocation(bl.getPosX(), bl.getPosY() + 25);
                //System.out.println(bl.getBounds());
            }
            /* Wenn Block am Ende des Spielfeldes angekommen oder Position unter dem Block nicht frei ist halte
                den aktuellen Block an und füge einen neuen hinzu */
            else if (bl.getPosY() >= 550 || !isPositionFree('d')) {
                System.out.println(bl.getBounds());
                //System.out.println("ID: " + Block.getId());
                destroyBlocks();
                addBlock();
                bl = blockList.get(blockList.size() - 1);
                paneel.add(bl);
                bl.addKeyListener(tastenDruck);
                bl.requestFocus();
            }
            threadRunning = false;
            timer1.setDelay(220 - counter);
        }
    };


    SpielfeldALT (int x, int y, String titel) {
        this.x = x;
        this.y = y;
        this.titel = titel;

        this.spielfeld =  new JFrame(getTitel());
        this.spielfeld.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.spielfeld.setSize(getX(), getY());

        this.paneel = new JPanel();
        this.paneel.setLayout(null);

        this.bl = this.blockList.get(blockList.size() - 1);
        this.bl.addKeyListener(tastenDruck);
        this.bl.requestFocus();
        this.paneel.add(bl);
        this.spielfeld.add(paneel);
        this.spielfeld.setVisible(true);
        this.timer1.addActionListener(alGameStart);
        this.timer1.start();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    private void tasteGedrueckt(int taste) {
        //System.out.println(taste);
        switch (taste) {
            //Pfeiltaste links
            case 37: if(bl.getPosX() > 60 && isPositionFree('l')) bl.setLocation(bl.getPosX() - 25, bl.getPosY());
                break;
            //Pfeiltaste hoch
            case 38: if (bl.getPosY() > 60) bl.setLocation(bl.getPosX(), bl.getPosY() - 25);
                break;
            //Pfeiltaste rechts
            case 39: if (bl.getPosX() < 275 && isPositionFree('r')) bl.setLocation(bl.getPosX() + 25, bl.getPosY());
                break;
            //Pfeiltaste runter
            case 40: if (bl.getPosY() < getY() - 120 && isPositionFree('d')) bl.setLocation(bl.getPosX(), bl.getPosY() + 25);
                break;
        }
    }

    private void addBlock() {
        Block block = new Block(Color.YELLOW);
        blockList.add(block);
    }

    private boolean isPositionFree(char direction) {
        switch (direction) {
            case 'd':
                for (Block b : blockList) {
                    // Wenn kein Block unter dem aktuellen ist und die X Position der Blöcke übereinstimmen
                    if (b.getPosY() == bl.getPosY() + 25 && b.getPosX() == bl.getPosX()) return false;
                }
                break;
            case 'l':
                for (Block b : blockList) {
                    if (b.getPosX() == bl.getPosX() - 25 && b.getPosY() == bl.getPosY()) return false;
                }
                break;
            case 'r':
                for (Block b : blockList) {
                    if (b.getPosX() == bl.getPosX() + 25 && b.getPosY() == bl.getPosY()) return false;
                }
                break;
        }
        return true;
    }

    //Zerstört alle Reihen voller Blöcke
    private void destroyBlocks() {
        int lines = 0;
        //vertikale Reihen
        for (int i = 550; i >= 50; i -= 25) {
            //horizontale Reihen
            byte anzBlocks = 0;
            for (int j = 50; j <= 275; j += 25) {
                for (Block blocks: blockList) {
                    if (blocks.getPosX() == j && blocks.getPosY() == i) {
                        anzBlocks++;
                        //System.out.println(anzBlocks);
                    }
                }
            }
            if (anzBlocks == 10) {
                // Solange Blöcke vorhanden sind
                while (anzBlocks > 0) {
                    //Setze index auf 0
                    int index = 0;
                    //Laufe durch die Blockliste durch und prüfe auf übereinstimmungen
                    for (Block blocks : blockList) {
                        if (blocks.getPosY() == i) {
                            //Wenn Übereinstimmung entdeckt, schreibe Index des Elements in variable index und lösche Objekt vom Panel, beende die Schleife
                            index = blockList.indexOf(blocks);
                            explosion(blocks.getPosX(), blocks.getPosY());
                            paneel.remove(blocks);
                            break;
                        }
                    }
                    //Lösche Objekt aus der Liste und verringere die Anzahl um 1
                    blockList.remove(index);
                    anzBlocks--;
                }
                //Zeichne das Spielfeld neu und erhöhe die Lines um 1
                spielfeld.repaint();
                spielfeld.revalidate();
                lines++;
            }
        }
        //Wenn Lines größer als 0
        if (lines > 0) {
            //Nehme anzahl der Lines mal 25 Pixel (Höhe eines Blocks) und erhöhe die Höhe aller verbliebenden Blöcke
            lines = lines * 25;
            for (Block blocks: blockList) {
                blocks.setLocation(blocks.getPosX(), blocks.getPosY() + lines);
            }
        }
        System.out.println(blockList.size());
    }

    private void explosion(int xPos, int yPos) {
        Thread thread = new Thread(() -> {
            JLabel exlabel = new JLabel();
            paneel.add(exlabel);
            exlabel.setSize(25,25);
            exlabel.setLocation(xPos, yPos);

            for (int i = 1; i <= 17; i++) {
                exlabel.setIcon(new ImageIcon(Class.class.getResource("/explosion/" + i + ".png")));
                try {
                    Thread.sleep(44);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            paneel.remove(exlabel);
            spielfeld.repaint();
            spielfeld.revalidate();
            Thread.currentThread().interrupt();
            return;
        });
        thread.start();
    }
}
