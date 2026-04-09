import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
public class MainDashboard extends JPanel
{
    ArrayList<Bug> bugs = new ArrayList<>(); 
    public MainDashboard()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton logButton = new JButton("View Log");
        
        logButton.addActionListener(new BugLogButton());



        JButton editButton = new JButton("Edit Bug");

        JButton addButton = new JButton("Add Bug");
        
        addButton.addActionListener(new NewBugButton());
        editButton.addActionListener(new BugEditButton());
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        this.add(logButton,c);
        c.gridx = 2;
        this.add(editButton,c);
        c.gridx = 4;
        this.add(addButton,c);
        this.setPreferredSize(new Dimension(300,100));


    }

    
    private class BugEditButton implements ActionListener
    {
        Bug current;
        private String[] convertToList(ArrayList<Bug> list)
        {
            if(list.isEmpty())
            {
                String[] x = {"No bugs here!"};
            }
            String[] out = new String[list.size()];
            for(int i = 0; i < out.length; i++)
                {
                    out[i] = list.get(i).getDescription();
                }
            
            return out;

        }

        private static Bug fetchBugByDescription(String description, ArrayList<Bug> bugs)
        {
            Bug ret = new NullBug();                
            for(Bug b : bugs)
                {
                    if(b.getDescription().equals(description))
                        return b;
                }
            return ret;
                
        }

        private void updateCurrent(Bug b)
        {
            current = b;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            JFrame bugEditWindow = new JFrame("Bug Editing");
            JPanel holder = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel selector = new JLabel("Select bug to edit: ");
            holder.add(selector,c);
            JButton selectArea = new JButton("Select Location");
            
            current = fetchBugByDescription(selectArea.getText(), bugs);
            c.gridwidth = 2;
            c. gridx = 2;
            holder.add(selectArea,c);
            bugEditWindow.add(holder);
            bugEditWindow.pack();
            bugEditWindow.setVisible(true);


            JPanel bugInfoHolder = new JPanel(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            holder.setPreferredSize(new Dimension(500,500));
            holder.add(bugInfoHolder,c);
            
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 8;
            
            JTextArea desc = new JTextArea();
            if(!(current instanceof NullBug) && current != null)
            {
                desc.setText(current.getDescription());
            }
            
            bugInfoHolder.add(desc,g);

            selectArea.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String toEdit = (String) JOptionPane.showInputDialog(
                        null,
                        "Select a Bug to Edit: ",
                        "BUG SELECTION",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        convertToList(bugs),
                        convertToList(bugs)[0]

                    );
                    selectArea.setText(toEdit);
                    updateCurrent(fetchBugByDescription(toEdit, bugs));
                    
                    
                    
                    
                }
            });
            

        }

    }

    private class BugLogButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JFrame bugViewWindow = new JFrame("Bug Log");
            JPanel holder = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            JLabel searchLabel = new JLabel("Filter by keyword: ");
            TextField searchField = new TextField(6);


            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 3;
            holder.add(searchLabel,c);
            c.gridx = 3;
            c.gridwidth = 6;
            holder.add(searchField,c);
            c.gridx = 0;
            c.gridy = 1;

            JPanel bugHolder = new JPanel(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            holder.setPreferredSize(new Dimension(500,500));
            holder.add(bugHolder,c);

            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 6;

            for(Bug b : bugs)
            {
                bugHolder.add(new BugRenderer(b),g);
                g.gridy+=4;
            }

            bugViewWindow.add(holder);
            bugViewWindow.setPreferredSize(new Dimension(500,500));
            bugViewWindow.pack();
            bugViewWindow.setVisible(true);

            searchField.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // TO FIX: potentially attempting to access larger substring than exists. Change sf.getText().length() to a case-based implementation: i.e. pick the shorter of the two
                    Predicate<Bug> bugPredicate= b -> b.getDescription().substring(0,searchField.getText().length()).compareTo(searchField.getText()) != 0;
                    ArrayList<Bug> filteredList = (ArrayList<Bug>) bugs.clone();
                    filteredList.removeIf(bugPredicate);
                    for(Bug b : filteredList)
                    {
                        bugHolder.add(new BugRenderer(b),g);
                        g.gridy+=3;
                    }


                }   


            });
            
        }
    }

    private class NewBugButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame bugInsertWindow = new JFrame("Bug Insertion Menu");
            JPanel holder = new JPanel(new GridBagLayout());
            GridBagConstraints mc = new GridBagConstraints();
            mc.gridwidth = 2;
            mc.gridx = 0;
            mc.gridy = 0;
            
            JLabel name = new JLabel("Please describe the encountered Bug: ");
            holder.add(name,mc);

            mc.gridx = 2;
            TextField namevalue = new TextField(6);
            holder.add(namevalue,mc);

            mc.gridwidth = 2;
            mc. gridx = 0;
            mc.gridy = 1;
            JLabel area = new JLabel("Where was this bug encountered?");
            holder.add(area,mc);
            JButton selectArea = new JButton("Select Location");
            selectArea.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String locations[] = {"Product Backlog", "Implementation A", "Test Case 2"};
                    String location = (String) JOptionPane.showInputDialog(
                        null,
                        "Please select encounter location: ",
                        "Locations below",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        locations,
                        locations[0]

                    );
                    selectArea.setText(location);
                }
            });
            mc.gridwidth = 2;
            mc. gridx = 2;
            holder.add(selectArea,mc);

            mc.gridwidth = 4;
            mc.gridy = 5;
            mc.gridheight = 2;
            
            JButton enter = new JButton("ENTER");
            enter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String[] options = {"YES", "NO"};
                    String selected = (String) JOptionPane.showInputDialog(
                        null,
                        """
                        Is this information correct?: 
                        Description:""" + namevalue.getText() + "\n\n" +
                        "Found in: " + selectArea.getText(),
                        "Submission Form",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        null,
                        options,
                        options[1]
                    );

                    if(selected.equals("YES"))
                        {
                            bugs.add(new Bug(namevalue.getText(), new User("user")));
                            bugInsertWindow.setVisible(false);
                        }
                    
                }
            });

            holder.add(enter,mc);
            
            bugInsertWindow.add(holder);
            bugInsertWindow.setPreferredSize(new Dimension(500,500));
            bugInsertWindow.pack();
            bugInsertWindow.setVisible(true);
        
        }
    }

    private class BugRenderer extends JPanel
    {
        public BugRenderer(Bug b)
        {
            this.setLayout(new GridBagLayout());
            this.setAlignmentX(LEFT_ALIGNMENT);
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.WEST;
            JLabel bugID = new JLabel("BUG ID: " + b.getID());
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 3;
            this.add(bugID,c);
            JLabel status = new JLabel("Status: " + b.getStatus());
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 3;
            this.add(status,c);
            // potentially change to JTextField?
            JLabel description = new JLabel(b.getDescription());
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 5;
            this.add(description,c);

        }

    }

}
