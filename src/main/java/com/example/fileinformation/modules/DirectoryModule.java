package com.example.fileinformation.modules;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@Component
public class DirectoryModule extends Module {
    @Override
    public boolean isSuitableExtension(File file) {
        return file.isDirectory() && file.exists();
    }
    
    @Override
    public String getDescription() {
        return "files - Список файлов\n" +
                "size - Размер всех файлов\n" +
                "modify - последняя модификация файла";
    }
    
    @Override
    public void executeCommand(String command, File file) {
        switch (command.toLowerCase()) {
            case "files":
                printFiles(file);
                break;
            case "size":
                printSize(file);
                break;
            case "modify":
                printModify(file);
                break;
        }
    }
    
    private void printFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    private void printSize(File directory) {
        System.out.println(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(directory)));
    }

    private void printModify(File directory) {
        try {
            BasicFileAttributes attr = Files.readAttributes(directory.toPath(), BasicFileAttributes.class);
            System.out.println(attr.lastModifiedTime());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
