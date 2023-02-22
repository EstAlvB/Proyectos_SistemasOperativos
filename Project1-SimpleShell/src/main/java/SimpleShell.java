/**
 * @author gabriel-lara
 * @author alexander-pastor
 * @author esteban-alvarado
 */
import java.io.*;
import java.util.Scanner;

public class SimpleShell {
    public static void main(String[] args) {
        
        String user = System.getProperty("user.name");
        String currentPath = System.getProperty("user.dir");
        History commandHistory = new History();
        File dir = new File(currentPath);
        
        while (true) {
            ShowInterface(currentPath + "\n|" + user + "|" + " > ");
            Scanner sc = new Scanner(System.in);
            String request = sc.nextLine();
            
            if (request.equals(""))
                continue;
            if (request.equals("exit")) {
                showMessage("Goodbye");
                System.exit(0);
            }
            if (request.equals("history")) {
                showMessage(commandHistory.getHistory());
                continue;
            }
            if (request.startsWith("!") && request.length() == 2) {
                showMessage(commandHistory.getCommand(request));
                continue;
            } else {
                commandHistory.addCommand(request);
            }
                
            Processor p = new Processor(request);
            String operation = p.prepare();
            Command c = new Command(p.getCommandParts(), dir);
            showMessage(c.executeTask(operation));
            dir = c.getDir();
            currentPath = c.getCanonicalPath();
        }
    }
    
    public static void ShowInterface(String message) {
        System.out.print("-----------------------------------------------------------\n" + message);
    }
    
    public static void showMessage(String message) {
        System.out.println(message);
    }
}
