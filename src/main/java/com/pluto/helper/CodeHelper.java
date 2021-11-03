package com.pluto.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class CodeHelper {

    public static List<String> readFromFile(String fileName) {
        LogUtils.log("readFromFile:" + fileName);
        File targetFile = new File(fileName);
        if (!targetFile.exists()) {
            return Collections.emptyList();
        }
        List<String> contents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
        } catch (Exception ignored) {
        }
        return contents;
    }

    public static void writeToFile(String fileName, List<String> contents) {
        File targetFile = new File(fileName);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException ignored) {
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)))) {
            for (String content : contents) {
                writer.write(content);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getCodeDataHomePath() {
        return "./codeData";
        //return "/Users/kevin/Works/github/project/gold_code/codeData";
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                deleteFile(subFile);
            }
        }
        file.delete();
    }

}
