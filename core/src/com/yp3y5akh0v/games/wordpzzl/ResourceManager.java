package com.yp3y5akh0v.games.wordpzzl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.TreeSet;

public final class ResourceManager {

    private TextureAtlas greyAtlas;
    private TextureAtlas goldAtlas;
    private TextureAtlas orangeAtlas;
    private TextureAtlas green_lightAtlas;
    private TextureAtlas green_darkAtlas;
    private TextureAtlas fontAtlas;
    private Skin uiSkin;
    private TreeSet<String> words;
    private Sound pauseSound;
    private Sound selectSound;
    private Sound wordSound;
    private Music backgroundMusic;
    private static final ResourceManager instance = new ResourceManager();

    private ResourceManager() {
    }

    public void load() {
        greyAtlas = new TextureAtlas("letters/grey/spritesheet.atlas");
        goldAtlas = new TextureAtlas("letters/gold/spritesheet.atlas");
        orangeAtlas = new TextureAtlas("letters/orange/spritesheet.atlas");
        green_lightAtlas = new TextureAtlas("letters/green_light/spritesheet.atlas");
        green_darkAtlas = new TextureAtlas("letters/green_dark/spritesheet.atlas");
        fontAtlas = new TextureAtlas("font/default.atlas");
        uiSkin = new Skin(Gdx.files.internal("font/default.json"), fontAtlas);

        pauseSound = Gdx.audio.newSound(Gdx.files.internal("audio/pause.mp3"));
        selectSound = Gdx.audio.newSound(Gdx.files.internal("audio/select.mp3"));
        wordSound = Gdx.audio.newSound(Gdx.files.internal("audio/word.mp3"));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/background.mp3"));
        backgroundMusic.setLooping(true);

        words = new TreeSet<>();
        byte[] wsb = Gdx.files.internal("wordsEn.txt").readBytes();
        StringBuilder sb = new StringBuilder();
        for (byte b : wsb) {
            if (b == 13) {
            } else if (b == 10) {
                words.add(sb.toString());
                sb.setLength(0);
            } else sb.append((char) b);
        }
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    public TextureAtlas getGreyAtlas() {
        return greyAtlas;
    }

    public TextureAtlas getGoldAtlas() {
        return goldAtlas;
    }

    public TextureAtlas getOrangeAtlas() {
        return orangeAtlas;
    }

    public TextureAtlas getGreen_lightAtlas() {
        return green_lightAtlas;
    }

    public TextureAtlas getGreen_darkAtlas() {
        return green_darkAtlas;
    }

    public TextureAtlas getFontAtlas() {
        return fontAtlas;
    }

    public Skin getUiSkin() {
        return uiSkin;
    }

    public TreeSet<String> getWords() {
        return words;
    }

    public Sound getPauseSound() {
        return pauseSound;
    }

    public Sound getSelectSound() {
        return selectSound;
    }

    public Sound getWordSound() {
        return wordSound;
    }

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public void dispose() {
        greyAtlas.dispose();
        goldAtlas.dispose();
        orangeAtlas.dispose();
        green_lightAtlas.dispose();
        green_darkAtlas.dispose();
        uiSkin.dispose();
        fontAtlas.dispose();
        pauseSound.dispose();
        selectSound.dispose();
        wordSound.dispose();
        backgroundMusic.dispose();
    }
}
