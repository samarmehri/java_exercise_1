import java.util.Scanner;

public class Launcher {
    public static final String BIENVENUE= "WELCOME! :)";
    public static final Command[] COMMANDS = {new Fibo(), new Freq(), new Predict(), new Quit()};


    public static void main(String[] args) {
        System.out.println(BIENVENUE);
        Scanner sc = new Scanner(System.in);
        String variable = "";
        Command cmd = null;
        do {
            variable = sc.nextLine();
            cmd = null;
            for(Command c : COMMANDS) if(c.name().contentEquals(variable)) {
                cmd = c;
                break;
            }
            if(cmd == null) {
                System.out.println("Unkown command");
            }
        } while(cmd == null || !cmd.run(sc));
        sc.close();
    }
}
