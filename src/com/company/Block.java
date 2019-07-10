//Die Block definiert einen Block, aus denen die Tetruinos gebaut werden
package com.company;

import javax.swing.*;
import java.awt.*;

//Klasse erweitertert JButton damit der Block alle Funktionen eines Buttons hat
class Block extends JButton {
    //Attribute der Block Klasse
   //private static int id = -1;
    private int posX;
    private int posY;
    private Color bgColour;
    private Icon texture;

    //Konstruktor stelle alle Maße, Positionen, Farben und Icons ein
    Block(Color bgColour, boolean minimalism) {
        this.bgColour = Color.YELLOW;
        this.setBackground(bgColour);
        if (!minimalism) this.texture = new ImageIcon(Class.class.getResource("/texture/block.png"));
        this.setIcon(texture);
        this.setFocusPainted(false);
        this.posX = 150;
        this.posY = 50;
        this.setBounds(posX, posY, 25, 25);
        //id++;
    }

    //GETTER UND SETTER

    public int getPosX() {
        return this.getBounds().x;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.getBounds().y;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    //public static int getId() { return id; }
}
