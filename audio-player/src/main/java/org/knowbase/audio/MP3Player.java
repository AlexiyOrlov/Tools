package org.knowbase.audio;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MP3Player extends Task {

    private Player player;
    private File file;
    MP3Player(File mp3File) {
        file=mp3File;
        setOnScheduled(event -> AudioPlayer.trackProgress.progressProperty().bind(progressProperty()));
    }


    void stop()
    {
        if(player!=null)
            player.close();
    }

    @Override
    protected Object call() {
        try {
            player = new Player(new FileInputStream(file));
            player.play();
        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
