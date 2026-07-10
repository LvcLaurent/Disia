package com.disia.game.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.disia.game.DisiaGame;
import com.disia.game.minigames.labyrinth.LabyrinthOptionsScreen;

public class GameSelectScreen extends AbstractScreen {

    public GameSelectScreen(DisiaGame game) {
        super(game);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        table.add(new Label("Choisir un mini-jeu", game.skin, "title")).padBottom(60).row();

        TextButton btnLabyrinth = new TextButton("Labyrinthe", game.skin);
        table.add(btnLabyrinth).width(300).height(60).padBottom(20).row();
        btnLabyrinth.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new LabyrinthOptionsScreen(game));
            }
        });

        TextButton btnRetour = new TextButton("Retour", game.skin);
        table.add(btnRetour).width(300).height(60).padTop(20).row();
        btnRetour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new MainMenuScreen(game));
            }
        });
    }
}
