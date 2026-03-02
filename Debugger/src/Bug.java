import java.util.*;
public class Bug
{
    final private Date recorded;
    private Date fixed;
    private String description;
    private User reporter;
    final private int ID;
    private static int count = 0;

    public Bug(String description, User reporter)
    {
        //this.source = source;
        setReporter(reporter);
        this.recorded = new Date();
        this.description = description;
        count++;
        this.ID = count;
    }

    @Override
    public String toString()
    {
        String out = "Bug ID " + ID + " reported at " + recorded;
        if(fixed != null)
            out += " | RESOLVED at " + fixed;
        return out;
        
    }
    
    public Date getTimeRecorded()
    {
        return this.recorded;
    }
    
    public Date getTimeFixed()
    {
        return fixed;
    }

    public void resolve()
    {
        this.fixed = new Date();
    }

    public void updateDescription(String description)
    {
        this.description = description;
    }

    public int getID()
    {
        return this.ID;
    }

    public boolean isResolved()
    {
        return this.fixed == null;
    }

    private void setReporter(User u)
    {
        this.reporter = u;
    }

    public String getDescription()
    {
        return description;
    }

    public User getReporter()
    {
        return this.reporter;
    }
}
