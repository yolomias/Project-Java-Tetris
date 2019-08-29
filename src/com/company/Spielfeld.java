//Klasse Spielfeld enthält das Spielfeld und all seine Elemente so wie deren Logik
package com.company;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.xml.soap.Node;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Spielfeld {
    //x, y Größe des Spielfensters
    private int x;
    private int y;
    //Titel des Spielfenster
    private String titel;
    //Darstellung des Frames und des Paneels mit den Inhalt
    private JFrame spielfeld;
    private JLayeredPane paneel;
    //Ein unsichtbares Paneel im Vordergrund um spezialeffekte darzustellen
    private JPanel pan;
    // private JLayeredPane layPane = new JLayeredPane();
    //Eine Liste um die Tetruinos des aktuellen Spiels abzuspeichern
    private List<Tetruino> tetruinos;
    //Der aktive Tetruino
    private Tetruino tetrus;
    //Der Tetruino der als nächstes kommt
    private Tetruino nextTretrus;
    //Beeinflusst das Spieltempo
    private int gamespeed = 500;
    //Der Timer, der die Tetruinos nach der Spielgeschwindigkeit bewegt
    private Timer timer1 = new Timer(gamespeed, null);
    //Weiß nicht mehr inwiefern es das Programm beeinflusst
    private int keyId;
    //Wird benötigt für die Methode ThreadCount
    private int counter = 0;
    //Ein Label was die Frame Rate anzeigt
    private JLabel fpsLabel = new JLabel();
    //Zeigt "Nächster Tetruino"
    private JLabel nextLabel = new JLabel("Nächster Tetruino");
    //Zeigt das aktuelle Level an
    private JLabel levelLabel = new JLabel();
    //Variable für das aktuelle Level
    private int level = 1;
    //Zeigt die zerstörten Reihen an
    private JLabel rowLabel = new JLabel();
    //Zählt die Reihen hoch
    private int rows = 0;
    //Zeigt die Punktzahl an
    private JLabel scoreLabel = new JLabel();
    //Variable in der der Punktestand eingespeichert ist
    private long score = 0;
    //Hintergrund des Spielfeldes
    private JLabel hintergrund = new JLabel("");
    //Hintergrund des Feldes indem sich die Tetruinos bewegen
    private JLabel background = new JLabel("");
    private JLabel nextBackground = new JLabel("");
    //Wird für die Methode ThreadCount benötigt
    private boolean threadRunning = true;
    //Minimalistisches Design der Blöcke
    private boolean isMinimalistic;
    private JCheckBox minimalisticBox = new JCheckBox();
    // Variablen um den Spielablauf zu kontrollieren
    private boolean isGameNewstarting = false;
    private boolean gameRunning;
    private boolean isGameRunning;
    private JButton pauseButton = new JButton();
    //Label für die Pause Funktion
    private JLabel pauseLabel = new JLabel("Spiel pausieren / fortsetzen");
    //Labels um die Besten 5 Spieler anzuzeigen
    private JLabel scoreTitel = new JLabel();
    private JLabel[] scores = new JLabel[5];
    //Eine Spielerliste in die die Punkte der letzten Spieler eingetragen werden
    private List<Spieler> spielerlist;
    private byte sekunden;
    private byte minuten;
    private byte stunden;
    //Timer für die vergange Spielzeit
    private JLabel timerLabel = new JLabel("Vergangene Zeit: ");
    private ActionListener zeitVergangen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sekunden++;
            if (sekunden >= 60) {
                minuten++;
                sekunden = 0;
            }
            if (minuten >= 60) {
                stunden++;
                minuten = 0;
            }
            String s = "";
            String m = "";
            String h = "";

            if (sekunden < 10) s += "0" + sekunden;
            else s += sekunden;
            if (minuten < 10) m += "0" + minuten;
            else m += minuten;
            if (stunden < 10) h += "0" + stunden;
            else if (stunden >= 120) {
                stunden = 120;
                h = "max";
            }
            else h += stunden;

            timerLabel.setText("Vergangene Zeit: " + h + ":" + m + ":" + s);
        }
    };
    private Timer timerVergangeneZeit = new Timer(1000, zeitVergangen);
    //Radio Buttons um den Hintergrund zu wechseln
    private JRadioButton background1 = new JRadioButton("1");
    private JRadioButton background2 = new JRadioButton("2");
    private JRadioButton background3 = new JRadioButton("3");
    private JRadioButton background4 = new JRadioButton("4");
    private ButtonGroup bgBtnGrp = new ButtonGroup();
    private JLabel backgroundLabel = new JLabel("Hintergrundbild ändern");
    //Prüft bei einem Tastenabdruck ab, ob für diese Taste eine Funktion hinterlegt wurde, funktioniert nur wenn
    //der KeyListener einem Block hinzugefügt wurde
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
    // Ein Focus Listener der sich immer für das Spielfeld den Focus holt, falls es ihn verlieren sollte
    FocusListener focus = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            if (!tetruinos.get(tetruinos.size() - 1).tetruBlocks.get(0).hasFocus())
                tetruinos.get(tetruinos.size() - 1).tetruBlocks.get(0).requestFocus();
        }
    };
    //Der Action Listener enthält die Spiellogik
    private ActionListener alGameStart = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Wird in einem Thread gestartet um das Spieltempo zu erhöhen, führt selten dazu dass sich ein Tetruino zersplittert
            Thread thread = new Thread(() -> {
                //starte ThreadCount
                 threadCount();

            /*


            //Beende Spiel und frage um neustart
            if (!isPositionFree('d') && bl.getPosY() == 50) {

                timer1.stop();
                int result = JOptionPane.showConfirmDialog(null, "Du hast verloren, HAHAHA!!! \n \n Möchtest du nochmal spielen?");
                if (result == JOptionPane.YES_OPTION) {
                    for(Tetruino b: blockList) {
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

            } */
               //Wenn startposition blockiert und position darunter nicht frei beende das Spiel
                if (!isNextTetruinoPositionFree(tetrus, 'd') && tetrus.tetruBlocks.get(0).getPosY() == 50
                        && tetrus.tetruBlocks.get(0).getPosX() == 150) {
                    timer1.stop();
                    timerVergangeneZeit.stop();
                    JOptionPane.showMessageDialog(spielfeld, "Das Spiel ist vorbei, du hast verloren!");
                    if (score >= 10000) addSpielerToList();
                    else JOptionPane.showMessageDialog(spielfeld, "Du warst nicht mal gut genug um 10000 Punkte zu erreichen,\n" +
                            "für einen Platz auf der Highscoreliste hat es daher nicht gereicht!");
                    saveSpielerlistToDisk();
                    //Frage nach ob das Spiel neugestartet werden soll
                    int result = JOptionPane.showConfirmDialog(spielfeld, "Willst du es nochmal versuchen?");
                    if (result == JOptionPane.YES_OPTION) {
                        System.out.println("Ja");
                        neuStart();
                    } else {
                        spielfeld.dispose();
                    }
                }
                // Wenn Block in Spielfeld und Position darunter frei ist
                else if (isNextTetruinoPositionFree(tetrus, 'd')) tetrus.move('d');
                // Wenn nächste Position nicht frei ist dann halte aktuellen Tetruino an und hole den nächsten
                else if (!isNextTetruinoPositionFree(tetrus, 'd')) {
                    //Mache aktuellen Tetruino nicht mehr aktiv
                    tetrus.setActive(false);
                    //Zerstöre Blöcke wenn möglich
                    destroyBlocks();
                    //Füge einen neuen Tetriuno hinzu
                    addTetruino();
                    //Mache vorletzten Tetruino zum aktuellen Tetriuno
                    tetrus = tetruinos.get(tetruinos.size() - 2);
                    tetrus.move('s');
                    tetrus.setActive(true);
                    //Setze den nächsten Tetruino
                    nextTretrus = tetruinos.get(tetruinos.size() - 1);
                    //Bewege den Tetruino in das nächste Tetruino Feld
                    nextTretrus.move('n');
                    //Füge ihn zum Paneel hinzu
                    nextTretrus.addToPanel(paneel, nextTretrus);
                    nextTretrus.setActive(true);
                    //Setze den KeyListener auf den aktuellen Tetruino
                    tetrus.tetruBlocks.get(0).addKeyListener(tastenDruck);
                    //Erhöhe Punktzahl um 5
                    score += 5;
                   //System.out.println("Tetruiono Größe: " + tetruinos.size());
                   //System.out.println("Level: " + level);
                   //System.out.println("Gamespeed: " + gamespeed);
                }
            /* Wenn Block am Ende des Spielfeldes angekommen oder Position unter dem Block nicht frei ist halte
                den aktuellen Block an und füge einen neuen hinzu */
            /*
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

            */
                //Wenn berechnung größer 0 setze die verzögerung
                if (gamespeed - counter > 0) timer1.setDelay(gamespeed - counter);
                //Setze den Framerate Zähler
                fpsLabel.setText("FPS: " + (1000 / gamespeed - (counter / 1000)) );
                //Beende den Zähler
                threadRunning = false;
                //Aktuallisiere Punkte und Reihen etc
                updateGame();
                Toolkit.getDefaultToolkit().sync();
                // System.out.println("Counter: " + counter);
            });
            //Starte den Spiel Thread
            thread.start();
        }
    };
    //Action um das Design minilastischer zu machen oder das alte wiederherzustellen
    private Action minimalisticAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (minimalisticBox.isSelected()) {
                isMinimalistic = true;
                for(Tetruino t: tetruinos) {
                    for (Block b: t.tetruBlocks) {
                        b.setIcon(null);
                    }
                }
            }
            else {
                isMinimalistic = false;
                for(Tetruino t: tetruinos) {
                    for (Block b: t.tetruBlocks) {
                        b.setIcon(loadIcon("/texture/block.png"));
                    }
                }
            }
        }
    };

    /*
    private void runningGame() {

    Thread threadGame = new Thread(() -> {
        while (gameRunning) {
            threadCount();

            // Wenn Block in Spielfeld und Position darunter frei ist
            if (isNextTetruinoPositionFree(tetrus, 'd')) tetrus.move('d');
            else if (!isNextTetruinoPositionFree(tetrus, 'd')) {
                destroyBlocks();
                addTetruino();
                tetrus = tetruinos.get(tetruinos.size() - 1);
                tetrus.addToPanel(paneel, tetrus);
                tetrus.tetruBlocks.get(0).addKeyListener(tastenDruck);
                score += 5;
            }

            if (gamespeed - counter > 0) {
                try {
                    Thread.sleep(gamespeed - counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threadRunning = false;
            updateGame();
        }
    });
    threadGame.start();
}
     */

    //Konstructor der Spielfeldklasse
    Spielfeld(int x, int y, String titel) {
        this.x = x;
        this.y = y;
        this.titel = titel;
        //Erstelle Spiel Frame
        this.spielfeld =  new JFrame(getTitel());
        this.spielfeld.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.spielfeld.setSize(getX(), getY());
        this.spielfeld.setResizable(false);
        //Erstelle beide Paneele für den Vordergrund und Hintergrund
        this.paneel = new JLayeredPane();
        this.paneel.setLayout(null);
        this.pan = new JPanel();
        this.pan.setBounds(0, 0, getX(), getY());
        this.pan.setFocusable(false);
        this.pan.setOpaque(false);
        this.pan.setLayout(null);
        //Initialisierung von Checkbox für minimalistischeres Design
        this.isMinimalistic = false;
        this.minimalisticBox.setBounds(595, 250, 200, 30);
        this.minimalisticBox.setFocusable(false);
        this.minimalisticBox.setAction(minimalisticAction);
        this.minimalisticBox.setText("Minimalistischeres Design");
        this.minimalisticBox.setOpaque(false);
        this.paneel.add(minimalisticBox);
        //Erstelle die Tetruinos Liste und füge gleich 2 hinzu
        this.tetruinos = new ArrayList<>();
        this.addTetruino();
        this.addTetruino();
        //Erstelle aktuellen Tetruino
        this.tetrus = tetruinos.get(0);
        this.tetrus.addToPanel(paneel, tetrus);
        this.tetrus.setActive(true);
       // this.tetrus.tetruBlocks.get(0).addKeyListener(tastenDruck);
        //Erstelle nächsten Tetruino und bewege ihn in das andere Feld
        this.nextTretrus = tetruinos.get(1);
        this.nextTretrus.addToPanel(paneel, nextTretrus);
        this.nextTretrus.move('n');
        this.nextTretrus.setActive(true);
        //Setze die Maße und Positionen der Labels
        this.fpsLabel.setBounds(75, 10, 100, 30);
        this.nextLabel.setBounds(400, 10, 200, 30);
        this.levelLabel.setBounds(400, 250, 300, 30);
        this.rowLabel.setBounds(400, 290, 300, 30);
        this.scoreLabel.setBounds(400, 330, 300, 30);
        //Setze deren Inhalt
        this.levelLabel.setText("Level: " + level);
        this.rowLabel.setText("Reihen: " + rows);
        this.scoreLabel.setText("Punkte: " + score);
        this.fpsLabel.setText("FPS: 0");
        //Füge sie dem Paneel hinzu
        this.paneel.add(fpsLabel);
        this.paneel.add(nextLabel);
        this.paneel.add(levelLabel);
        this.paneel.add(rowLabel);
        this.paneel.add(scoreLabel);
        this.paneel.add(backgroundLabel);
        //Selektiere einen der Radio Buttons
        background1.setSelected(true);
        //Setze die Position
        backgroundLabel.setBounds(600, 50, 150, 30);
        background1.setLocation(600, 80);
        background2.setLocation(620, 80);
        background3.setLocation(640, 80);
        background4.setLocation(660, 80);
        //Entferne den Fokus, damit der Tetruino ihn nicht mehr verlieren kann
        background1.setFocusable(false);
        background2.setFocusable(false);
        background3.setFocusable(false);
        background4.setFocusable(false);
        //Setze die Größe
        background1.setSize(15,15);
        background2.setSize(15,15);
        background3.setSize(15,15);
        background4.setSize(15,15);
        //Füge die Radio Buttons dem Paneel hinzu
        this.paneel.add(background1);
        this.paneel.add(background2);
        this.paneel.add(background3);
        this.paneel.add(background4);
        //Setze Icons
        background1.setIcon(loadIcon("/radio_buttons/radio.png"));
        background2.setIcon(loadIcon("/radio_buttons/radio.png"));
        background3.setIcon(loadIcon("/radio_buttons/radio.png"));
        background4.setIcon(loadIcon("/radio_buttons/radio.png"));
        background1.setSelectedIcon(loadIcon("/radio_buttons/radio_selected.png"));
        background2.setSelectedIcon(loadIcon("/radio_buttons/radio_selected.png"));
        background3.setSelectedIcon(loadIcon("/radio_buttons/radio_selected.png"));
        background4.setSelectedIcon(loadIcon("/radio_buttons/radio_selected.png"));
        background1.setPressedIcon(loadIcon("/radio_buttons/radio_pressed.png"));
        background2.setPressedIcon(loadIcon("/radio_buttons/radio_pressed.png"));
        background3.setPressedIcon(loadIcon("/radio_buttons/radio_pressed.png"));
        background4.setPressedIcon(loadIcon("/radio_buttons/radio_pressed.png"));
        //Füge die Action Listener hinzu
        background1.addActionListener(e -> changeBackgrounds(1));
        background2.addActionListener(e -> changeBackgrounds(2));
        background3.addActionListener(e -> changeBackgrounds(3));
        background4.addActionListener(e -> changeBackgrounds(4));
        // Entferne Hintergrundfarbe
        background1.setOpaque(false);
        background2.setOpaque(false);
        background3.setOpaque(false);
        background4.setOpaque(false);
        //Gruppiere die Radio Buttons
        this.bgBtnGrp.add(background1);
        this.bgBtnGrp.add(background2);
        this.bgBtnGrp.add(background3);
        this.bgBtnGrp.add(background4);
        //Pause Button
        this.pauseLabel.setBounds(600, 110, 200, 30);
        this.pauseButton.setBounds(660, 150, 56, 73);
        this.pauseButton.setIcon(loadIcon("/button_icons/play_button.png"));
        this.pauseButton.setOpaque(false);
        this.pauseButton.setContentAreaFilled(false);
        this.pauseButton.setBorderPainted(false);
        this.pauseButton.setFocusable(false);
        this.pauseButton.addActionListener(e -> pauseGame() );
        this.paneel.add(pauseButton);
        this.paneel.add(pauseLabel);
        this.isGameRunning = true;
        //Initialisiere den Timer und die dazugehörigen Labels
        this.timerLabel.setBounds(400, 210, 300, 30);
        this.paneel.add(timerLabel);
        this.sekunden = 0;
        this.minuten = 0;
        this.stunden = 0;
        //Liste der Spieler wird initialisiert
        spielerlist = new ArrayList<>();
        try {
            FileInputStream filestream = new FileInputStream(System.getProperty("user.home") + "/Project-Java-Tetris-high.score");
            ObjectInputStream objectstream = new ObjectInputStream(filestream);

            spielerlist = (List<Spieler>) objectstream.readObject();

            objectstream.close();
            filestream.close();
        } catch (IOException e) {
            e.printStackTrace();
            spielerlist.add(new Spieler("Ja!", 100000, "99:99:99"));
            spielerlist.add(new Spieler("OG LOC", 50000, "50:50:50"));
            spielerlist.add(new Spieler("Kollegah", 45000, "69:69:69"));
            spielerlist.add(new Spieler("The Rock", 44500, "68:68:68"));
            spielerlist.add(new Spieler("Yo Oli", 15650, "01:01:01"));
        }
        catch (ClassNotFoundException e) {
            System.out.println("Konnte Klasse nicht finden...");
            e.printStackTrace();
        }

        //Initialisiere die TOP 5 besten Spieler
        this.scoreTitel.setBounds(400, 380, 300, 30);
        this.scoreTitel.setText("Die 5 besten Spieler");
        this.scoreTitel.setFont(scoreTitel.getFont().deriveFont(20.0f));
        this.paneel.add(scoreTitel);
        this.scores[0] = new JLabel();
        this.scores[1] = new JLabel();
        this.scores[2] = new JLabel();
        this.scores[3] = new JLabel();
        this.scores[4] = new JLabel();
        this.scores[0].setBounds(400, 410, 370, 30);
        this.scores[1].setBounds(400, 440, 370 ,30);
        this.scores[2].setBounds(400, 470, 370 ,30);
        this.scores[3].setBounds(400, 500, 370, 30);
        this.scores[4].setBounds(400, 530, 370 ,30);

        this.paneel.add(scores[0]);
        this.paneel.add(scores[1]);
        this.paneel.add(scores[2]);
        this.paneel.add(scores[3]);
        this.paneel.add(scores[4]);

        best5Players();
        /*
        this.bl = this.blockList.get(blockList.size() - 1);
        this.bl.addKeyListener(tastenDruck);
        this.bl.requestFocus();
        this.paneel.add(bl);
        */

       // List<Image> icons = new ArrayList<Image>();
      //  icons.add(loadIcon(""))

        try {
            spielfeld.setIconImage(loadImage("/texture/icon64.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Füge die Hintergründe für das Spielfeld ein
        this.hintergrund.setIcon(loadIcon("/background/background.png"));
        this.hintergrund.setBounds(0, 0, 950, 617);
       //this.background.setIcon(new ImageIcon(Class.class.getResource("/background/1.png")));
       //this.background.setLocation(75, 50);
       //this.background.setSize(250, 500);
       //this.nextBackground.setIcon(new ImageIcon(Class.class.getResource("/background/1b.png")));
       //this.nextBackground.setBounds(400,50, 150,150);
        //Verschiebe sie in den Hintergrund damit sie das Spielfeld nicht verdecken
        this.paneel.add(hintergrund);
        this.paneel.setLayer(hintergrund, 0);
      //  this.paneel.add(background);
      //  this.paneel.setLayer(background, 1);
      //  this.paneel.add(nextBackground);
      //  this.paneel.setLayer(nextBackground, 1);
        //Füge die Inhalte dem Frame hinzu
        this.spielfeld.add(pan);
        this.spielfeld.add(paneel);
        this.spielfeld.setVisible(true);
        this.spielfeld.addKeyListener(tastenDruck);
        this.spielfeld.requestFocus();
        //Füge den Spiellogik Action Listener dem Timer hinnzu und starte ihn
        this.timer1.addActionListener(alGameStart);
        this.timer1.start();
        this.timerVergangeneZeit.start();
       //this.gameRunning = true;
       //runningGame();
    }
    // GETTER UND SETTER
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

    //Wenn eine Taste gedrückt
    private void tasteGedrueckt(int taste) {
        //Führe nur aus wenn der Tetruino aktiv ist
        if (tetrus.isActive() && isGameRunning) {
            //System.out.println(taste);
            //Switch Case für die einzelnen Tasten die für die Steuerung genutzt werden und bewege den Tetruino wenn position frei ist
            switch (taste) {

                //Pfeiltaste links, versuche Tetruino nach links zu bewegen
                case 37:
                    if ( /*bl.getPosX() > 60 && */ isNextTetruinoPositionFree(tetrus, 'l')) tetrus.move('l');
                    break;

                //Pfeiltaste rechts, versuche Tetruino nach rechts zu bewegen
                case 39:
                    if (/*bl.getPosX() < 275 && */ isNextTetruinoPositionFree(tetrus, 'r')) tetrus.move('r');
                    break;

                //Pfeiltaste runter, versuche Tetruino nach unten zu bewegen
                case 40:
                    if (isNextTetruinoPositionFree(tetrus, 'd')) tetrus.move('d');
                    break;

                //X Taste, versuche Tetruino im Uhrzeigersinn zu drehen
                case 88:
                    tetrus.rotate('x');
                    break;

                //+ Taste erhöht das Level
                case 521:
                    if (level < 10) increaseLevel();
                    break;

                //Y Taste, versuche Tetruino gegen den Uhrzeigersinn zu drehen
                // case 89: tetruinos.get(0).rotate('y');
                //    break;
            }
        }
        if (taste == 32) pauseButton.doClick();
        else if (taste == 82) {
            timer1.stop();
            timerVergangeneZeit.stop();
            int result = JOptionPane.showConfirmDialog(spielfeld, "Willst du das Spiel wirklich neustarten?");
            if (result == JOptionPane.YES_OPTION) {
                if (!isGameRunning) pauseButton.doClick();
                neuStart();
            }
            else {
                if (!isGameRunning) pauseButton.doClick();
                timer1.start();
                timerVergangeneZeit.start();
            }
        }
    }

    //Initialisiere die 5 besten Spieler Labels
    private void best5Players() {
        for (int i = 0; i < 5; i++) {
            Spieler s = spielerlist.get(i);
            scores[i].setText((i + 1) + ". Platz: " + s.getName() + ",    Punktzahl: " + s.getPunktzahl() + ",    Zeit: " + s.getZeit());
        }
    }

    //Pausiere das Spiel und setze es wieder fort
    private void pauseGame() {
        if (isGameRunning) {
            isGameRunning = false;
            pauseButton.setIcon(loadIcon("/button_icons/pause_button.png"));
            timer1.stop();
            timerVergangeneZeit.stop();
        }
        else {
            isGameRunning = true;
            pauseButton.setIcon(loadIcon("/button_icons/play_button.png"));
            timer1.start();
            timerVergangeneZeit.start();
        }
    }

    //Sichere die spielerliste auf die Festplatte
    private void saveSpielerlistToDisk() {
        try {
            FileOutputStream filestream = new FileOutputStream(System.getProperty("user.home") + "/Project-Java-Tetris-high.score");
            ObjectOutputStream outputstream = new ObjectOutputStream(filestream);
            outputstream.writeObject(spielerlist);
            outputstream.close();
            filestream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Füge den aktuellen Spieler auf der spielerliste hinzu
    private void addSpielerToList() {
        boolean namePassend = false;
        String name = "";
        do {
            name = JOptionPane.showInputDialog(spielfeld, "Gib deinen Namen ein! \n" +
                    "(Dieser sollte mindestens aus 2 Zeichen bestehen und nicht nur aus Leerzeichen bestehen)");

            if (name != null) {
                if (name.length() >= 2 && name.length() <= 20) namePassend = true;
            }
        } while (!namePassend);
        int i = 0;
        boolean existiertSpielerBereits = false;
        //Prüfe nach, ob der Spieler bereits existiert und ob die neue Punktzahl größer ist als die alte
        for (Spieler s: spielerlist) {
            if (s.getName().equals(name)) {
                if (s.getPunktzahl() < score) {
                    existiertSpielerBereits = true;
                    break;
                }
                //Wenn der Name gleich ist, aber die neue Punktzahl kleiner ist als die alte ist die Punktzahl schlecht und soll nicht gespeichert werden
                else {
                    System.out.println("Spielstand abgelehnt! Spiel besser du Versager!!");
                    return;
                }
            }
            i++;
        }
        //Wenn Spieler bereits existiert soll sein Spielstand überschrieben werden
        if (existiertSpielerBereits) {
            spielerlist.remove(i);
            System.out.println("Spieler überschrieben.");
        }
        //String vergangeneZeit = stunden + ":" + minuten + ":" + sekunden;
        String[] vergangeneZeit = timerLabel.getText().split("Vergangene Zeit: ");
        spielerlist.add(new Spieler(name, score, vergangeneZeit[1]));
        Collections.sort(spielerlist, Comparator.comparing(Spieler::getPunktzahl).reversed());

    }

    //Gibt ein Icon zurück
    private ImageIcon loadIcon(String path) {
        return new ImageIcon(Class.class.getResource(path));
    }

    //Gibt ein Image zurück
    private Image loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResource(path));
    }

    //Methode um das Spiel neuzustarten
    private void neuStart() {
        removeAllBlocks();
        this.tetruinos = new ArrayList<>();
        gamespeed = 500;
        counter = 0;
        level = 1;
        rows = 0;
        score = 0;
        sekunden = 0;
        minuten = 0;
        stunden = 0;
        timerLabel.setText("Vergangene Zeit: 00:00:00");
        updateGame();
        best5Players();
        spielfeld.repaint();
        spielfeld.revalidate();
        this.addTetruino();
        this.addTetruino();
        //Erstelle aktuellen Tetruino
        this.tetrus = tetruinos.get(0);
        this.tetrus.addToPanel(paneel, tetrus);
        this.tetrus.setActive(true);
        // this.tetrus.tetruBlocks.get(0).addKeyListener(tastenDruck);
        //Erstelle nächsten Tetruino und bewege ihn in das andere Feld
        this.nextTretrus = tetruinos.get(1);
        this.nextTretrus.addToPanel(paneel, nextTretrus);
        this.nextTretrus.move('n');
        this.nextTretrus.setActive(true);
        //this.spielfeld.addKeyListener(tastenDruck);
        this.spielfeld.requestFocus();
        timer1.start();
        timerVergangeneZeit.start();
        for (Spieler s: spielerlist) {
            s.print();
        }
    }

    //Methode um die Hintergrundbilder mittels der Radio Buttons zu ändern
    private void changeBackgrounds(int number) {
        background.setIcon(new ImageIcon(Resource.class.getResource("/background/" + number + ".png")));
        nextBackground.setIcon(new ImageIcon(Resource.class.getResource("/background/" + number + "b.png")));
    }

    //Versucht die Spielgeschwindigkeit zu optimieren
    //Zählt jede Millisekunde hoch und messe damit die Zeit die benötigt wurde um den Code auszuführen
    //und verringere die Wartezeit des Timeres um die vergangene Zeit um das Ruckeln etwas in den Griff zu bekommen
    private void threadCount() {
        Thread thread = new Thread(() -> {
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
        thread.start();
    }

    //Erhöhe das Spiellevel
    private void increaseLevel() {
        //Erhöhe nur wenn Reihe folgende Zahlen enthält, damit das Level nur einmalig erhöt wird
       /* if (rows == 9 || rows == 19 || rows == 29 || rows == 39 || rows == 49 || rows == 59 || rows == 69 || rows == 79
            || rows == 89 || rows == 99) { */
            gamespeed -= 35;
            level++;
       // }
    }

    //Aktuallisiere die Anzeigen wie Level, Reihe etc
    private void updateGame() {
        if (rows >= 10 && level == 1) increaseLevel();
        else if (rows >= 20 && level == 2) increaseLevel();
        else if (rows >= 30 && level == 3) increaseLevel();
        else if (rows >= 40 && level == 4) increaseLevel();
        else if (rows >= 50 && level == 5) increaseLevel();
        else if (rows >= 60 && level == 6) increaseLevel();
        else if (rows >= 70 && level == 7) increaseLevel();
        else if (rows >= 80 && level == 8) increaseLevel();
        else if (rows >= 90 && level == 9) increaseLevel();
        // Tetris hat eigentlich nur 10 Level !
        //else if (rows >= 100 && level == 10) increaseLevel();

        levelLabel.setText("Level: " + level);
        rowLabel.setText("Reihen: " + rows);
        scoreLabel.setText("Punkte: " + score);
    }

    //Füge einen neuen zufälligen Tetruino hinzu
    private void addTetruino() {
        Random rand = new Random();
        int random = rand.nextInt(100);
        if (random == 31) this.tetruinos.add(new Ultimatus(isMinimalistic));
        else {
            rand = new Random();
            random = rand.nextInt(60);
            if (random == 13) this.tetruinos.add(new Pingas(isMinimalistic));
            else {
                rand = new Random();
                random = rand.nextInt(7);
                switch (random) {
                    case 0:
                        this.tetruinos.add(new Ess(isMinimalistic));
                        break;

                    case 1:
                        this.tetruinos.add(new Zett(isMinimalistic));
                        break;

                    case 2:
                        this.tetruinos.add(new Lawliet(isMinimalistic));
                        break;

                    case 3:
                        this.tetruinos.add(new Lelouch(isMinimalistic));
                        break;

                    case 4:
                        this.tetruinos.add(new Quaddroino(isMinimalistic));
                        break;

                    case 5:
                        this.tetruinos.add(new Tilt(isMinimalistic));
                        break;

                    case 6:
                        this.tetruinos.add(new Longinus(isMinimalistic));
                        break;
                }
            }
        }
        // Wird das überhaupt noch benötigt ?!
        tetruinos.get(tetruinos.size() - 1).tetruBlocks.get(0).addFocusListener(focus);
    }

    //Zerstört alle vollständigen Reihen
    private void destroyBlocks() {
        if (tetrus.getName() == 'P') asUsualISee();
            //Halte Timer an
            timer1.stop();
            //Wenn mehrere Reihen aufeinmal zerstört werden soll man auch mehr Punkte bekommen!
            int multiplikator = 1;
            //Gehe jedes Feld durch
            //vertikale Reihen
            for (int i = 550; i >= 50; i -= 25) {
                //Zähle die Anzahl der Blöcke in der horizontalen Reihe
                byte anzBlocks = 0;
                List<Block> line = new ArrayList<>();
                for (Tetruino t: tetruinos) {
                    //Zähle nur mit wenn Tetruino nicht aktiv ist
                    if (!t.isActive()) {
                        for (Block blocks : t.tetruBlocks) {
                            //Wenn Position des Tetruinos gleich der der Reihe zähle hoch
                            if (blocks.getPosY() == i) {
                                anzBlocks++;
                                line.add(blocks);
                                //System.out.println(anzBlocks);
                            }
                            if (tetrus.getName() == 'U') {
                                if (blocks.getPosX() == tetrus.tetruBlocks.get(0).getPosX()) {
                                    line.add(blocks);
                                }
                                if (blocks.getPosY() == tetrus.tetruBlocks.get(0).getPosY()) {
                                    line.add(blocks);
                                }
                            }
                        }
                    }
                }
                //Wenn Anzahl der Blöcke größer oder gleich 10
                if (anzBlocks >= 10) {
                    //Lösche Reihen auf der Höhe i
                    removeLine(line);
                    //Bewege Reihen nach unten auf der Höhe i
                    moveLine(i);
                    //Erhöhe i um 25 damit diese Reihe nochmal geprüft wird, da ja die Reihen nach unten gerutscht sind
                    i += 25;
                    //Erhöhe Multiplikator
                    multiplikator++;
                    //Zeichne das Spielfeld neu
                   // spielfeld.repaint();
                   // spielfeld.revalidate();
                    //Erhöhe Score und Reihen
                    score += 100 * level * multiplikator;
                    rows++;

                }
                //Wenn Tetruino ein Ultimatus ist
                else if (tetrus.getName() == 'U') {
                    int height = tetrus.tetruBlocks.get(0).getPosY();
                    int width = tetrus.tetruBlocks.get(0).getPosX();
                    explosionUltimatus(width, height);
                    removeLine(line);
                    if (tetruinos.size() > 0) moveLine(height);
                    score += 100 * level * 4;
                    rows++;

                    break;
                }
                //if (multiplikator == 4) boomBitch();
            }
        //Starte wieder den Timer damit das Spiel fortgesetzt werden kann
        timer1.start();
    }

    //Bewege alle Reihen mit der übergebenen Höhe nach unten
    private void moveLine(int height) {
        int size = tetruinos.size() - 1;
        int counter = 0;
        for (Tetruino t: tetruinos) {
            if (!t.isActive() && counter < size )
            for (Block b: t.tetruBlocks) {
                if (b.getPosY() < height) {
                    b.setLocation(b.getPosX(), b.getPosY() + 25);
                }
            }
            counter++;
        }
    }

    //Entferne alle Tetruinos vom Spielfeld für den neustart
    private void removeAllBlocks() {
        for (Tetruino t: tetruinos) {
            for (Block b: t.tetruBlocks) {
                paneel.remove(b);
            }
        }
    }

    //Entferne Reihen mit der übergebenen Höhe
    private void removeLine(/*int height*/ List<Block> liste) {
            //Lege zwei neue Arrays an um die zu löschenden Blöcke zwischen zu speichern
            List<Block> blockList = new ArrayList<>();
            //Führe nur aus wenn Blockliste nicht leer
            if (!liste.isEmpty()) {
                //Gehe alle Blöcke in der Liste durch
                for (Block b : liste) {
                    //Gehe alle Tetruinos bis auf den aktiven durch
                    for (Tetruino t : tetruinos) {
                        if (!t.isActive()) {
                            //Füge eine Explosion an die Stelle des Block
                            explosion(b.getPosX(), b.getPosY());
                            //Entferne den Block vom Paneel
                            paneel.remove(b);
                            //Wentferne den Block aus der Liste des Tetruinos
                            t.tetruBlocks.remove(b);
                            //Warte 5 Millisekunden um, damit dem Zuschauer das Spektakel nicht entgeht
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // System.out.println("Block entfernt!");
                        }
                    }
                }
            }
        List<Tetruino> tetruinoList = new ArrayList<>();
        for (Tetruino t : tetruinos) {
            if (!t.isActive()) {
                //Wenn Liste des Tetruinos leer ist, füge diesen auf TetruinoList hinzu
                if (t.tetruBlocks.isEmpty()) tetruinoList.add(t);
            }
        }
            //Wenn TetruinoList nicht leer
            if (!tetruinoList.isEmpty()) {
                //Entferne noch die Tetriunos aus der Liste wenn sie keine Blöcke mehr enthalten
                for (Tetruino t : tetruinoList) {
                    tetruinos.remove(t);
                }
            }
       // System.out.println("Tetruinos Größe: " + tetruinos.size());
    }

     // Quelle für Explosionsanimation https://rs76.pbsrc.com/albums/j39/Puto_Gonci/explosion.gif~c200
    //Starte eine Explosionsanimation an den übergebenen Koordinaten
    private void explosion(int xPos, int yPos) {
        //Starte einen Thread damit das Hauptprogramm nicht stehen bleibt
        Thread thread = new Thread(() -> {
            //Füge ein neues Label hinzu auf das Paneel im Vordergrund
            JLabel exlabel = new JLabel();
            pan.add(exlabel);
            //pan.setLayer(exlabel, 2);
            exlabel.setSize(25,25);
            exlabel.setLocation(xPos, yPos);
            //Lade die Bilder der Explosion um eine flüssige Animation zu gewährleisten
            for (int i = 1; i <= 17; i++) {
                exlabel.setIcon(new ImageIcon(Class.class.getResource("/explosion/" + i + ".png")));
                try {
                    Thread.sleep(44);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Entferne das Label nach der Animation wieder
            pan.remove(exlabel);
            spielfeld.repaint();
            spielfeld.revalidate();
            //Beende den Thread damit der Threadspeicher nicht voll läuft
            Thread.currentThread().interrupt();
            return;
        });
        thread.start();
    }

    //Wenn ein Ultimatus hochgeht, soll sich die Explosion in alle Richtungen ausbreiten
    private void explosionUltimatus(int x, int y) {
        //Hoch
        if (y > 50) {
            Thread up = new Thread(() -> {
                for (int i = y - 25; i >= 50; i -= 25) {
                    explosion(x, i);
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            up.start();
        }
        //Runter
        if (y < 530) {
            Thread down = new Thread(() -> {
                for (int i = y + 25; i <= 530; i += 25) {
                    explosion(x, i);
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            down.start();
        }
        //Links
        if (x > 60) {
            Thread left = new Thread(() -> {
                for (int i = x - 25; i >= 60; i -= 25) {
                    explosion(i, y);
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            left.start();
        }
        //Rechts
        if (x < 300) {
            Thread right = new Thread(() -> {
                for (int i = x + 25; i <= 300; i += 25) {
                    explosion(i, y);
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            right.start();
        }
    }

    //PINGAS !!!!
    private void asUsualISee() {
        Thread snoopy = new Thread(() -> {
            JLabel pingasLabel = new JLabel();
            pan.add(pingasLabel);
            pingasLabel.setBounds(125, 50, 224, 286);
            pingasLabel.setIcon(loadIcon("/texture/asusual.png"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pan.remove(pingasLabel);
            spielfeld.repaint();
            spielfeld.revalidate();
            Thread.currentThread().interrupt();
        });
        snoopy.start();
    }

    //Wenn der Multiplikator 4 erreicht hat, soll dies mit einer fetten Explosion symbolisiert werden
    // Wirkt doch zu überladen und zieht das Spieltempo zu weit herunter
    /*private void boomBitch() {
        Thread thread = new Thread(() -> {
            JLabel exlabel = new JLabel();
            pan.add(exlabel);
            exlabel.setSize(720,405);
            exlabel.setLocation(0, 200);

            for (int i = 1; i <= 30; i++) {
                exlabel.setIcon(new ImageIcon(Class.class.getResource("/big_explosion/" + i + ".png")));
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            pan.remove(exlabel);
            spielfeld.repaint();
            spielfeld.revalidate();
            Thread.currentThread().interrupt();
            return;
        });
        thread.start();
    } */


    //Methode die Prüft ob die nächste Postion des Blocks frei ist
    private boolean isPositionFree(char direction, Block b) {
        //Gehe durch die Tetruinos durch und die darin enthaltenen Blöcke, dann prüfe die Positionen
        //Wenn nicht frei beende durchlauf und gebe false zurück
        switch (direction) {
            //DOWN
            case 'd':
                for (Tetruino t: tetruinos) {
                    for (Block bl: t.tetruBlocks) {
                        if (b.getPosY() + 25 == bl.getPosY() && b.getPosX() == bl.getPosX() || b.getPosY() + 25 > 530) {
                            return false;
                        }
                    }
                }
                break;
            //LEFT
            case 'l':
                for (Tetruino t: tetruinos) {
                    for (Block bl: t.tetruBlocks) {
                        if (b.getPosX() - 25 == bl.getPosX() && b.getPosY() == bl.getPosY() || b.getPosX() - 25 < 60) {
                            return false;
                        }
                    }
                }
                break;
            //RIGHT
            case 'r':
                for (Tetruino t: tetruinos) {
                    for (Block bl: t.tetruBlocks) {
                        if (b.getPosX() + 25 == bl.getPosX() && b.getPosY() == bl.getPosY() || b.getPosX() + 25 > 300) {
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    //Frage die einzelnen Blöcke der Tetruinos ab
    private boolean isNextTetruinoPositionFree(Tetruino t, char direction) {
        if (t.isActive() ) {
            //Prüfe die Blöcke der Tetruinos durch ob die nächste Position frei ist
            switch (t.getName()) {
                //Lawliet
                case 'l':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                            }
                            break;

                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(2)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;

                        case 3:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                            }
                            break;

                        case 4:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(2)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Lelouch
                case 'L':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                            }
                            break;

                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(2)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;

                        case 3:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                            }
                            break;

                        case 4:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Quadroino
                case 'Q':
                    switch (direction) {
                        //DOWN
                        case 'd':
                            if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                return true;
                            break;
                        //LEFT
                        case 'l':
                            if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(2)))
                                return true;
                            break;
                        //RIGHT
                        case 'r':
                            if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                return true;
                            break;
                    }
                    break;
                //Longinus
                case 'I':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;

                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Ess
                case 'S':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(2)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;

                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(0))
                                            && isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Zett
                case 'Z':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(2)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;

                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Tilt
                case 'T':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3)))
                                        return true;
                                    break;
                            }
                            break;

                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                            }
                            break;

                        case 3:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(0))
                                            && isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(0)))
                                        return true;
                                    break;
                            }
                            break;

                        case 4:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1)))
                                        return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(1))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(1)) && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(3))) return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Pingas
                case 'P':
                    switch (t.getRotation()) {
                        case 1:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(4))
                                            && isPositionFree(direction, t.tetruBlocks.get(5))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(4))
                                            && isPositionFree(direction, t.tetruBlocks.get(5))) return true;
                                    break;
                            }
                            break;
                        case 2:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(2)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(4))
                                            && isPositionFree(direction, t.tetruBlocks.get(5))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(5))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                            }
                            break;
                        case 3:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(5))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(3)) && isPositionFree(direction, t.tetruBlocks.get(4))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))
                                            && isPositionFree(direction, t.tetruBlocks.get(5))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(4))
                                            && isPositionFree(direction, t.tetruBlocks.get(5))) return true;
                                    break;
                            }
                            break;
                        case 4:
                            switch (direction) {
                                //DOWN
                                case 'd':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(3))
                                            && isPositionFree(direction, t.tetruBlocks.get(4))
                                            && isPositionFree(direction, t.tetruBlocks.get(5))) return true;
                                    break;
                                //LEFT
                                case 'l':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(5))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                                //RIGHT
                                case 'r':
                                    if (isPositionFree(direction, t.tetruBlocks.get(0)) && isPositionFree(direction, t.tetruBlocks.get(1))
                                            && isPositionFree(direction, t.tetruBlocks.get(2))) return true;
                                    break;
                            }
                            break;
                    }
                    break;
                //Ultimatus
                case 'U':
                    switch (direction) {
                        //DOWN
                        case 'd':
                            if (isPositionFree(direction, t.tetruBlocks.get(0)))
                                return true;
                            break;
                        //LEFT
                        case 'l':
                            if (isPositionFree(direction, t.tetruBlocks.get(0))) return true;
                            break;
                        //RIGHT
                        case 'r':
                            if (isPositionFree(direction, t.tetruBlocks.get(0))) return true;
                            break;
                    }
                    break;
            }
        }
        return false;
    }
}