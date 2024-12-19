package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;

public class Contact{
    private Integer contactId;
    private User user;
    private String contact;
    private ContactType contactType;

    public Contact(){}

    public Contact(User user) {
        this.user = user;
    }

    public Contact(String contact) {
        this.contact = contact;
    }

    public Contact(String contact, ContactType contactType) {
        this.contact = contact;
        this.contactType = contactType;
    }

    public Contact(User user, String contact, ContactType contactType) {
        this.user = user;
        this.contact = contact;
        this.contactType = contactType;
    }
    
    public Contact(Integer contactId, User user, String contact, ContactType contactType) {
        this.contactId = contactId;
        this.user = user;
        this.contact = contact;
        this.contactType = contactType;
    }
    
    public int getUserIdFromContact(){
        int userId = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select user_id from contacts where contact=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setString(1, this.contact);
                ResultSet rs = ps.executeQuery();

                if(rs.next())
                    userId = rs.getInt(1);

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return userId;
    }


    public  static ArrayList<Contact> getAllContacts(User user){
        ArrayList<Contact> contacts = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from contacts where user_id=? order by contact_type_id";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, user.getUserId());
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    contacts.add(new Contact(rs.getInt(1),user,rs.getString(3),new ContactType(rs.getInt(4))));
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return contacts;
    }

    public void getUserAddress(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select contact from contacts where user_id=? and contact_type_id=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, user.getUserId());
                ps.setInt(2, ContactType.ADDRESS);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    contact = rs.getString(1);
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void getUserEmail(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select contact from contacts where user_id=? and contact_type_id=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, user.getUserId());
                ps.setInt(2, ContactType.EMAIL1);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    contact = rs.getString(1);
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean checkContact(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select user_id from contacts where contact=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setString(1, contact);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    this.user.setUserId(rs.getInt(1));
                    flag = true;
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return flag;
    }

    public boolean saveContact(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into contacts (user_id,contact,contact_type_id) values (?,?,?)";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, user.getUserId());    
                ps.setString(2, contact);    
                ps.setInt(3, contactType.getContactTypeId());       

                int val = ps.executeUpdate();

                if(val==1){
                    flag = true;
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return flag;
    }

    public void setContactId(Integer contactId){
        this.contactId = contactId;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public void setContactType(ContactType contactType){
        this.contactType = contactType;
    }

    public Integer getContactId(){
        return contactId;
    }
    public User getUser( ){
        return user;
    }
    public String getContact( ){
        return contact;
    }
    public ContactType getContactType( ){
        return contactType;
    }
}