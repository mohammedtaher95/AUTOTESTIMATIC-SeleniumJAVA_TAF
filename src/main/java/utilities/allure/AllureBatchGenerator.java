package utilities.allure;

import utilities.LoggingManager;

import java.io.*;
import java.nio.file.*;

public class AllureBatchGenerator {

    private AllureBatchGenerator(){

    }
    public static void generateBatFile(){
        Path file = Paths.get("generateAllureReport.bat");
        if(!Files.exists(file)){
            try {
                Files.writeString(file,"@echo off\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.writeString(file,"allure serve target/allure-results\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.writeString(file,"pause\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.writeString(file,"exit", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                LoggingManager.error("Unable to create Allure Batch File" + e.getMessage());
            }
        }
    }
}
