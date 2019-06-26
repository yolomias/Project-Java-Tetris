//Normaler L förmiger Tetruino
/*
    0 *
    1 *
    2 * 3*
 */

package com.company;

import java.awt.*;

public class Lawliet extends Tetruino {

    public Lawliet() {
        this.setRotation(1);
        this.setAmount(4);
        this.setName('l');
        for (int i = 0; i < getAmount(); i++) {
            Block block = new Block(Color.BLUE);
            tetruBlocks.add(block);
        }
        platziereBlocks();
    }

    @Override
    protected void platziereBlocks() {
        tetruBlocks.get(1).setLocation(150, 75);
        tetruBlocks.get(2).setLocation(150, 100);
        tetruBlocks.get(3).setLocation(175, 100);
    }

    /*
        1: *    2: * * *    3: * *    4:     *
           *       *             *       * * *
           * *                   *
     */

    @Override
    public void rotate(char rotation) {
        if (rotation == 'x') {
            if (getRotation() >= 4) setRotation(1);
            else setRotation(getRotation() + 1);
        } else if (rotation == 'y') {
            if (getRotation() <= 1) setRotation(4);
            else setRotation(getRotation() - 1);
        }
        switch (getRotation()) {
            case 1:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX(), tetruBlocks.get(0).getPosY() - 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() - 25, tetruBlocks.get(1).getPosY());
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() - 50, tetruBlocks.get(2).getPosY() + 25);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() - 25, tetruBlocks.get(3).getPosY() + 50);
                break;

            case 2:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() + 50, tetruBlocks.get(0).getPosY());
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX() + 25, tetruBlocks.get(1).getPosY() - 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX(), tetruBlocks.get(2).getPosY() - 50);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() - 25, tetruBlocks.get(3).getPosY() - 25);
                break;

            case 3:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() - 25 , tetruBlocks.get(0).getPosY() + 50);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX(), tetruBlocks.get(1).getPosY() + 25);
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() + 25, tetruBlocks.get(2).getPosY());
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX(), tetruBlocks.get(3).getPosY() - 25);
                break;

            case 4:
                tetruBlocks.get(0).setLocation(tetruBlocks.get(0).getPosX() - 25, tetruBlocks.get(0).getPosY() - 25);
                tetruBlocks.get(1).setLocation(tetruBlocks.get(1).getPosX(), tetruBlocks.get(1).getPosY());
                tetruBlocks.get(2).setLocation(tetruBlocks.get(2).getPosX() + 25, tetruBlocks.get(2).getPosY() + 25);
                tetruBlocks.get(3).setLocation(tetruBlocks.get(3).getPosX() + 50, tetruBlocks.get(3).getPosY());
                break;
        }
    }
}
