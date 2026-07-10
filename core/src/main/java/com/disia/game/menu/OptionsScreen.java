package com.disia.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.disia.game.DisiaGame;

public class OptionsScreen extends AbstractScreen {

    private static final String PREFS_NAME = "disia-settings";

    public OptionsScreen(DisiaGame game) {
        super(game);

        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Options", game.skin, "title");
        table.add(title).colspan(2).padBottom(60).row();

        Label labelPseudo = new Label("Pseudo :", game.skin);
        TextField fieldPseudo = new TextField(prefs.getString("pseudo", ""), game.skin);
        fieldPseudo.setMessageText("Votre pseudo");

        Label labelAge = new Label("Âge :", game.skin);
        TextField fieldAge = new TextField(prefs.getInteger("age", 0) > 0
                ? String.valueOf(prefs.getInteger("age", 0)) : "", game.skin);
        fieldAge.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        fieldAge.setMessageText("Votre âge");

        table.add(labelPseudo).right().padRight(20).padBottom(20);
        table.add(fieldPseudo).width(300).height(50).padBottom(20).row();

        table.add(labelAge).right().padRight(20).padBottom(50);
        table.add(fieldAge).width(300).height(50).padBottom(50).row();

        TextButton btnSave   = new TextButton("Sauvegarder", game.skin);
        TextButton btnRetour = new TextButton("Retour",       game.skin);

        table.add(btnSave)  .colspan(2).width(300).height(60).padBottom(20).row();
        table.add(btnRetour).colspan(2).width(300).height(60).row();

        btnSave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String pseudo  = fieldPseudo.getText().trim();
                String ageText = fieldAge.getText().trim();
                int age = ageText.isEmpty() ? 0 : Integer.parseInt(ageText);
                prefs.putString("pseudo", pseudo);
                prefs.putInteger("age", age);
                prefs.flush();
                goTo(new MainMenuScreen(game));
            }
        });

        btnRetour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goTo(new MainMenuScreen(game));
            }
        });
    }
}
