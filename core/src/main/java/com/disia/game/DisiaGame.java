package com.disia.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.disia.game.menu.MainMenuScreen;
import com.disia.game.ui.DisiaUI;

public class DisiaGame extends Game {

    public SpriteBatch batch;
    public Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        skin = DisiaUI.createSkin();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
    }
}
