//Die Spieler Klasse enthält die Daten für die Highscores
package com.company;

import java.io.Serializable;

public class Spieler implements Serializable {
    private String name;
    private long punktzahl;
    private String zeit;

    Spieler(String name, long punktzahl, String zeit) {
        this.name = name;
        this.punktzahl = punktzahl;
        this.zeit = zeit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPunktzahl() {
        return punktzahl;
    }

    public void setPunktzahl(long punktzahl) {
        this.punktzahl = punktzahl;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }

    public void print() {
        System.out.println("Name: " + getName() + " Score: " + getPunktzahl() + " Zeit: " + getZeit());
    }
}
