//Gespiegelter L förmiger Tetruino
/*
         0 * 1 *
             2 * 3 *
 */

package com.company;

import java.awt.*;

public class Zett extends Tetruino {

    public Zett(boolean minimalstic) {
        this.setRotation(1);
        this.setAmount(4);
        this.setName('Z');
        for (int i = 0; i < getAmount(); i++) {
            Block block = new Block(Color.MAGENTA, minimalstic);
            int number = i;
            block.addActionListener(e -> System.out.println("Block Nr: " + number));
            tetruBlocks.add(block);
        }
        platziereBlocks();
    }

    @Override
    protected void platziereBlocks() {
        tetruBlocks.get(1).setLocation(175, 50);
        tetruBlocks.get(2).setLocation(175, 75);
        tetruBlocks.get(3).setLocation(200, 75);
    }

    /*
        1: * *   2:      *
             * *       * *
                       *
     */

    @Override
    public void rotate(char rotation) {
        if (rotation == 'x') {
            if (getRotation() == 1) setRotation(2);
            else setRotation(1);
        } /* else if (rotation == 'y') {
            if (getRotation() <= 1) setRotation(4);
            else setRotation(getRotation() - 1);
        } */

        switch (getRotation()) {
            case 1:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() -25, tetruBlocks.get(0).getPosY() - 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX(), tetruBlocks.get(1).getPosY() - 50);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() - 25, tetruBlocks.get(2).getPosY() + 25);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX(), tetruBlocks.get(3).getPosY() );
                break;

            case 2:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() + 25, tetruBlocks.get(0).getPosY() + 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX(), tetruBlocks.get(1).getPosY() + 50);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() + 25, tetruBlocks.get(2).getPosY() - 25);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX(), tetruBlocks.get(3).getPosY());
                break;
        }
    }
}
