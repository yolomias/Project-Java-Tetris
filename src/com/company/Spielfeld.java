//Klasse Spielfeld enthält das Spielfeld und all seine Elemente so wie deren Logik
package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    //Hintergrund des Feldes indem sich die Tetruinos bewegen
    private JLabel background = new JLabel("");
    private JLabel nextBackground = new JLabel("");
    //Wird für die Methode ThreadCount benötigt
    private boolean threadRunning = true;
    //Wird in dieser Version gerade nicht mehr benötigt
    private boolean gameRunning;

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
                if (!isNextTetruinoPositionFree(tetrus, 'd') && tetrus.tetruBlocks.get(0).getPosY() == 50) {
                    timer1.stop();
                    JOptionPane.showMessageDialog(spielfeld, "Das Spiel ist vorbei, du hast verloren!");
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
                // System.out.println("Counter: " + counter);
            });
            //Starte den Spiel Thread
            thread.start();
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
        //Erstelle beide Paneele für den Vordergrund und Hintergrund
        this.paneel = new JLayeredPane();
        this.paneel.setLayout(null);
        this.pan = new JPanel();
        this.pan.setBounds(0, 0, getX(), getY());
        this.pan.setFocusable(false);
        this.pan.setOpaque(false);
        this.pan.setLayout(null);
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

        /*
        this.bl = this.blockList.get(blockList.size() - 1);
        this.bl.addKeyListener(tastenDruck);
        this.bl.requestFocus();
        this.paneel.add(bl);
        */
        //Füge die Hintergründe für das Spielfeld ein
        this.background.setIcon(new ImageIcon(Class.class.getResource("/background/1.png")));
        this.background.setLocation(75, 50);
        this.background.setSize(250, 500);
        this.nextBackground.setIcon(new ImageIcon(Class.class.getResource("/background/1b.png")));
        this.nextBackground.setBounds(400,50, 150,150);
        //Verschiebe sie in den Hintergrund damit sie das Spielfeld nicht verdecken
        this.paneel.add(background);
        this.paneel.setLayer(background, 0);
        this.paneel.add(nextBackground);
        this.paneel.setLayer(nextBackground, 0);
        //Füge die Inhalte dem Frame hinzu
        this.spielfeld.add(pan);
        this.spielfeld.add(paneel);
        this.spielfeld.setVisible(true);
        this.spielfeld.addKeyListener(tastenDruck);
        this.spielfeld.requestFocus();
        //Füge den Spiellogik Action Listener dem Timer hinnzu und starte ihn
        this.timer1.addActionListener(alGameStart);
        this.timer1.start();
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
        if (tetrus.isActive() ) {
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

                //Y Taste, versuche Tetruino gegen den Uhrzeigersinn zu drehen
                // case 89: tetruinos.get(0).rotate('y');
                //    break;
            }
        }
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
        updateGame();
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
        //this.spielfeld.addKeyListener(tastenDruck);
        this.spielfeld.requestFocus();
        timer1.start();
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
        else if (rows >= 100 && level == 10) increaseLevel();

        levelLabel.setText("Level: " + level);
        rowLabel.setText("Reihen: " + rows);
        scoreLabel.setText("Punkte: " + score);
    }

    //Füge einen neuen zufälligen Tetruino hinzu
    private void addTetruino() {
        Random rand = new Random();
        int random = rand.nextInt(7);
        switch (random) {
            case 0:
                this.tetruinos.add(new Ess());
                break;

            case 1:
                this.tetruinos.add(new Zett());
                break;

            case 2:
                this.tetruinos.add(new Lawliet());
                break;

            case 3:
                this.tetruinos.add(new Lelouch());
                break;

            case 4:
                this.tetruinos.add(new Quaddroino());
                break;

            case 5:
                this.tetruinos.add(new Tilt());
                break;

            case 6:
                this.tetruinos.add(new Longinus());
                break;
        }
        // Wird das überhaupt noch benötigt ?!
        tetruinos.get(tetruinos.size() - 1).tetruBlocks.get(0).addFocusListener(focus);
    }

    //Zerstört alle vollständigen Reihen
    private void destroyBlocks() {
        //Halte Timer an
        timer1.stop();
        //Wenn mehrere Reihen aufeinmal zerstört werden soll man auch mehr Punkte bekommen!
        int multiplikator = 1;
        //Gehe jedes Feld durch
        //vertikale Reihen
        for (int i = 550; i >= 50; i -= 25) {
            //horizontale Reihen
            //Zähle die Anzahl der Blöcke in der horizontalen Reihe
            byte anzBlocks = 0;
            for (int j = 50; j <= 300; j += 25) {
                for (Tetruino t: tetruinos) {
                    //Zähle nur mit wenn Tetruino nicht aktiv ist
                    if (!t.isActive() ) {
                        for (Block blocks : t.tetruBlocks) {
                            //Wenn Position des Tetruinos gleich der der Reihe zähle hoch
                            if (blocks.getPosX() == j && blocks.getPosY() == i) {
                                anzBlocks++;
                                //System.out.println(anzBlocks);
                            }
                        }
                    }
                }
            }
            //Wenn Anzahl der Blöcke gleich 10
            if (anzBlocks == 10) {
                //Lösche Reihen auf der Höhe i
                removeLine(i);
                //Bewege Reihen nach unten auf der Höhe i
                moveLine(i);
                //Erhöhe i um 25 damit diese Reihe nochmal geprüft wird, da ja die Reihen nach unten gerutscht sind
                i += 25;
                //Erhöhe Multiplikator
                multiplikator++;
                //Zeichne das Spielfeld neu
                spielfeld.repaint();
                spielfeld.revalidate();
                //Erhöhe Score und Reihen
                score += 100 * level * multiplikator;
                rows++;

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
    private void removeLine(int height) {
        //Lege zwei neue Arrays an um die zu löschenden Blöcke zwischen zu speichern
        List<Block> blockList = new ArrayList<>();
        List<Tetruino> tetruinoList = new ArrayList<>();
        for (Tetruino t: tetruinos) {
            if (!t.isActive() ) {
                //Wenn Liste des Tetruinos leer ist, füge diesen auf TetruinoList hinzu
                if (t.tetruBlocks.isEmpty()) tetruinoList.add(t);
                //Gehe jeden Block auf dem Tetruino durch und wenn höhe übereinstimmt füge ihn der Blockliste hinzu
                for (Block b : t.tetruBlocks) {
                    if (b.getPosY() == height) blockList.add(b);
                }
            }
        }
        //Führe nur aus wenn Blockliste nicht leer
        if (!blockList.isEmpty() ) {
            //Gehe alle Blöcke in der Liste durch
            for (Block b: blockList) {
                //Gehe alle Tetruinos bis auf den aktiven durch
                for (Tetruino t: tetruinos) {
                    if (!t.isActive() ) {
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
        //Wenn TetruinoList nicht leer
        if (!tetruinoList.isEmpty() ) {
            //Entferne noch die Tetriunos aus der Liste wenn sie keine Blöcke mehr enthalten
            for(Tetruino t: tetruinoList) {
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
            }
        }
        return false;
    }
}