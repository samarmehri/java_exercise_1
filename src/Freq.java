import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Freq implements Command {
    public static void freq2() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Not an ugly ass l&f
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.out.println("Couldnt change l&f :(");
        }
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        FileInputStream in = new FileInputStream(chooser.getSelectedFile());

        String a = new String(in.readAllBytes());

        String[] words = a.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        Arrays.stream(words).filter((str) -> !str.isBlank())
                .collect(Collectors.groupingBy((str) -> str)).entrySet().stream()
                .sorted(Comparator.comparingInt((e) -> -e.getValue().size())).limit(3)
                .forEach((str) -> System.out.print(str.getKey() + " "));
        in.close();
    }
    @Override
    public String name() {
        return "freq";
    }

    @Override
    public boolean run(Scanner sc) {
        System.out.println("Choose a file !");
        String chosen = "";
        try {
            chosen = sc.next();

            String a = Files.readString(Paths.get(chosen));
            String[] words = a.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
            Arrays.stream(words).filter((str) -> !str.isBlank())
                    .collect(Collectors.groupingBy((str) -> str)).entrySet().stream()
                    .sorted(Comparator.comparingInt((e) -> -e.getValue().size())).limit(3)
                    .forEach((str) -> System.out.print(str.getKey() + " "));
        } catch (IOException e) {
            System.out.println("Unreadable file : " + e.getClass() + " " + e.getMessage());
        }
        return false;
    }

}