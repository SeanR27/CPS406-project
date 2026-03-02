public class User
{
    final private String name;
    private String pass;

    public User(String name)
    {
        this.name = name;
        this.pass = "";
    }

    
    public String getName()
    {
        return this.name;
    }
    public boolean test(String pass)
    {
        return this.pass.equals(pass);
    }

    public void changePassword(String oldpass, String newpass)
    {
        if(test(oldpass))
            setPassword(newpass);
    }

    private void setPassword(String newPass)
    {
        this.pass = newPass;
    }
    



}
