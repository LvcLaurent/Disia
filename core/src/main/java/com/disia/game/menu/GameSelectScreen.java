package com.disia.game.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.disia.game.DisiaGame;

public class GameSelectScreen extends AbstractScreen {

    public GameSelectScreen(DisiaGame game) {
        super(game);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title       = new Label("Choisir un mini-jeu", game.skin, "title");
        Label placeholder = new Label("Aucun mini-jeu disponible pour l'instant.", game.skin);
        TextButton btnRetour = new TextButton("Retour", game.skin);

        table.add(title)      .padBottom(60).row();
        table.add(placeholder).padBottom(60).row();
        table.add(btnRetour)  .width(300).height(60).row();

        btnRetour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new MainMenuScreen(game));
            }
        });
    }
}
