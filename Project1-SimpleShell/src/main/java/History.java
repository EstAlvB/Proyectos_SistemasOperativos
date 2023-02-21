import java.util.ArrayList;

public class History {
    private ArrayList<String> list;
    private int n;

    public History() {
        list = new ArrayList<String>(10);
        n = 0;
    }

    public String getHistory() {
        if (!list.isEmpty()) {
            String out = "here is a list of your commands:\nEnter !<number> to get your command.\n";
            int i = 1;
            for (String command : list) {
                out += i + ") " + command + "\n";
                i++;
            }
            return out;
        }
        return "no history available!";
    }

    public void addCommand(String cmd) {
        list.add(cmd);
        n++;
    }

    public String lastcommand() {
        if (!list.isEmpty())
            return list.get(n - 1);
        return null;
    }

    public String get_command(int number) {
        if (number > 0 && number <= n)
            return list.get(number - 1);
        return null;
    }
}
