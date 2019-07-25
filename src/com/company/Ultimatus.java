//Ein spezialer Tetruino, der alle Tetruinos in der Reihe und Spalte vernichtet
/*
        0 *
 */

package com.company;

import java.awt.*;

//Initialisiert den Tetriuno
//Die anderen Klassen werden nicht erläutert, weil diese bis auf die Positionen und Namen gleich sind
public class Ultimatus extends Tetruino {
    private byte colorCounter;

    //Konstruktor
    public Ultimatus(boolean minimalstic) {
        this.setRotation(1);
        this.setAmount(1);
        this.setName('U');
        this.colorCounter = 0;
        for (int i = 0; i < getAmount(); i++) {
            Block block = new Block(Color.YELLOW, minimalstic);
            int number = i;
            block.addActionListener(e -> System.out.println("Block Nr: " + number));
            tetruBlocks.add(block);
        }
        colourChanger();
        platziereBlocks();
    }

    //Platziere die Blöcke so dass es aussieht wie im Original
    @Override
    protected void platziereBlocks() {

    }

    //Rotiere den Block in dem die Positionen der Blöcke verschoben wurden
    @Override
    public void rotate(char rotation) {

    }

    //Verändert ständig die Farbe des Tetruinos
    private void colourChanger() {
        Thread epilepsy = new Thread(() -> {
            Block block = this.tetruBlocks.get(0);
            super.setActive(true);
            while (super.isActive()) {
                switch (colorCounter) {
                    case 0:
                        block.setBackground(Color.decode("#ff70cb"));
                        colorCounter++;
                        break;
                    case 1:
                        block.setBackground(Color.decode("#69e8ff"));
                        colorCounter++;
                        break;
                    case 2:
                        block.setBackground(Color.decode("#52ff5e"));
                        colorCounter++;
                        break;
                    case 3:
                        block.setBackground(Color.decode("#fff952"));
                        colorCounter = 0;
                        break;
                }
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toolkit.getDefaultToolkit().sync();
            }
        });
        epilepsy.start();
    }
}
