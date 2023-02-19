import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Command {

    private ProcessBuilder pb;
    ArrayList<String> commandParts;
    File dir;

    public Command(ArrayList<String> commandParts, File dir) {
        this.commandParts = commandParts;
        pb = new ProcessBuilder(commandParts);
        this.dir = dir;
    }

    public String setTask(String operation) {
        if (operation.equals("cd")) {
            if (commandParts.size() < 2) {
                return "not a valid cd command, try with a new one ";
            }
            String newPath = commandParts.get(1).replaceAll("^\"|\"$", "");
            Path p = Paths.get(newPath);
            File newDir;
            if (p.isAbsolute())
                newDir = new File(newPath);
            else
                newDir = new File(dir, newPath);

            if (newDir.exists() || newDir.isDirectory()) {
                dir = newDir;
            }
        }else if (operation.startsWith("ping ")){
            String text= operation.substring(5);
            try{
            Process pingCommand= Runtime.getRuntime().exec("ping "+ text);
            BufferedReader pingReader= new BufferedReader(new InputStreamReader(pingCommand.getInputStream()));
            String temp="";
            while (( temp = pingReader.readLine())!= null){
                System.out.println(temp);
            }
            }catch (java.io.IOException e) {
            System.out.print(e.getMessage());
        }        
        }
        return "";          
    }
    
    public String executeTask() {
        String result = "";
        try {
            pb.directory(dir);
            Process process = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String ln;
            while ((ln = br.readLine()) != null)
                result = ln;
            br.close();
        } catch (java.io.IOException e) {
            result = e.getMessage();
        }
        return result;
    }

    public String getCanonicalPath() {
        String p = "";
        try {
            p = dir.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public File getDir() {
        return dir;
    }
   
}