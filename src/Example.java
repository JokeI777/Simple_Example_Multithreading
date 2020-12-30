import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.*;

//Return top names with their count met in members VK group
//Example group id: iritrtf_urfu
public class Example {
    public static Map<String, Integer> dictionary = new HashMap<>();
    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        VK_API api = new VK_API("first_name", "iritrtf_urfu");
        JSONArray json = api.getPopularNamesFromGroup();

        Thread t1 = new VK_Thread(json, "first_name");
        Thread t2 = new VK_Thread(json, "first_name");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        dictionary.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
    }

    static class VK_Thread extends Thread{
        public static JSONArray arrayMembers;
        public static String needInfo;
        public static Integer i = 0;

        public VK_Thread(JSONArray arrayMembers, String needInfo){
            this.arrayMembers = arrayMembers;
            this.needInfo = needInfo;
        }
        @Override
        public void run(){
            synchronized (VK_Thread.class) {
                for (; i < arrayMembers.length(); i++) {
                    String name = arrayMembers.getJSONObject(i).get(needInfo).toString();
                    if (dictionary.containsKey(name)) {
                        dictionary.put(name, dictionary.get(name) + 1);
                    } else {
                        dictionary.put(name, 1);
                    }
                }
            }
        }
    }
}