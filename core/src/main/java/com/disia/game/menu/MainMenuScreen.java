package com.disia.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.disia.game.DisiaGame;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(DisiaGame game) {
        super(game);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("DISIA", game.skin, "title");
        TextButton btnJouer   = new TextButton("Jouer",   game.skin);
        TextButton btnOptions = new TextButton("Options", game.skin);
        TextButton btnQuitter = new TextButton("Quitter", game.skin);

        table.add(title).padBottom(80).row();
        table.add(btnJouer)  .width(300).height(60).padBottom(20).row();
        table.add(btnOptions).width(300).height(60).padBottom(20).row();
        table.add(btnQuitter).width(300).height(60).row();

        btnJouer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new GameSelectScreen(game));
            }
        });

        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new OptionsScreen(game));
            }
        });

        btnQuitter.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
}
