package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

//Die Tetruino Klasse welche sich aus Blöcken zusammensetzt, abstract damit später die genaue Form festgelegt wird
//Erläuterung dann in der TILT Klasse
public abstract class Tetruino {
    //Attribute
    private char name;
    private int rotation;
    private int amount;
    private boolean active;
    protected List<Block> tetruBlocks = new ArrayList<>();

    public Tetruino() {

    }
    //GETTER UND SETTER
    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /* protected void addTetruino(Tetruino t) {
        tetruList.add(t);
    } */

    protected abstract void platziereBlocks();

    public abstract void rotate(char rotation);

    //Die Bewegen Methode ist bei allen Tetruinos gleich
    public void move(char way) {
        switch (way) {
            //DOWN
            case 'd':
                for(Block b: tetruBlocks) {
                    b.setLocation(b.getPosX(), b.getPosY() + 25);
                }
                break;
            //LEFT
            case 'l':
                for(Block b: tetruBlocks) {
                    b.setLocation(b.getPosX() - 25, b.getPosY() );
                }
                break;
            //RIGHT
            case 'r':
                for(Block b: tetruBlocks) {
                    b.setLocation(b.getPosX() + 25, b.getPosY() );
                }
                break;
            //Next
            case 'n':
                for(Block b: tetruBlocks) {
                    b.setLocation(b.getPosX() + 300, b.getPosY() + 25 );
                }
                break;

            //Start
            case 's':
                for(Block b: tetruBlocks) {
                    b.setLocation(b.getPosX() - 300, b.getPosY() - 25 );
                }
                break;
        }
    }

    //Eine Methode um den Tetriuno auf das Paneel zu bekommen
    public void addToPanel(JLayeredPane panel, Tetruino t) {
        for(Block b: tetruBlocks) {
            panel.add(b);
            panel.setLayer(b, 1);
        }
    }

}
