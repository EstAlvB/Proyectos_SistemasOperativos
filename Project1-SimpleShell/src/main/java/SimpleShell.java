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
        File dir = new File(currentPath);
        
        while (true) {
            ShowInterface(currentPath + "\n|" + user + "|" + " > ");
            showMessage("\b");
            Scanner sc = new Scanner(System.in);
            String request = sc.nextLine();
            

            if (request.equals(""))
                continue;
            if (request.equals("exit")) {
                showMessage("Goodbye");
                System.exit(0);
            }
            
            if (request.equals("ls")){
                File actual_dir=new File(".");
                File[] files = actual_dir.listFiles();
                for (File file:files){
                    showMessage(file.getName());
                }
            
                }

                if (request.startsWith("echo ")){
                    String [] stringsDiv=request.split(" ");
                    if (stringsDiv.length>1){
                        String text=request.substring(5);
                        showMessage("Message from echo: " + text);
                    } else {
                        showMessage("Not enought arguments for echo");
                    }      
                }
                
            

            Processor p = new Processor(request);
            String operation = p.prepare();
            Command c = new Command(p.getCommandParts(), dir);
            showMessage(c.setTask(operation));
            dir = c.getDir();
            showMessage(c.executeTask());
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
