/*
1  Fetch all contents from a Webpage
2. fetch hyperlinks from the webpage.
3. Repeat the 1 & 2 from the fetched hyperlink
4. repeat the process untill 200 hyperlinks regietered or no more hyperlink to fetch.
*/

import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Content {
    private static final String HTML_A_HREF_TAG_PATTERN =
            "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
    private Pattern pattern;
    private Set<String> visitedUrls = new HashSet<String>();

    public Content() {
        pattern = Pattern.compile(HTML_A_HREF_TAG_PATTERN);
    }

    private void fetchContentFromURL(String strLink) {
        String content = null;
        URLConnection connection = null;
        try {
            connection = new URL(strLink).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            if (scanner.hasNext()) {
                content = scanner.next();
                visitedUrls.add(strLink);
                fetchURL(content);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("These are the fetched url from the given website:\n"+visitedUrls);
    }

    private void fetchURL(String content) {
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group = matcher.group();
            if (group.toLowerCase().contains("http") || group.toLowerCase().contains("https")) {
                group = group.substring(group.indexOf("=") + 1);
                group = group.replaceAll("'", "");
                group = group.replaceAll("\"", "");
                System.out.println("lINK " + group);
                if (!visitedUrls.contains(group) && visitedUrls.size() < 200) {
                    fetchContentFromURL(group);
                }
            }
        }
        System.out.println("DONE");
    }

    public static void main(String[] args) {
        new Content().fetchContentFromURL("https://felight.io/");
    }

}