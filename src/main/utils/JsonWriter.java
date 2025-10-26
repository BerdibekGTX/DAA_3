package utils;

import org.json.simple.JSONObject;
import java.io.FileWriter;

public class JsonWriter {
    public static void saveToFile(JSONObject obj, String path) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());
            System.out.println("✅ JSON saved: " + path);
        } catch (Exception e) {
            System.err.println("❌ Error saving JSON: " + e.getMessage());
        }
    }
}
