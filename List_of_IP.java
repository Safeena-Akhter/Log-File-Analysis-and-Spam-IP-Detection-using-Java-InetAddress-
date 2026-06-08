import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class List_of_IP {

    public static final String BLACKHOLE = "sbl.spamhaus.org";

    static Map<String, Integer> ipCount = new HashMap<>();


    static Set<String> spamIPs = new HashSet<>();

    public static void main(String[] args) {

        File folder = new File("logs");

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("Log folder not found!");
            return;
        }

    
        Pattern ipPattern =
                Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");

        // Read all log files
        for (File file : files) {

            if (!file.getName().endsWith(".log"))
                continue;

            System.out.println("Processing: " + file.getName());

            try (BufferedReader br =
                         new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = br.readLine()) != null) {

                    Matcher matcher = ipPattern.matcher(line);

                    while (matcher.find()) {

                        String ip = matcher.group();

                        // COUNT 
                        ipCount.put(ip,
                                ipCount.getOrDefault(ip, 0) + 1);

                        // skip local IPS
                        if (isLocalIP(ip))
                            continue;

                        // Spam check
                        if (isSpammer(ip)) {
                            spamIPs.add(ip);
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Save spam IPs to file
        saveSpamIPs();

        //display sorted IPs by access count
        printSortedIPs();
    }
   // IS LOCAL IP CHECK
    public static boolean isLocalIP(String ip) {

        try {

            InetAddress address = InetAddress.getByName(ip);

            return address.isAnyLocalAddress()
                    || address.isLoopbackAddress()
                    || address.isSiteLocalAddress()
                    || address.isLinkLocalAddress();

        } catch (Exception e) {
            return true;
        }
    }

      //IS SPAMMER CHECK
    public static boolean isSpammer(String ip) {

        try {

            InetAddress address = InetAddress.getByName(ip);
            byte[] quad = address.getAddress();

            String query = BLACKHOLE;

            // Reverse IP required to check SPAMMER
            for (byte b : quad) {

                int unsigned = b < 0 ? b + 256 : b;

                query = unsigned + "." + query;
            }

            // DNS lookup
            InetAddress.getByName(query);

            return true; // IF FOUND

        } catch (UnknownHostException e) {
            return false; // not spam
        }
    }

    //SAVE INTO FILE
    public static void saveSpamIPs() {

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter("spam_ips.txt"))) {

            for (String ip : spamIPs) {
                pw.println(ip);
            }

            System.out.println("\nSpam IPs saved to spam_ips.txt");

        } catch (Exception e) {
            System.out.println("Error saving spam IPs: " + e.getMessage());
        }
    }

 //SORT IP ACCESS COUNT IN DESCENDING ORDER
    public static void printSortedIPs() {

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(ipCount.entrySet());

        // Sort descending
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        System.out.println("\nIP ACCESS COUNT  in descending order:");

        for (Map.Entry<String, Integer> entry : list) {

            System.out.println(entry.getKey()
                    + "  -->  "
                    + entry.getValue());
        }
    }
}