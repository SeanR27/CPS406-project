import java.util.ArrayList;
public class DisplayDatabase
{
    
    User current = new User("System");
    ArrayList<Bug> bugs = new ArrayList<>(); 
    public DisplayDatabase()
    {

    }
    public void recordBug(String description)
    {
        bugs.add(new Bug(description, current));
    }
    public void printBugs()
    {
        System.out.println("Current bugs logged in system: ");
        if(bugs.isEmpty())
            System.out.println("No bugs found :)!");
        for(Bug b : bugs)
            {
                System.out.println(b);
            }
    }
    private Bug findBug(int id)
    {
        Bug bug = new NullBug();

        for(Bug b : bugs)
            {
                if(b.getID() == id)
                    bug = b;

            }
        return bug;

    }

    public void resolveBug(String StringID)
    {
       int id;
        try
        {
            id = Integer.parseInt(StringID);
            Bug target = findBug(id);
            if(!(target instanceof NullBug))
                {
                    target.resolve();
                    System.out.println("Target successfully resolved!");
                }

        }
        catch(NumberFormatException e)
        {
            System.out.println("Invalid ID.");
            
        }
    }

    public String getDetails (String StringID)
    {
       int id;
        try
        {
            id = Integer.parseInt(StringID);
            Bug target = findBug(id);
            if(!(target instanceof NullBug))
                {
                    String toReturn = target.getDescription() + " reported by: " + target.getReporter().getName() + " at " + target.getTimeRecorded().toString();
                    toReturn = target.isResolved() ? toReturn : toReturn + " resolved at " + target.getTimeFixed().toString();
                    return toReturn;
                }
            return "FETCH FAILURE";

        }
        catch(NumberFormatException e)
        {
            return "FETCH FAILURE";
        }
    }

    public void signIn(String user, String pass)
    {

    }
    public void setCurrentUser(User u)
    {
        this.current = u;
    }
    public User getCurrentUser()
    {
        return this.current;
    }
}