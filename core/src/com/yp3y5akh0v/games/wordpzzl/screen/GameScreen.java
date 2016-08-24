package com.yp3y5akh0v.games.wordpzzl.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yp3y5akh0v.games.wordpzzl.GameLogic;
import com.yp3y5akh0v.games.wordpzzl.ResourceManager;
import com.yp3y5akh0v.games.wordpzzl.actor.ActorState;
import com.yp3y5akh0v.games.wordpzzl.actor.Letter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class GameScreen implements Screen, InputProcessor {

    private final Game game;
    private Stage stage;

    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;

    private Random random;
    private boolean isDragged;
    private boolean isTwiceGreenDarkClicked;
    private Letter greenDarkClicked;

    private int[] vowel = {
            0, // a
            4, // e
            8, // i
            14, // o
            20 // u
    };

    private int[] consonant = {
            1, // b
            2, // c
            3, // d
            5, // f
            6, // g
            7, // h
            9, // j
            10, // k
            11, // l
            12, // m
            13, // n
            15, // p
            16, // q
            17, // r
            18, // s
            19, // t
            21, // v
            22, // w
            23, // x
            24, // y
            25, // z
    };

    public GameScreen(final Game game) {

        this.game = game;

        map = new TmxMapLoader().load("map.tmx");
        stage = new Stage(new ScreenViewport());

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapRenderer.setView((OrthographicCamera) stage.getCamera());

        random = new Random();
        try {
            loadLetters();
        } catch (ReflectionException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }

        shapeRenderer = new ShapeRenderer();

        isDragged = false;
    }

    private int vowelOrConsonant() {
        return random.nextInt(2) == 0 ?
                vowel[random.nextInt(vowel.length)] :
                consonant[random.nextInt(consonant.length)];
    }

    private void loadLetters() throws ReflectionException {
        for (Object letterObject : map.getLayers()
                .get("letter_object")
                .getObjects()
                .getByType(ClassReflection.forName("com.badlogic.gdx.maps.objects.RectangleMapObject"))) {
            Rectangle rect = ((RectangleMapObject) letterObject).getRectangle();
            Letter letter = new Letter(ActorState.GREY, vowelOrConsonant(), rect.x, rect.y);
            letter.setSize(letter.getWidth() / 2, letter.getHeight() / 2);
            stage.addActor(letter);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        ResourceManager.getInstance().getBackgroundMusic().play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        mapRenderer.render();
        stage.draw();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Stack<Letter> sequence = GameLogic.getInstance().getSequence();
        for (int i = 1; i < sequence.size(); i++) {
            Letter l1 = sequence.get(i - 1);
            Letter l2 = sequence.get(i);
            shapeRenderer.rectLine(l1.getX() + l1.getWidth() / 2,
                    l1.getY() + l1.getHeight() / 2,
                    l2.getX() + l2.getWidth() / 2,
                    l2.getY() + l2.getHeight() / 2,
                    2);
        }
        shapeRenderer.end();
    }

    private void update(float delta) {
        shapeRenderer.updateMatrices();
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
        ResourceManager.getInstance().getBackgroundMusic().pause();
    }

    @Override
    public void dispose() {
        stage.dispose();
        map.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                ResourceManager.getInstance().getPauseSound().play();
                game.setScreen(new PauseScreen(game, this));
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
        Vector2 coord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Letter hitLetter = (Letter) stage.hit(coord.x, coord.y, false);
        if (hitLetter != null) {
            GameLogic gameLogic = GameLogic.getInstance();
            if (gameLogic.sizeOfSequence() == 1 && hitLetter == gameLogic.peekFromSequence()) {
                gameLogic.clearSequence();
                ResourceManager.getInstance().getSelectSound().play();
            } else
                gameLogic.pushToSequence(hitLetter);
            if (hitLetter.getState() == ActorState.GREEN_DARK) {
                isTwiceGreenDarkClicked = greenDarkClicked == hitLetter;
                greenDarkClicked = hitLetter;
            } else {
                greenDarkClicked = null;
                isTwiceGreenDarkClicked = false;
            }
        }
        isDragged = false;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 coord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Letter hitLetter = (Letter) stage.hit(coord.x, coord.y, false);
        if (hitLetter != null) {
            GameLogic gameLogic = GameLogic.getInstance();
            if (hitLetter.getState() == ActorState.GREEN_DARK) {
                if (isDragged || isTwiceGreenDarkClicked) {
                    ResourceManager.getInstance().getWordSound().play();
                    HashMap<Integer, Integer> newLetterPosCount = new HashMap<>();
                    for (Letter letter : gameLogic.getSequence()) {
                        if (!newLetterPosCount.containsKey((int) letter.getX()))
                            newLetterPosCount.put((int) letter.getX(), 1);
                        else
                            newLetterPosCount.put((int) letter.getX(), newLetterPosCount.get((int) letter.getX()) + 1);
                        stage.getRoot().removeActor(letter);
                    }
                    gameLogic.calculateScore();
                    gameLogic.clearSequence();
                    for (Map.Entry<Integer, Integer> entry : newLetterPosCount.entrySet()) {
                        for (int i = 0; i < entry.getValue(); i++) {
                            Letter letter = new Letter(ActorState.GREY,
                                    vowelOrConsonant(),
                                    entry.getKey(),
                                    640 + 64 * i);
                            letter.setSize(letter.getWidth() / 2, letter.getHeight() / 2);
                            stage.addActor(letter);
                        }
                    }
                    newLetterPosCount.clear();
                }
            }
        }
        isDragged = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 coord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Letter hitLetter = (Letter) stage.hit(coord.x, coord.y, false);
        if (hitLetter != null)
            GameLogic.getInstance().pushToSequence(hitLetter);
        isDragged = true;
        return true;
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
