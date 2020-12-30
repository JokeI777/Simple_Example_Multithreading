import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VK_API {
    public static String needInfo;
    private static String groupID;

    public VK_API(String needInfo, String groupID){
        this.needInfo = needInfo;
        this.groupID = groupID;
    }
    public static JSONArray getPopularNamesFromGroup() throws IOException {
        //Enter ur token VK!
        Scanner scanner = new Scanner(Paths.get("C:\\go\\Token_Vk_api.txt"));
        String token = scanner.nextLine();
        String api = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=" + groupID +
                "&fields=city&access_token=" +
                token;
        String membersInfo = getMembersInfo(api);
        return getPopular(membersInfo);
    }

    private static String getMembersInfo(String api) throws IOException {
        URL url = new URL(api);
        URLConnection c = url.openConnection();
        Scanner scanner = new Scanner(new BufferedReader(
                new InputStreamReader(
                        c.getInputStream())));

        StringBuilder s = new StringBuilder();
        while (scanner.hasNextLine()) {
            s.append(scanner.nextLine());
        }
        scanner.close();
        return s.toString();
    }

    private static JSONArray getPopular(String members){
        JSONObject json = new JSONObject(members);
        JSONArray arrayMembers = json.getJSONObject("response").getJSONArray("items");
        return arrayMembers;
    }
}
