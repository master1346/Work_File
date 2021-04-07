package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.lang.System.exit;

public class Main {
    static int number = 1;
    public static void main(String[] args) {
        String savePath = "/Users/user/Games/savegames";
        String savePath1 = "/Users/user/Games/zip";
        File fileDir = new File(savePath);
        List<String> listSaveObject = new ArrayList<>();
        GameProgress gameProgress1 = new GameProgress(70, 3, 1, 12.0);
        GameProgress gameProgress2 = new GameProgress(100, 2, 5, 32.0);
        GameProgress gameProgress3 = new GameProgress(75, 1, 10, 10.0);

        saveGame(savePath, gameProgress1);
        saveGame(savePath, gameProgress2);
        saveGame(savePath, gameProgress3);

        FilenameFilter filter = (directory, fileName) -> fileName.startsWith("gameProgress");
        for(File file : fileDir.listFiles(filter)){
              listSaveObject.add(file.getAbsolutePath());
        }

        zipFiles(savePath + "/Object.zip", listSaveObject);

        for(File file : fileDir.listFiles(filter)){
            file.delete();
        }
        System.out.println("Распаковка архива!");
        openZip(savePath + "/Object.zip", "");

        System.out.println("Печать объектов!");

         for(File file : fileDir.listFiles(filter)){
            System.out.println(openProgress(file.getAbsolutePath()));
         }


    }
    public static void saveGame(String path, GameProgress gameProgress){
        try( FileOutputStream fos = new FileOutputStream(path + "/gameProgress" + number );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos)){
            objectOutputStream.writeObject(gameProgress);
            number++;
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> list) {
        FileInputStream fis = null;
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for(String nameFile : list) {
                fis = new FileInputStream(nameFile);
                ZipEntry entry = new ZipEntry(nameFile);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                if(fis != null) {
                 fis.close();
                }
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void openZip(String pathZip, String dirZip){
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip))){
            ZipEntry entry ;
            while((entry = zin.getNextEntry()) != null) {
                FileOutputStream fout = new FileOutputStream(dirZip + entry.getName());
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }   fout.flush();
                    zin.closeEntry();
                    fout.close();
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static GameProgress openProgress(String pathFile){
        try(FileInputStream fis = new FileInputStream(pathFile);
            ObjectInputStream ois = new ObjectInputStream(fis)){
               return (GameProgress) ois.readObject();
        }catch (IOException ex){
           System.out.println(ex.getMessage());
           return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
