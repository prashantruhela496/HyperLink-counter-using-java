import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperlinkManager {
    private static final String HTML_A_HREF_TAG_PATTERN =
            "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
    private static Pattern pattern;
    public  static Set<String> visitedUrls = new HashSet<String>();

    public HyperlinkManager() {
        pattern = Pattern.compile(HTML_A_HREF_TAG_PATTERN);
    }

    private static void fetchContentFromURL(String strLink) {
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
        System.out.println("These are the fetched url from the given website:\n" + visitedUrls);
    }

    private static void fetchURL(String content) {
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println("No of Hyperlinks found: "+group.length());
            System.out.println("These are the links:");
            System.out.println("lINK " + group);
        }
    }

    public static void showLinks(String webLink) {
        fetchContentFromURL(webLink);
    }
}