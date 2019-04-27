/**
 * @author Georges Cosson
 */

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

/**
 * entry point of our program
 */
public class Main {
    private static String RESULT_PATH = "entities.json";

    public static void main(String [] args)
    {
        /* lets crawl the http://legacy.aonprd.com  website */
        /* here all the different urls we found */
        String[] urls = {
                "http://legacy.aonprd.com/bestiary/monsterIndex.html",
                "http://legacy.aonprd.com/bestiary2/additionalMonsterIndex.html",
                "http://legacy.aonprd.com/bestiary4/monsterIndex.html",
                "http://legacy.aonprd.com/bestiary3/monsterIndex.html",
        };

        // lets create our URL crawler
        URLCrawler urlCrawler = new URLCrawler(urls);

        // we now have all the URLs to scrap
        ArrayList<String> allURLs = urlCrawler.crawl();

        // let's scrap all these URLs
        EntityCrawler eCrawler = new EntityCrawler(allURLs);
        JSONArray result = eCrawler.crawl();

        // let's save it as a file
        try {
            FSHelper.writeToFile(RESULT_PATH, result);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
