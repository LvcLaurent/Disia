package com.disia.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class DisiaUI {

    public static Skin createSkin() {
        Skin skin = new Skin();

        skin.add("pixel-dark",  createTexture(new Color(0.18f, 0.18f, 0.22f, 1f)));
        skin.add("pixel-mid",   createTexture(new Color(0.28f, 0.28f, 0.34f, 1f)));
        skin.add("pixel-light", createTexture(new Color(0.40f, 0.40f, 0.48f, 1f)));
        skin.add("pixel-white", createTexture(Color.WHITE));

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        skin.add("default-font", font);

        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(4f);
        skin.add("title-font", titleFont);

        Label.LabelStyle labelDefault = new Label.LabelStyle(font, Color.WHITE);
        skin.add("default", labelDefault);

        Label.LabelStyle labelTitle = new Label.LabelStyle(titleFont, Color.WHITE);
        skin.add("title", labelTitle);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up       = skin.newDrawable("pixel-dark");
        btnStyle.over     = skin.newDrawable("pixel-mid");
        btnStyle.down     = skin.newDrawable("pixel-light");
        btnStyle.font     = font;
        btnStyle.fontColor = Color.WHITE;
        skin.add("default", btnStyle);

        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font            = font;
        tfStyle.fontColor       = Color.WHITE;
        tfStyle.messageFontColor = new Color(0.6f, 0.6f, 0.6f, 1f);
        tfStyle.background      = skin.newDrawable("pixel-dark");
        tfStyle.cursor          = skin.newDrawable("pixel-white");
        tfStyle.selection       = skin.newDrawable("pixel-mid");
        skin.add("default", tfStyle);

        return skin;
    }

    private static Texture createTexture(Color color) {
        Pixmap px = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        px.setColor(color);
        px.fill();
        Texture tex = new Texture(px);
        px.dispose();
        return tex;
    }
}
