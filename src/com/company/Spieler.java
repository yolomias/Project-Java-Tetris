//Die Spieler Klasse enthält die Daten für die Highscores
package com.company;

public class Spieler {
    private String name;
    private String punktzahl;
    private String zeit;

    public Spieler(String name, String punktzahl, String zeit) {
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

    public String getPunktzahl() {
        return punktzahl;
    }

    public void setPunktzahl(String punktzahl) {
        this.punktzahl = punktzahl;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }
}
