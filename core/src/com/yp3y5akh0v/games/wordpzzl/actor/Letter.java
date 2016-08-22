package com.yp3y5akh0v.games.wordpzzl.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.yp3y5akh0v.games.wordpzzl.ResourceManager;

public class Letter extends Actor {

    private ActorState state;
    private int value;
    private Sprite sprite;

    private float y0Down, y1Down, curDT;
    private boolean isMove;

    public Letter(ActorState state, int value, float x, float y) {
        this.state = state;
        this.value = value;
        sprite = new Sprite(getTextureRegion());
        setBounds(x, y, sprite.getWidth(), sprite.getHeight());
        curDT = 0;
    }

    public void setState(ActorState state) {
        this.state = state;
    }

    public ActorState getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public char getCharacter() {
        return (char) (value + 97);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setRegion(getTextureRegion());
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isMove)
            moveScript(delta);
        else if (isMove = isThereAirBellow()) {
            y0Down = getY();
            y1Down = y0Down - 64;
        }
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    public TextureRegion getTextureRegion() {
        ResourceManager resourceManager = ResourceManager.getInstance();
        TextureRegion textureRegion;
        switch (state) {
            case GREY:
                textureRegion = resourceManager.getGreyAtlas()
                        .findRegion(String.valueOf(getCharacter()));
                break;
            case GOLD:
                textureRegion = resourceManager.getGoldAtlas()
                        .findRegion(String.valueOf(getCharacter()));
                break;
            case ORANGE:
                textureRegion = resourceManager.getOrangeAtlas()
                        .findRegion(String.valueOf(getCharacter()));
                break;
            case GREEN_LIGHT:
                textureRegion = resourceManager.getGreen_lightAtlas()
                        .findRegion(String.valueOf(getCharacter()));
                break;
            case GREEN_DARK:
                textureRegion = resourceManager.getGreen_darkAtlas()
                        .findRegion(String.valueOf(getCharacter()));
                break;
            default:
                textureRegion = null;
        }
        return textureRegion;
    }

    private boolean isThereAirBellow() {
        return getY() > 0 && getStage().hit(getX(), getY() - 1, false) == null;
    }

    public void moveScript(float delta) {
        curDT += delta * 2;
        if (curDT < 1) {
            float curY = y0Down + (y1Down - y0Down) * curDT;
            setPosition(getX(), curY);
        } else {
            setPosition(getX(), y1Down);
            curDT = 0;
            isMove = false;
        }
    }
}
