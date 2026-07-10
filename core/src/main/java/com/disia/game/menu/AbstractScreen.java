package com.disia.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.disia.game.DisiaGame;

public abstract class AbstractScreen implements Screen {

    protected final DisiaGame game;
    protected final Stage stage;

    public AbstractScreen(DisiaGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
    }

    /** Navigue vers un autre écran en disposant celui-ci proprement. */
    protected void goTo(Screen next) {
        // Postrunnable pour ne pas disposer le stage en plein milieu d'un act()
        Gdx.app.postRunnable(() -> {
            game.setScreen(next);
            dispose();
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
