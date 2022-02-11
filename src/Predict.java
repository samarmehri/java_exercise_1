import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Predict implements Command {

    public String name() {
        return "predict";
    }


    public boolean run(Scanner sc) {
        System.out.println("Choose a file !");
        String chosen = "";
        try {
            chosen = sc.nextLine();

            String a = Files.readString(Paths.get(chosen));
            String[] words = a.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");

            Map<String, Map<String, Integer>> m = new HashMap<>();
            for (int i = 0; i < words.length - 1; i++) {
                String str = words[i],
                        next = words[i + 1];
                Map<String, Integer> m2 = m.get(str);
                if (m2 == null) {
                    m2 = new HashMap<>();
                    m.put(str, m2);
                    m2.put(next, 1);
                    continue;
                }
                Integer j = m2.get(next);
                if (j == null)
                    m2.put(next, 1);
                else
                    m2.replace(next, j + 1);
            }
            System.out.println("Enter a word : ");
            chosen = sc.nextLine();
            if (!m.containsKey(chosen)) {
                System.out.println("This word is not in the text !");
                return false;
            }
            String next = chosen;
            StringBuilder builder = new StringBuilder();
            int length = 1;
            while (m.containsKey(next) && length++ < 20) {
                Map<String, Integer> val = m.get(next);
                Integer value = 0;
                m.remove(next); // Avoid infinite loops
                builder.append(" " + next);
                for (Entry<String, Integer> e : val.entrySet()) {
                    if (e.getValue() > value) {
                        next = e.getKey();
                        value = e.getValue();
                    }
                }
            }
            builder.append(' ' + next);
            System.out.println("Most common sentence :" + builder.toString());
        } catch (IOException e) {
            System.out.println("Unreadable file : " + e.getClass() + " " + e.getMessage());
        }
        return false;
    }
}