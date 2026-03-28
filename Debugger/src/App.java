import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
public class App
{   
    final static DisplayDatabase data = new DisplayDatabase();
    public static void main(String[] args) throws Exception
    {
        boolean active = false;
        Scanner in = new Scanner(System.in);
        String saved = "";
        while(active)
            {
                while(data.getCurrentUser() == null)
                    {
                        System.out.println("Please enter a username:");
                        saved = in.nextLine();
                        String user = saved;
                        System.out.println("Please enter a password:");
                        saved = in.nextLine();
                        
                        
                        
                    }
                saved = in.nextLine();
                switch(saved)
                {
                    case "ls":
                        data.printBugs();
                        break;
                    case "report":
                        System.out.println("Thank you for reporting a bug!\nPlease enter additional info below:");        
                        saved = in.nextLine();
                        data.recordBug(saved);
                        break;
                    case "resolve":
                        System.out.println("Please enter ID: ");
                        saved = in.nextLine();
                        data.resolveBug(saved);
                        break;
                    case "details":
                        System.out.println("Please enter ID: ");
                        saved = in.nextLine();
                        saved = data.getDetails(saved);
                        System.out.println(saved);
                        break;
                    case "exit":
                        active = false;
                        break;

                }
            }
        in.close();

        JFrame j = new JFrame("Display Database");
        MainDashboard m = new MainDashboard();
        j.add(m);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.pack();
        JScrollPane scroll = new JScrollPane(m);
        j.add(scroll);
        j.setVisible(true);


    }
}
