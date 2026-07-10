package com.disia.game.minigames.labyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.audio.Sound;
import com.disia.game.DisiaGame;
import com.disia.game.audio.SoundFactory;
import com.disia.game.menu.AbstractScreen;
import com.disia.game.menu.GameSelectScreen;

public class LabyrinthScreen extends AbstractScreen {

    private static final int   COLS  = 13;
    private static final int   ROWS  = 9;
    private static final float SPEED = 10f; // cases par seconde

    private final Theme         theme;
    private final Maze          maze;
    private final ShapeRenderer shapes;
    private final Matrix4       proj = new Matrix4();
    private final Sound         spaceSound;

    // Position logique (grille)
    private int playerCol = 0;
    private int playerRow = 0;

    // Position visuelle (interpolée, en coordonnées de cases)
    private float renderX = 0f;
    private float renderY = 0f;

    // Déplacement en cours
    private int     targetCol = 0;
    private int     targetRow = 0;
    private boolean moving    = false;

    // Dernière direction pressée (mémorisée pour mouvement continu)
    private int queuedDx = 0;
    private int queuedDy = 0;

    private boolean won = false;
    private Table   winOverlay;

    private float cellSize, offsetX, offsetY, wallThick;

    public LabyrinthScreen(DisiaGame game, Theme theme) {
        super(game);
        this.theme      = theme;
        this.maze       = new Maze(COLS, ROWS, System.currentTimeMillis());
        this.shapes     = new ShapeRenderer();
        this.spaceSound = SoundFactory.createThemeSound(theme);
        buildUI();
    }

    private void buildUI() {
        Table nav = new Table();
        nav.setFillParent(true);
        nav.top().left().pad(16);
        stage.addActor(nav);

        TextButton btnRetour = new TextButton("Retour", game.skin);
        nav.add(btnRetour).width(180).height(50);
        btnRetour.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                goTo(new GameSelectScreen(game));
            }
        });

        winOverlay = new Table();
        winOverlay.setFillParent(true);
        winOverlay.setVisible(false);
        winOverlay.setBackground(game.skin.newDrawable("pixel-white", new Color(0f, 0f, 0f, 0.78f)));
        stage.addActor(winOverlay);

        winOverlay.add(new Label("Bravo !", game.skin, "title")).padBottom(16).row();
        winOverlay.add(new Label("Labyrinthe terminé !", game.skin)).padBottom(50).row();

        TextButton btnRejouer = new TextButton("Rejouer",        game.skin);
        TextButton btnMenu    = new TextButton("Retour au menu", game.skin);
        winOverlay.add(btnRejouer).width(300).height(60).padBottom(20).row();
        winOverlay.add(btnMenu)   .width(300).height(60).row();

        btnRejouer.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                goTo(new LabyrinthScreen(game, theme));
            }
        });
        btnMenu.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                goTo(new GameSelectScreen(game));
            }
        });
    }

    @Override
    public void show() {
        super.show();
        updateLayout();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        updateLayout();
    }

    private void updateLayout() {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        float margin = 80f;
        cellSize  = Math.min((w - 2 * margin) / COLS, (h - 2 * margin) / ROWS);
        wallThick = Math.max(2f, cellSize * 0.10f);
        offsetX   = (w - cellSize * COLS) / 2f;
        offsetY   = (h - cellSize * ROWS) / 2f;
        proj.setToOrtho2D(0, 0, w, h);
    }

    @Override
    public void render(float delta) {
        Color bg = theme.bgColor;
        Gdx.gl.glClearColor(bg.r, bg.g, bg.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!won) {
            readInput();
            updateMovement(delta);
        }

        shapes.setProjectionMatrix(proj);
        drawMazeAndPlayer();

        stage.act(delta);
        stage.draw();
    }

    /** Lit la direction pressée et la mémorise. Espace → son du thème. */
    private void readInput() {
        if      (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { queuedDx =  1; queuedDy =  0; }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  { queuedDx = -1; queuedDy =  0; }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  { queuedDx =  0; queuedDy =  1; }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP))    { queuedDx =  0; queuedDy = -1; }
        else                                               { queuedDx =  0; queuedDy =  0; }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && spaceSound != null) {
            spaceSound.play(0.8f);
        }
    }

    /**
     * Fait glisser le joueur vers la case cible.
     * Quand il arrive, essaie immédiatement la direction en attente
     * pour un mouvement continu si on maintient une touche.
     */
    private void updateMovement(float delta) {
        if (!moving) {
            if (queuedDx != 0 || queuedDy != 0) {
                if (maze.canMove(playerCol, playerRow, queuedDx, queuedDy)) {
                    targetCol = playerCol + queuedDx;
                    targetRow = playerRow + queuedDy;
                    moving    = true;
                }
            }
            return;
        }

        // Avance renderX/Y vers la cible
        float dx   = targetCol - renderX;
        float dy   = targetRow - renderY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        float step = SPEED * delta;

        if (dist <= step) {
            // Arrivée : on snape à la case cible
            renderX   = targetCol;
            renderY   = targetRow;
            playerCol = targetCol;
            playerRow = targetRow;
            moving    = false;

            if (playerCol == COLS - 1 && playerRow == ROWS - 1) {
                won = true;
                winOverlay.setVisible(true);
                return;
            }

            // Mouvement continu si la touche est encore pressée
            if ((queuedDx != 0 || queuedDy != 0)
                    && maze.canMove(playerCol, playerRow, queuedDx, queuedDy)) {
                targetCol = playerCol + queuedDx;
                targetRow = playerRow + queuedDy;
                moving    = true;
            }
        } else {
            renderX += (dx / dist) * step;
            renderY += (dy / dist) * step;
        }
    }

    private void drawMazeAndPlayer() {
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Sol et murs
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                float x = offsetX + c * cellSize;
                float y = offsetY + (ROWS - 1 - r) * cellSize;

                shapes.setColor(c == COLS - 1 && r == ROWS - 1 ? theme.goalColor : theme.floorColor);
                shapes.rect(x, y, cellSize, cellSize);

                shapes.setColor(theme.wallColor);
                if (r == 0        || !maze.canMove(c, r,  0, -1))
                    shapes.rect(x, y + cellSize - wallThick, cellSize,  wallThick);
                if (r == ROWS - 1 || !maze.canMove(c, r,  0,  1))
                    shapes.rect(x, y,                        cellSize,  wallThick);
                if (c == 0        || !maze.canMove(c, r, -1,  0))
                    shapes.rect(x, y,                        wallThick, cellSize);
                if (c == COLS - 1 || !maze.canMove(c, r,  1,  0))
                    shapes.rect(x + cellSize - wallThick, y, wallThick, cellSize);
            }
        }

        // Joueur (position interpolée)
        float px  = offsetX + renderX * cellSize;
        float py  = offsetY + (ROWS - 1 - renderY) * cellSize;
        float pad = cellSize * 0.15f;
        shapes.setColor(theme.playerColor);
        shapes.ellipse(px + pad, py + pad, cellSize - 2 * pad, cellSize - 2 * pad);

        shapes.end();
    }

    @Override
    public void dispose() {
        if (spaceSound != null) spaceSound.dispose();
        shapes.dispose();
        super.dispose();
    }
}
