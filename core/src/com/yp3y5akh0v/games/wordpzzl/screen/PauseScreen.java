package com.yp3y5akh0v.games.wordpzzl.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yp3y5akh0v.games.wordpzzl.GameLogic;
import com.yp3y5akh0v.games.wordpzzl.ResourceManager;
import com.yp3y5akh0v.games.wordpzzl.Stats;

public class PauseScreen implements Screen, InputProcessor {

    private final Game game;
    private final Screen parent;
    private Stage stage;
    private Label pauseLabel;
    private Label scoreLabel;
    private Label movesLabel;
    private Label bestWordLabel;
    private TextButton resumeTextButton;
    private TextButton exitTextButton;
    private Table table;

    public PauseScreen(final Game game, final Screen parent) {

        this.game = game;
        this.parent = parent;

        stage = new Stage(new ScreenViewport());

        Skin uiSkin = ResourceManager.getInstance().getUiSkin();
        Stats stats = GameLogic.getInstance().getStats();

        pauseLabel = new Label("pause", uiSkin, "default");
        scoreLabel = new Label("score: " + stats.getScore(), uiSkin, "default");
        movesLabel = new Label("number of moves: " + stats.getMoves(), uiSkin, "default");
        bestWordLabel = new Label("best word: " + stats.getBestWord(), uiSkin, "default");

        resumeTextButton = new TextButton("resume", uiSkin, "default");
        exitTextButton = new TextButton("exit", uiSkin, "default");

        resumeTextButton.setTouchable(Touchable.enabled);
        exitTextButton.setTouchable(Touchable.enabled);

        resumeTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(parent);
            }
        });

        exitTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table = new Table();
        table.setFillParent(true);

        table.add().expand();
        table.add(pauseLabel).expand();
        table.add().expand();

        table.row();

        table.add().expand();
        table.add(scoreLabel).expand();
        table.add().expand();

        table.row();

        table.add().expand();
        table.add(movesLabel).expand();
        table.add().expand();

        table.row();

        table.add().expand();
        table.add(bestWordLabel).expand();
        table.add().expand();

        table.row();

        table.add(resumeTextButton).width(125).height(55).expand();
        table.add().expand();
        table.add(exitTextButton).width(125).height(55).expand();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(this, stage));
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
                game.setScreen(parent);
                break;
            case Input.Keys.BACK:
                Gdx.app.exit();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
