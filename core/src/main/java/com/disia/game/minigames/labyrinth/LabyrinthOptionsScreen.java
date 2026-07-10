package com.disia.game.minigames.labyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.disia.game.DisiaGame;
import com.disia.game.menu.AbstractScreen;
import com.disia.game.menu.GameSelectScreen;

public class LabyrinthOptionsScreen extends AbstractScreen {

    private static final String PREFS_NAME  = "disia-settings";
    private static final String PREF_THEME  = "labyrinth-theme";

    public LabyrinthOptionsScreen(DisiaGame game) {
        super(game);

        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        String savedTheme = prefs.getString(PREF_THEME, Theme.VOITURE.name());

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        table.add(new Label("Labyrinthe — Thème", game.skin, "title")).padBottom(60).row();
        table.add(new Label("Choisir une apparence :", game.skin)).padBottom(30).row();

        for (Theme t : Theme.values()) {
            String label = t.label + (t.name().equals(savedTheme) ? "  ✓" : "");
            TextButton btn = new TextButton(label, game.skin);
            table.add(btn).width(320).height(60).padBottom(18).row();

            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    prefs.putString(PREF_THEME, t.name());
                    prefs.flush();
                    goTo(new LabyrinthScreen(game, t));
                }
            });
        }

        TextButton btnRetour = new TextButton("Retour", game.skin);
        table.add(btnRetour).width(320).height(60).padTop(20).row();
        btnRetour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new GameSelectScreen(game));
            }
        });
    }
}
