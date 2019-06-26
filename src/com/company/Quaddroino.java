// Quadrat förmiger Tetruino
/*
      0 * * 1
      2 * * 3
 */

package com.company;

import java.awt.*;

public class Quaddroino extends Tetruino {

    public Quaddroino() {
        this.setRotation(1);
        this.setAmount(4);
        this.setName('Q');
        for (int i = 0; i < getAmount(); i++) {
            Block block = new Block(Color.RED);
            tetruBlocks.add(block);
        }
        platziereBlocks();
    }

    @Override
    protected void platziereBlocks() {
        tetruBlocks.get(1).setLocation(175, 50);
        tetruBlocks.get(2).setLocation(150, 75);
        tetruBlocks.get(3).setLocation(175, 75);
    }

    /*
        1: * *
           * *
     */

    @Override
    public void rotate(char rotation) {
        // Ein Quadrat brauch man nicht rotieren, weil es dann immer noch gleich aussieht

        if (getRotation() == 1) {
            tetruBlocks.get(0).setBackground(Color.decode("#f8682c"));
            tetruBlocks.get(1).setBackground(Color.decode("#91c300"));
            tetruBlocks.get(2).setBackground(Color.decode("#00b4f1"));
            tetruBlocks.get(3).setBackground(Color.decode("#ffc300"));
            setRotation(2);
        }
        else if (getRotation() == 2) {
            for (Block b: tetruBlocks) {
                b.setBackground(Color.RED);
            }
            setRotation(1);
        }
    }
}
