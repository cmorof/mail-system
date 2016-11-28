/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    // The state on/off of the automatic message.
    private boolean state;
    // The subject of the automatic message.
    private String newSubject;
    // The message of the automatic message.
    private String newMessage;
    
    private MailItem lastMail;

    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        state = false;
        newSubject = "";
        newMessage ="";
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        if (item != null)
        {
            lastMail = item;
        }
        
        if (state && item != null)
        {
            sendMailItem(item.getFrom(), newSubject, newMessage);
        }
        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = getNextMailItem();
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String subject, String message)
    {
        MailItem item = new MailItem(user, to, subject, message);
        server.post(item);
    }
    
    public int numberOfMails()
    {
        return server.howManyMailItems(user);
    }

    public void setAutomaticReply(String subjectAuto, String messageAuto)
    {
        newSubject = subjectAuto;
        newMessage = messageAuto;
        
        state = !state;
    }
    
    public void printLastMail()
    {
        if (lastMail == null){
            System.out.println("No new mail.");
        }
        else{
            lastMail.print();
        }
    }
}
