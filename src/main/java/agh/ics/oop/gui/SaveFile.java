package agh.ics.oop.gui;

import agh.ics.oop.InputParameters;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    String leftPath = "src/main/resources/fileLeft.csv";
    String rightPath = "src/main/resources/fileRight.csv";
    FileWriter fileWriter = null;

    // constructor
    public SaveFile(InputParameters inputParameters, boolean isLeft) throws IOException {
        if (isLeft){
            File fileLeft = new File("src/main/resources/fileLeft.csv");
            if(!fileLeft.exists()){
                fileLeft.createNewFile();
            }else {
                fileLeft.delete();
                fileLeft.createNewFile();
            }
            try {
                fileWriter = new FileWriter(leftPath);
                fileWriter.write(prepareFirstLine());
                fileWriter.write("\n");
            }finally {
                if(fileWriter != null){
                    fileWriter.close();
                }
            }
        }else{
            File fileRight = new File("src/main/resources/fileRight.csv");
            if(!fileRight.exists()){
                fileRight.createNewFile();
            }else{
                fileRight.delete();
                fileRight.createNewFile();
            }
            try {
                fileWriter = new FileWriter(rightPath);
                fileWriter.write(prepareFirstLine());
                fileWriter.write("\n");
            }finally {
                if(fileWriter != null){
                    fileWriter.close();
                }
            }
        }
    }

    // methods
    private String prepareFirstLine(){
        String s = "day  animals  plants  energy  kids  life expectancy";
        return s;
    }

    private String prepareLine(int day, int animals, int grass, int energy, int kids, int lifeTime){
        String s = Integer.toString(day) + "       " + Integer.toString(animals) + "        " + Integer.toString(grass) + "     " +
                Integer.toString(energy) + "       " + Integer.toString(kids) + "       " + Integer.toString(lifeTime);
        return s;
    }

    public void updateFile(int day, int animals, int grass, int energy, int kids, int lifeTime, Boolean isLeft) throws IOException {
        if (isLeft) {
            fileWriter = new FileWriter(leftPath, true);
        } else {
            fileWriter = new FileWriter(rightPath, true);
        }
        try {
            fileWriter.write(prepareLine(day, animals, grass, energy, kids, lifeTime));
            fileWriter.write("\n");
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

}
