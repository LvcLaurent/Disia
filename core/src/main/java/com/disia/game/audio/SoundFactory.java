package com.disia.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.disia.game.minigames.labyrinth.Theme;

public class SoundFactory {

    public static Sound createThemeSound(Theme theme) {
        try {
            String path;
            switch (theme) {
                case VOITURE:    path = "sounds/horn.ogg";   break;
                case POISSON:    path = "sounds/bubble.ogg"; break;
                case ASTRONAUTE: path = "sounds/beep.ogg";   break;
                default:         path = "sounds/beep.ogg";   break;
            }
            return Gdx.audio.newSound(Gdx.files.internal(path));
        } catch (Exception e) {
            Gdx.app.error("SoundFactory", "Impossible de charger le son : " + e.getMessage());
            return null;
        }
    }
}
