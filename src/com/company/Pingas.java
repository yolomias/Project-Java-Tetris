// Snooping as usual I see ?!
/*
     5-14 *
        4 *
        3 *
    0 * 1 * 2 *


 */

package com.company;

import java.awt.*;

//Initialisiert den Tetriuno
//Die anderen Klassen werden nicht erläutert, weil diese bis auf die Positionen und Namen gleich sind
public class Pingas extends Tetruino {

    //Konstruktor
    public Pingas (boolean minimalstic) {
        this.setRotation(1);
        this.setAmount(15);
        this.setName('P');
        for (int i = 0; i < getAmount(); i++) {
            Block block = new Block(Color.PINK, minimalstic);
            int number = i;
            block.addActionListener(e -> System.out.println("Block Nr: " + number));
            tetruBlocks.add(block);
        }
        tetruBlocks.get(5).setBackground(Color.decode("#FF8A8A"));
        platziereBlocks();
    }

    //Platziere die Blöcke so dass es aussieht wie im Original
    @Override
    protected void platziereBlocks() {
        tetruBlocks.get(0).setLocation(125, 125);
        tetruBlocks.get(1).setLocation(150, 125);
        tetruBlocks.get(2).setLocation(175, 125);
        tetruBlocks.get(3).setLocation(150, 100);
        tetruBlocks.get(4).setLocation(150, 75);
        tetruBlocks.get(5).setLocation(150, 50);
        tetruBlocks.get(6).setLocation(150, 50);
        tetruBlocks.get(7).setLocation(150, 50);
        tetruBlocks.get(8).setLocation(150, 50);
        tetruBlocks.get(9).setLocation(150, 50);
        tetruBlocks.get(10).setLocation(150, 50);
        tetruBlocks.get(11).setLocation(150, 50);
        tetruBlocks.get(12).setLocation(150, 50);
        tetruBlocks.get(13).setLocation(150, 50);
        tetruBlocks.get(14).setLocation(150, 50);
    }

    /*
        1: *    2: *          3: * * *     4:        *
           *       * * * *         *           * * * *
           *       *               *                 *
         * * *                     *
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
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() - 75, tetruBlocks.get(0).getPosY() + 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() - 50, tetruBlocks.get(1).getPosY() + 50);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() - 25, tetruBlocks.get(2).getPosY() + 75);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() - 25, tetruBlocks.get(3).getPosY() + 25);
                tetruBlocks.get(4).setLocation(tetruBlocks.get(4).getPosX(), tetruBlocks.get(4).getPosY());
                tetruBlocks.get(5).setLocation(tetruBlocks.get(5).getPosX() + 25, tetruBlocks.get(5).getPosY() - 25);
                tetruBlocks.get(6).setLocation(tetruBlocks.get(6).getPosX() + 25, tetruBlocks.get(6).getPosY() - 25);
                tetruBlocks.get(7).setLocation(tetruBlocks.get(7).getPosX() + 25, tetruBlocks.get(7).getPosY() - 25);
                tetruBlocks.get(8).setLocation(tetruBlocks.get(8).getPosX() + 25, tetruBlocks.get(8).getPosY() - 25);
                tetruBlocks.get(9).setLocation(tetruBlocks.get(9).getPosX() + 25, tetruBlocks.get(9).getPosY() - 25);
                tetruBlocks.get(10).setLocation(tetruBlocks.get(10).getPosX() + 25, tetruBlocks.get(10).getPosY() - 25);
                tetruBlocks.get(11).setLocation(tetruBlocks.get(11).getPosX() + 25, tetruBlocks.get(11).getPosY() - 25);
                tetruBlocks.get(12).setLocation(tetruBlocks.get(12).getPosX() + 25, tetruBlocks.get(12).getPosY() - 25);
                tetruBlocks.get(13).setLocation(tetruBlocks.get(13).getPosX() + 25, tetruBlocks.get(13).getPosY() - 25);
                tetruBlocks.get(14).setLocation(tetruBlocks.get(14).getPosX() + 25, tetruBlocks.get(14).getPosY() - 25);
                break;

            case 2:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() , tetruBlocks.get(0).getPosY() - 75);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() - 25, tetruBlocks.get(1).getPosY() - 50);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() - 50, tetruBlocks.get(2).getPosY() - 25);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX(), tetruBlocks.get(3).getPosY() - 25);
                tetruBlocks.get(4).setLocation(tetruBlocks.get(4).getPosX() + 25, tetruBlocks.get(4).getPosY());
                tetruBlocks.get(5).setLocation(tetruBlocks.get(5).getPosX() + 50, tetruBlocks.get(5).getPosY() + 25);
                tetruBlocks.get(6).setLocation(tetruBlocks.get(6).getPosX() + 50, tetruBlocks.get(6).getPosY() + 25);
                tetruBlocks.get(7).setLocation(tetruBlocks.get(7).getPosX() + 50, tetruBlocks.get(7).getPosY() + 25);
                tetruBlocks.get(8).setLocation(tetruBlocks.get(8).getPosX() + 50, tetruBlocks.get(8).getPosY() + 25);
                tetruBlocks.get(9).setLocation(tetruBlocks.get(9).getPosX() + 50, tetruBlocks.get(9).getPosY() + 25);
                tetruBlocks.get(10).setLocation(tetruBlocks.get(10).getPosX() + 50, tetruBlocks.get(10).getPosY() + 25);
                tetruBlocks.get(11).setLocation(tetruBlocks.get(11).getPosX() + 50, tetruBlocks.get(11).getPosY() + 25);
                tetruBlocks.get(12).setLocation(tetruBlocks.get(12).getPosX() + 50, tetruBlocks.get(12).getPosY() + 25);
                tetruBlocks.get(13).setLocation(tetruBlocks.get(13).getPosX() + 50, tetruBlocks.get(13).getPosY() + 25);
                tetruBlocks.get(14).setLocation(tetruBlocks.get(14).getPosX() + 50, tetruBlocks.get(14).getPosY() + 25);
                break;

            case 3:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() + 50, tetruBlocks.get(0).getPosY());
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() + 25, tetruBlocks.get(1).getPosY() - 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX(), tetruBlocks.get(2).getPosY() - 50);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX(), tetruBlocks.get(3).getPosY());
                tetruBlocks.get(4).setLocation(tetruBlocks.get(4).getPosX() - 25, tetruBlocks.get(4).getPosY() + 25);
                tetruBlocks.get(5).setLocation(tetruBlocks.get(5).getPosX() - 50, tetruBlocks.get(5).getPosY() + 50);
                tetruBlocks.get(6).setLocation(tetruBlocks.get(6).getPosX() - 50, tetruBlocks.get(6).getPosY() + 50);
                tetruBlocks.get(7).setLocation(tetruBlocks.get(7).getPosX() - 50, tetruBlocks.get(7).getPosY() + 50);
                tetruBlocks.get(8).setLocation(tetruBlocks.get(8).getPosX() - 50, tetruBlocks.get(8).getPosY() + 50);
                tetruBlocks.get(9).setLocation(tetruBlocks.get(9).getPosX() - 50, tetruBlocks.get(9).getPosY() + 50);
                tetruBlocks.get(10).setLocation(tetruBlocks.get(10).getPosX() - 50, tetruBlocks.get(10).getPosY() + 50);
                tetruBlocks.get(11).setLocation(tetruBlocks.get(11).getPosX() - 50, tetruBlocks.get(11).getPosY() + 50);
                tetruBlocks.get(12).setLocation(tetruBlocks.get(12).getPosX() - 50, tetruBlocks.get(12).getPosY() + 50);
                tetruBlocks.get(13).setLocation(tetruBlocks.get(13).getPosX() - 50, tetruBlocks.get(13).getPosY() + 50);
                tetruBlocks.get(14).setLocation(tetruBlocks.get(14).getPosX() - 50, tetruBlocks.get(14).getPosY() + 50);
                break;

            case 4:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() + 25, tetruBlocks.get(0).getPosY() + 50);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() + 50, tetruBlocks.get(1).getPosY() + 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() + 75, tetruBlocks.get(2).getPosY());
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() + 25, tetruBlocks.get(3).getPosY());
                tetruBlocks.get(4).setLocation(tetruBlocks.get(4).getPosX(), tetruBlocks.get(4).getPosY() - 25);
                tetruBlocks.get(5).setLocation(tetruBlocks.get(5).getPosX() - 25, tetruBlocks.get(5).getPosY() - 50);
                tetruBlocks.get(6).setLocation(tetruBlocks.get(6).getPosX() - 25, tetruBlocks.get(6).getPosY() - 50);
                tetruBlocks.get(7).setLocation(tetruBlocks.get(7).getPosX() - 25, tetruBlocks.get(7).getPosY() - 50);
                tetruBlocks.get(8).setLocation(tetruBlocks.get(8).getPosX() - 25, tetruBlocks.get(8).getPosY() - 50);
                tetruBlocks.get(9).setLocation(tetruBlocks.get(9).getPosX() - 25, tetruBlocks.get(9).getPosY() - 50);
                tetruBlocks.get(10).setLocation(tetruBlocks.get(10).getPosX() - 25, tetruBlocks.get(10).getPosY() - 50);
                tetruBlocks.get(11).setLocation(tetruBlocks.get(11).getPosX() - 25, tetruBlocks.get(11).getPosY() - 50);
                tetruBlocks.get(12).setLocation(tetruBlocks.get(12).getPosX() - 25, tetruBlocks.get(12).getPosY() - 50);
                tetruBlocks.get(13).setLocation(tetruBlocks.get(13).getPosX() - 25, tetruBlocks.get(13).getPosY() - 50);
                tetruBlocks.get(14).setLocation(tetruBlocks.get(14).getPosX() - 25, tetruBlocks.get(14).getPosY() - 50);
                break;
        }
    }
}
