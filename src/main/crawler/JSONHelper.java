/**
 * @author Georges Cosson
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * utility class to work with JSONs
 */
public class JSONHelper {

    public JSONHelper() {}

    /**
     * saves entity in a JSON format
     */
    public JSONObject entityAsJSON(String entityName, ArrayList<String> entitySpells) {

        // create the JSON object
        JSONObject entity = new JSONObject();

        /* put name */
        entity.put("name", entityName);

        /* put spells */
        JSONArray spellsAsJSON = new JSONArray();
        for(String spell : entitySpells) {
            spellsAsJSON.put(spell);
        }

        /* add the spells */
        entity.put("spells", spellsAsJSON);

        return entity;
    }
}
