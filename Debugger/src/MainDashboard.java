import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class MainDashboard extends JPanel
{
    ArrayList<Bug> bugs = new ArrayList<>(); 
    public MainDashboard()
    {
        bugs.add(new Bug("test1", new User("JOHN DOE")));
        bugs.add(new Bug("Strange bump on the road", new User("JANE DOE")));
        bugs.add(new Bug("Odd failure in system A", new User("Undefined")));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton logButton = new JButton("View Log");
        JButton editButton = new JButton("Edit Bug");
        JButton viewButton = new JButton("Add Bug");
        JLabel searchLabel = new JLabel("Filter by keyword: ");
        TextField searchField = new TextField(6);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(logButton,c);
        c.gridx = 1;
        c.gridx = 3;
        this.add(editButton,c);
        c.gridx = 5;
        this.add(viewButton,c);
        c.gridx = 7;
        this.add(searchLabel,c);
        c.gridx = 12;
        c.gridwidth = 6;
        this.add(searchField,c);

        searchField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TO FIX: potentially attempting to access larger substring than exists. Change sf.getText().length() to a case-based implementation: i.e. pick the shorter of the two
                Predicate<Bug> bugPredicate= b -> b.getDescription().substring(0,searchField.getText().length()).compareTo(searchField.getText()) != 0;
                ArrayList<Bug> filteredList = (ArrayList<Bug>) bugs.clone();
                filteredList.removeIf(bugPredicate);



            }   


        });
        
        
        JPanel bugListHolder = new JPanel();
        bugListHolder.setLayout(new GridBagLayout());
        GridBagConstraints internal = new GridBagConstraints();
        internal.gridx = 0;
        internal.gridy = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 9;
        this.add(bugListHolder,c);
        for(Bug b : bugs)
            {
                bugListHolder.add(new BugRenderer(b),internal);
                internal.gridy++;
            }

    }


    private class BugRenderer extends JPanel
    {
        public BugRenderer(Bug b)
        {
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            JLabel bugID = new JLabel("BUG ID: " + b.getID());
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            this.add(bugID,c);
            JLabel status = new JLabel("Status: " + b.getStatus());
            c.gridx = 3;
            c.gridy = 0;
            c.gridwidth = 2;
            this.add(status,c);
            // potentially change to JTextField?
            JLabel description = new JLabel(b.getDescription());
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 2;
            this.add(description,c);

        }

    }
}
