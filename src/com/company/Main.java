package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static String dirPath = "/Users/user/Games/";
    public static void main(String[] args) {
        String[] dirName = new String[]{"src", "res", "savegames", "temp"};
        String[] dirResName = new String[]{"drawables", "vectors", "icons"};
        String[] fileMainName = new String[]{"Main.java", "Utils.java"};
        StringBuilder sbTemp = new StringBuilder();

        for (String dir : dirName) {
            checkDir(dir, sbTemp);
            if (dir.equals("src")) {
                checkDir(dir + "/main", sbTemp);
                for (String fileName : fileMainName) {
                    createFileInDir(new File(dir + "/main/" + fileName), fileName, sbTemp);
                }
                checkDir("src/test", sbTemp);
            }
            if (dir.equals("res")) {
                for (String mkdirName : dirResName) {
                    checkDir("res/" + mkdirName , sbTemp);
               }
            }
            if (dir.equals("temp")) {
                createFileInDir(new File("temp/temp.txt"),"temp.txt" , sbTemp);
                try (FileWriter writer = new FileWriter(dirPath + "temp/temp.txt", false)) {
                    writer.write(sbTemp.toString());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    public static void createFileInDir(File myFile, String fileName, StringBuilder sbTemp){
        try {
            if (myFile.createNewFile()) {
                System.out.println("Файл " + fileName + " был создан");
                sbTemp.append("Файл " + fileName + " был создан" + '\n');
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            sbTemp.append(ex.getMessage());
        }
    }

    public static void checkDir(String dir, StringBuilder sb){
        String TempName = dirPath + dir;
        File dirN = new File(TempName);
        if (dirN.mkdir()) {
            System.out.println("Создана директория " + TempName);
            sb.append("Создана директория " + TempName + '\n');
        } else {
            System.out.println("Не создана директория " + TempName);
            sb.append("Не создана директория " + TempName + '\n');
        }
    }
}