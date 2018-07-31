package org.knowbase.audio;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.knowbase.Vbox2;
import org.knowbase.tools.Settings;
//import org.lwjgl.openal.*;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

//import static org.lwjgl.openal.AL10.alGetError;

public class AudioPlayer extends Application {

    static Settings settings;
    Rectangle2D MAXIMUM_BOUNDS;
    static Stage MAIN_STAGE;
    static ProgressBar trackProgress;
    @Override
    public void start(Stage primaryStage)   {
        MAXIMUM_BOUNDS= Screen.getPrimary().getVisualBounds();
        MAIN_STAGE=primaryStage;
        settings=new Settings(Paths.get("settings.txt"));
        trackProgress=new ProgressBar(0);
        final MP3Player[] mp3Player = new MP3Player[1];
        final OGGPlayer[] OGGPlayer = new OGGPlayer[1];
        Button openFile=new Button("Open MP3 file");
        openFile.setOnAction(event -> {
            FileChooser mp3chooser=new FileChooser();
            mp3chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3","*.mp3"));
            mp3chooser.setInitialDirectory(new File(AudioPlayer.settings.getOrDefault("last_directory",System.getProperty("user.home"))));
            File file=mp3chooser.showOpenDialog(AudioPlayer.MAIN_STAGE);
            if(file!=null) {
                mp3Player[0] = new MP3Player(file);
                Thread thread=new Thread(mp3Player[0]);
                thread.start();
            }

        });
        Button openOGGfile=new Button("Open OGG file");
        openOGGfile.setOnAction(event -> {
            FileChooser oggchooser=new FileChooser();
            oggchooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OGG","*.ogg"));
            oggchooser.setInitialDirectory(new File(settings.getOrDefault("last_directory",System.getProperty("user.home"))));
            File file=oggchooser.showOpenDialog(primaryStage);
            if(file!=null)
            {
                try {
                    OGGPlayer[0] = new OGGPlayer(file.toURI().toURL().toString());
                    Thread thread=new Thread(OGGPlayer[0]);
                    thread.start();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        Button stop=new Button("Stop");
        stop.setOnAction(event -> {
            if(mp3Player[0]!=null)
                 mp3Player[0].stop();
            if(OGGPlayer[0]!=null)
                OGGPlayer[0].stopMusic();
        });
        Vbox2 vbox2=new Vbox2(openFile,openOGGfile,stop,trackProgress);
        Scene scene=new Scene(vbox2,MAXIMUM_BOUNDS.getWidth()-300,MAXIMUM_BOUNDS.getHeight()-300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Audio player");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void stop() {
        settings.save();
    }
}
