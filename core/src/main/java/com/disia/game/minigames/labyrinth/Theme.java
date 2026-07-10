package com.disia.game.minigames.labyrinth;

import com.badlogic.gdx.graphics.Color;

public enum Theme {

    VOITURE("Voiture",
        new Color(0.12f, 0.12f, 0.12f, 1f),   // fond (bitume)
        new Color(0.55f, 0.55f, 0.55f, 1f),   // mur (béton)
        new Color(0.90f, 0.15f, 0.15f, 1f),   // joueur (voiture rouge)
        new Color(0.18f, 0.18f, 0.18f, 1f),   // sol (asphalte)
        new Color(1.00f, 0.85f, 0.00f, 1f)    // objectif (ligne d'arrivée)
    ),
    POISSON("Poisson",
        new Color(0.00f, 0.04f, 0.18f, 1f),   // fond (mer profonde)
        new Color(0.00f, 0.20f, 0.55f, 1f),   // mur (récif)
        new Color(1.00f, 0.50f, 0.00f, 1f),   // joueur (poisson orange)
        new Color(0.00f, 0.10f, 0.32f, 1f),   // sol (eau)
        new Color(1.00f, 0.95f, 0.20f, 1f)    // objectif (trésor)
    ),
    ASTRONAUTE("Astronaute",
        new Color(0.00f, 0.00f, 0.04f, 1f),   // fond (espace)
        new Color(0.28f, 0.28f, 0.38f, 1f),   // mur (astéroïde)
        new Color(1.00f, 1.00f, 1.00f, 1f),   // joueur (combinaison blanche)
        new Color(0.03f, 0.03f, 0.08f, 1f),   // sol (vide spatial)
        new Color(1.00f, 0.80f, 0.10f, 1f)    // objectif (étoile)
    );

    public final String label;
    public final Color bgColor;
    public final Color wallColor;
    public final Color playerColor;
    public final Color floorColor;
    public final Color goalColor;

    Theme(String label, Color bg, Color wall, Color player, Color floor, Color goal) {
        this.label       = label;
        this.bgColor     = bg;
        this.wallColor   = wall;
        this.playerColor = player;
        this.floorColor  = floor;
        this.goalColor   = goal;
    }
}
