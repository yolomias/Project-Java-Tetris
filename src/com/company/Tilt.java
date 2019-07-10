//Der T förmige Tetruino
/*
        0 *
    1 * 2 * 3 *


 */

package com.company;

import java.awt.*;

//Initialisiert den Tetriuno
//Die anderen Klassen werden nicht erläutert, weil diese bis auf die Positionen und Namen gleich sind
public class Tilt extends Tetruino {

    //Konstruktor
    public Tilt(boolean minimalstic) {
        this.setRotation(1);
        this.setAmount(4);
        this.setName('T');
        for (int i = 0; i < getAmount(); i++) {
            Block block = new Block(Color.YELLOW, minimalstic);
            int number = i;
            block.addActionListener(e -> System.out.println("Block Nr: " + number));
            tetruBlocks.add(block);
        }
        platziereBlocks();
    }

    //Platziere die Blöcke so dass es aussieht wie im Original
    @Override
    protected void platziereBlocks() {
        tetruBlocks.get(1).setLocation(125, 75);
        tetruBlocks.get(2).setLocation(150, 75);
        tetruBlocks.get(3).setLocation(175, 75);
    }

    /*
        1:  *    2:  *     3:         4:  *
          * * *      * *      * * *     * *
                     *          *         *

     */

    //Rotiere den Block in dem die Positionen der Blöcke verschoben wurden
    @Override
    public void rotate(char rotation) {
        //X Taste für drehen im Uhrzeigersinn Y für gegen den Uhrzeigersinn
        if (rotation == 'x') {
            if (getRotation() >= 4) setRotation(1);
            else setRotation(getRotation() + 1);
        } /* else if (rotation == 'y') {
            if (getRotation() <= 1) setRotation(4);
            else setRotation(getRotation() - 1);
        } */

        //Passe die Positionen an
        switch (getRotation()) {
            case 1:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() + 25, tetruBlocks.get(0).getPosY() - 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() - 25, tetruBlocks.get(1).getPosY() - 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX(), tetruBlocks.get(2).getPosY());
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() + 25, tetruBlocks.get(3).getPosY() + 25);
                break;

            case 2:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() + 25, tetruBlocks.get(0).getPosY() + 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() + 25, tetruBlocks.get(1).getPosY() - 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX(), tetruBlocks.get(2).getPosY());
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() - 25, tetruBlocks.get(3).getPosY() + 25);
                break;

            case 3:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() - 25, tetruBlocks.get(0).getPosY() + 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() + 25, tetruBlocks.get(1).getPosY() + 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX(), tetruBlocks.get(2).getPosY());
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() - 25, tetruBlocks.get(3).getPosY() - 25);
                break;

            case 4:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() - 25, tetruBlocks.get(0).getPosY() - 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() - 25, tetruBlocks.get(1).getPosY() + 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX(), tetruBlocks.get(2).getPosY());
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() + 25, tetruBlocks.get(3).getPosY() - 25);
                break;
        }
    }
}
