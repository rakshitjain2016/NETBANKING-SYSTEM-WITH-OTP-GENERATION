/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*; 
import java.util.Random;
/**
 *
 * @author rjrak
 */
public class Banking {
    //KYC, user_log, acc_det, acc_det_full
    /**
     * @param args the command line arguments
     */
    public long gen_AccountNum(){
        long accno = 0;
        try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","rakshit");
        Statement st= con.createStatement();
        int count = 0;
        do{
            Random r=new Random();
            accno=(long)(r.nextDouble()*(9999999999999999L - 1000000000000000L));
            PreparedStatement stmt=con.prepareStatement("SELECT ACCNO FROM KYC WHERE ACCNO=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setLong(1, accno);
            ResultSet rs=stmt.executeQuery();
            rs.last();
            count=rs.getRow();
        }while(count>0);
        con.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return accno;
    }
    
    public long SendMailbySite(String emailto){
        long otp=0; 
        final String user="from email";//change accordingly  
        final String password="password of from email";//change accordingly  
    
        String to=emailto;//change accordingly  
       //Get the session object  
        Properties props = new Properties();  
        props.put("mail.smtp.host","smtp.gmail.com");  
        props.put("mail.smtp.port","587");  
        props.put("mail.smtp.starttls.enable","true");  
        props.put("mail.smtp.auth", "true");  
     
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(user,password);  
            }  
        });  
  
   //Compose the message  
    try {  
     Random r=new Random();
     otp=(long)(r.nextDouble()*(999999L - 100000L));
            
     MimeMessage message = new MimeMessage(session);  
     message.setFrom(new InternetAddress(user));  
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
     message.setSubject("OTP VIT NETBANKING");  
     message.setText("This is your OTP"+otp);  
       
    //send the message  
     Transport.send(message);  
  
     System.out.println("message sent successfully...");  
     } 
    catch (MessagingException e) 
    {
        JOptionPane.showMessageDialog(null, e);
    }
    return otp;
    }

    public void SendMailbySiteText(String emailto, String username){
        long otp=0; 
        final String user="from email";//change accordingly  
        final String password="password of from email";//change accordingly  
    
        String to=emailto;//change accordingly  
       //Get the session object  
        Properties props = new Properties();  
        props.put("mail.smtp.host","smtp.gmail.com");  
        props.put("mail.smtp.port","587");  
        props.put("mail.smtp.starttls.enable","true");  
        props.put("mail.smtp.auth", "true");  
     
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(user,password);  
            }  
        });  
  
   //Compose the message  
    try {  
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","rakshit");
        Statement st= con.createStatement();
        PreparedStatement stmt= con.prepareStatement("SELECT * FROM USER_LOG WHERE USERNAME=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);
        ResultSet rs=stmt.executeQuery();
        rs.absolute(1);
           
        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject("OTP VIT NETBANKING");  
        message.setText("This is your Password:  "+rs.getString("PASSWORD"));  
       
        //send the message  
        Transport.send(message);  
  
        System.out.println("message sent successfully..."); 
        } 
        catch (MessagingException e) 
        {
             JOptionPane.showMessageDialog(null, e);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);    
        }
        return;
    }
            
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    
}
