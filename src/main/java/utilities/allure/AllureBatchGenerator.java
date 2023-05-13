package utilities.allure;

import java.io.*;
import java.nio.file.*;

public class AllureBatchGenerator {

    private AllureBatchGenerator(){

    }
    public static void generateBatFile() throws IOException {
        Path file = Paths.get("generateAllureReport.bat");
        if(!Files.exists(file)){
            Files.writeString(file,"@echo off\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            Files.writeString(file,"allure serve target/allure-results\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            Files.writeString(file,"pause\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            Files.writeString(file,"exit", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }
}
