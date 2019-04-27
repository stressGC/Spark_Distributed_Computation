/**
 * @author Georges Cosson
 */
import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * utility class to write the JSON to the file system
 */
public class FSHelper {

    /**
     * writes a json to a specified file
     * @param path path of the file to write to
     * @param json json to write to this file
     * @throws IOException
     */
    public static void writeToFile(String path, JSONArray json) throws IOException {
        System.out.println(">> WRITING RESULT TO FILE : " + path);

        // instantiate writers
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // write to file and close
        printWriter.print(json.toString());
        printWriter.close();
    }
}
