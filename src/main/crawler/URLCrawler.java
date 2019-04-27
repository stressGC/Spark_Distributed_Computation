import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class URLCrawler {
    /* attributes */
    private String url;
    private String[] urls;
    private boolean isMultipleURL;

    public URLCrawler(String url) {
        this.url = url;
        this.isMultipleURL = false;
    }

    public URLCrawler(String[] urls) {
        this.urls = urls;
        this.isMultipleURL = true;
    }

    public ArrayList<String> crawl() {
        if(this.isMultipleURL) {
            ArrayList<String> results = new ArrayList();
            for (String url : this.urls) {
                ArrayList<String> partialResult = this.getAllLinks(url);
                results.addAll(partialResult);

                System.out.println(">> ADDING " + partialResult.size() + " NEW LINKS (" + results.size() + " TOTAL) - " + url);
            }

            return results;
        } else {
            return this.getAllLinks(url);
        }
    }

    private ArrayList getAllLinks(String url) {
        ArrayList links = new ArrayList();
        Document doc = null;

        try {
            doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements newsHeadlines = doc.select("#monster-index-wrapper ul li");

        for (Element headline : newsHeadlines) {
            String headlineChar = headline.toString();
            String test = Character.toString(headlineChar.charAt(4));
            if (test.equals("<")) {
                Element tag = Jsoup.parse(headlineChar, "", Parser.xmlParser());
                String urlFetched = this.formatURL(url) + "/" + tag.select("a").attr("href");

                links.add(urlFetched);
            }
        }

        return links;
    }

    private static String formatURL(String url) {
        int index = url.lastIndexOf('/');
        return url.substring(0,index);
    }
}
