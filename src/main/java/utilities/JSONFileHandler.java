package utilities;

import io.restassured.path.json.JsonPath;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JSONFileHandler {

    private final String jsonFilePath;
    private static final ThreadLocal<FileReader> jsonFileReader = new ThreadLocal<>();
    public JSONFileHandler(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
    }

    private String cleanJsonPath(String jsonPath) {
        if (jsonPath.startsWith("x.")) {
            return jsonPath.replace("x.", "");
        } else {
            return jsonPath;
        }

    }
    public String getData(String jsonPath){
        Object data = getData(cleanJsonPath(jsonPath), DataType.STRING);
        if(data != null){
            return String.valueOf(data);
        }
        else {
            return null;
        }
    }

    public List<?> getDataAsList(String jsonPath){
        Object data = getData(cleanJsonPath(jsonPath), DataType.LIST);
        if(data != null){
            return (List<?>) data;
        }
        else {
            return Collections.emptyList();
        }
    }

    public Map<?,?> getDataAsMap(String jsonPath){
        Object data = getData(cleanJsonPath(jsonPath), DataType.MAP);
        if(data != null){
            return (Map<?, ?>) data;
        }
        else {
            return Collections.emptyMap();
        }
    }

    private Object getData(String jsonPath, DataType type){
        Object data = null;
        initializeFileReader();
        switch (type){
            case STRING -> data = JsonPath.from(jsonFileReader.get()).getString(jsonPath);
            case LIST -> data = JsonPath.from(jsonFileReader.get()).getList(jsonPath);
            case MAP -> data = JsonPath.from(jsonFileReader.get()).getMap(jsonPath);
        }
        return data;
    }

    private void initializeFileReader(){
        try {
            jsonFileReader.set(new FileReader(System.getProperty("user.dir")
                    + "/src/test/resources/testDataFiles/" + jsonFilePath, StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            LoggingManager.error("JSON File not Found", e);
        } catch (IOException e) {
            LoggingManager.error("JSON File didn't match the required format", e);
        }
    }

    public void removeReader(){
        jsonFileReader.remove();
    }


    public enum DataType{
        STRING,
        LIST,
        MAP
    }
}
