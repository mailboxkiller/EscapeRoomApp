package dev.trainwreck.escaperoomapp.data.gameobjects;


import com.google.gson.Gson;
import dev.trainwreck.escaperoomapp.data.ClueData;
import dev.trainwreck.escaperoomapp.util.Util;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameData implements Serializable{

    private UUID gameId;
    private String gameName;
    private List<RoomData> roomData = new ArrayList<>();
    private ClueData clueData = new ClueData();
    private Image image;


    public GameData(String gameName) {
        this.gameId = UUID.randomUUID();
        this.gameName = gameName;
        roomData.add(new RoomData("test-room"));
        loadGameData();
    }

    public void addRoom(RoomData roomData){
        this.roomData.add(roomData);
    }


    public void saveGame(){
        clueData.saveClueData(gameName);
    }

    public void loadGameData(){
        clueData.loadClueData(gameName);
        loadImage();

    }

    public void pickImage(){
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        chooser.setAcceptAllFileFilterUsed(false);
        int returnValue = chooser.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION){
            try{
                File imageFile = new File("data/"+gameName+"/ImageDataSaveFile.png");
                Files.createDirectories(Paths.get("data/"+gameName));
                ImageIO.write(ImageIO.read(chooser.getSelectedFile()), "png", imageFile);
                InputStream inputStream = new BufferedInputStream(new FileInputStream(imageFile));
                image = new Image(inputStream,1920,1080,true,true);
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void loadImage(){
        try {
            File imageFile = new File("data/"+gameName+"/ImageDataSaveFile.png");
            InputStream inputStream = new BufferedInputStream(new FileInputStream(imageFile));
            image = new Image(inputStream,1920,1080,true,false);
        }catch (FileNotFoundException e) {
            pickImage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }

    public ObservableList<String> getClueData() {
        return clueData.getObservableList();
    }

    public void setClueData(ObservableList<String> clueData) {
        this.clueData.setObservableList(clueData);
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public List<RoomData> getRoomData() {
        return roomData;
    }
}
