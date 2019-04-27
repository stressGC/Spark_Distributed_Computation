/**
 * @author Georges Cosson
 */
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * class made to crawl an entity
 */
public class EntityCrawler {

    /* attributes */
    private JSONHelper jsonHelper = new JSONHelper();
    private ArrayList<String> urls;

    /* constructor */
    public EntityCrawler(ArrayList<String> urls) {
        this.urls = urls;
    }

    /**
     * launches entity crawler
     * @param {ArrayList} contains all urls to crawl
     */
    public JSONArray crawl() {
        JSONArray crawlingResults = new JSONArray();

        System.out.println(">> CRAWLING STARTED FOR " + this.urls.size() + " URLs");

        // fetch all urls
        for(String url : this.urls) {
            JSONObject entity = this.crawlOne(url);

            // check for null result
            if(entity != null) {
                crawlingResults.put(entity);
            }
        }

        return crawlingResults;
    }

    /**
     * crawls one URL
     * @param url
     * @return a JSON with entity informations
     */
    private JSONObject crawlOne(String url) {
        try {
            // get html from the page
            Document html = Jsoup.connect(url).get();

            // let's retrieve the entity's informations
            String name = fetchName(html); // name
            ArrayList<String> spells = fetchSpells(html, name); // spells

            // check for for empty names (happens on secondary bestiaries)
            if(name.equals("")) {
                return null;
            }

            // return formatted result
            return this.jsonHelper.entityAsJSON(name, spells);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * extracts the entity name from the html
     * @param html
     * @return entity name
     */
    private String fetchName(Document html) {
        // html / css queries used
        String regularQuery = ".stat-block-title > b";
        String emptyQuery = ".stat-block-title";

        Elements entity = html.select(regularQuery);

        // check for empty
        if (entity.isEmpty()) {
            entity = html.select(emptyQuery);
        }

        /* remove noisy text */
        entity.select(".stat-block-cr").remove();
        return entity.text();
    }

    /**
     * extracts the spells from the html
     * @param html
     * @param entityName
     * @return spells
     */
    public ArrayList<String> fetchSpells(Document html, String entityName) {
        String query = ".stat-block-title, .stat-block-breaker:contains(Offense) ~ .stat-block-2 a";
        Elements elems = html.select(query);

        ArrayList<String> spells = new ArrayList<>();
        boolean hasResult = false;

        // lets get all the spells for the current entity
        for (Element element : elems) {

            // clean the array
            element.select(".stat-block-cr").remove();

            // check for empty classNames
            if (!element.classNames().isEmpty()) {
                if (hasResult) {
                    return spells;
                }
                if (element.text().equals(entityName)) {
                    hasResult = true;
                }
            }

            // if element is a link tag, and we got a result
            if (element.tag().toString().equals("a") && hasResult) {
                // then add the element to spells
                spells.add(element.text());
            }
        }

        return spells;
    }
}
