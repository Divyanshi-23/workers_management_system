package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class User{
    private Integer userId;
    private String userName;
    private String name;
    private String gender;
    private Date dob;
    private String profilePic;
    private String password;
    private City city;
    private String uId;
    private Boolean role;
    private Status status;
    private String activationCode;
    private Integer rating;

    private static StrongPasswordEncryptor spe = new StrongPasswordEncryptor();

    public User(){

    }
    public User(Integer userId){
        this.userId = userId;
    }

    public User(String activationCode) {
        this.activationCode = activationCode;
    }

    public User(String userName, String name, String gender, Date dob, String profilePic, String password, City city,
        String uId, Boolean role, String activationCode) {
        this.userName = userName;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.profilePic = profilePic;
        this.password = password;
        this.city = city;
        this.uId = uId;
        this.role = role;
        this.activationCode = activationCode;
    }

    public static ArrayList<User> findWorkerInArea(City city){
        ArrayList<User> workers = new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from users where city_id=? and role=1 order by rating desc";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,city.getCityId());

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt(1));
                    user.setUserName(rs.getString(2));
                    user.setName(rs.getString(3));
                    user.setGender(rs.getString(4));;
                    user.setProfilePic(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setCity(city);
                    user.setUId(rs.getString(8));
                    user.setRole( rs.getBoolean(9));
                    user.setActivationCode(rs.getString(10));
                    user.setStatus(new Status(rs.getString(11)));
                    user.setDob(rs.getDate(12));
                    user.setRating(rs.getInt(13));
                    
                    workers.add(user);
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return workers;
    }

    public boolean updateProfilePic(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update users set profile_pic=? where user_id=? ";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setString(1,profilePic);
                ps.setInt(2,userId);

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

    public boolean setActivationCode(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update users set activation_code=? where user_id=? ";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setString(1,activationCode);
                ps.setInt(2,userId);

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

    public boolean resetPassword(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update users set password=?, activation_code=null where user_id=? and activation_code=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setString(1,spe.encryptPassword(password));
                ps.setInt(2,userId);
                ps.setString(3,activationCode);

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

    public void getDetails(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from users where user_id=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){                            
                    this.userName = rs.getString("user_name");
                    this.name = rs.getString("name");
                    this.gender = rs.getString("gender");
                    this.profilePic = rs.getString("profile_pic");
                    this.city = new City(rs.getInt("city_id"));
                    this.uId = rs.getString("uid");
                    this.rating = rs.getInt("rating");
                    this.status = new Status(rs.getInt("status_id"));
                    this.dob = rs.getDate("dob") ;
                    this.role = rs.getBoolean("role");
                    this.password = rs.getString("password");
                    this.activationCode = rs.getString("activation_code");
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean activateAccount(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update users set status_id=? where user_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,Status.ACTIVE);
                ps.setInt(2,userId);

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

    public boolean verifyEmail(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update users set activation_code=null, status_id=? where user_id=? and activation_code=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,role?Status.EMAIL_VERIFIED:Status.ACTIVE);
                ps.setInt(2,userId);
                ps.setString(3, activationCode);

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

    public int login(){
        int result = 0; //exception occurred
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select password, user_name, name, gender,profile_pic,city_id,uid,activation_code,rating,status_id, dob,role from users where user_id=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    if(spe.checkPassword(password, rs.getString("password"))){
                        if(rs.getString("activation_code") == null){
                            
                            this.userName = rs.getString(2);
                            this.name = rs.getString(3);
                            this.gender = rs.getString(4);
                            this.profilePic = rs.getString(5);
                            this.city = new City(rs.getInt(6));
                            this.uId = rs.getString(7);
                            this.rating = rs.getInt(9);
                            this.status = new Status(rs.getInt(10));
                            this.dob = rs.getDate(11) ;
                            this.role = rs.getBoolean(12);
                            
                            result = 3; //suuccesful login
                        }else{
                            result = 2; //email verfication pending
                        }
                    }else{
                        result = 1; //incorrect password
                    }
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return result;
    }
    
    public boolean saveRecord(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into users (name,user_name,gender,dob,profile_pic,password,city_id,uid,role,activation_code) values (?,?,?,?,?,?,?,?,?,?)";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);){
                ps.setString(1, name);    
                ps.setString(2, userName);    
                ps.setString(3, gender);    
                ps.setDate(4, dob);
                ps.setString(5, profilePic);    
                ps.setString(6, spe.encryptPassword(password));    
                ps.setInt(7, city.getCityId());    
                ps.setString(8, uId);    
                ps.setBoolean(9, role);    
                ps.setString(10, activationCode);    

                int val = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                if(val==1){
                    if(rs.next()){
                        this.userId = rs.getInt(1);
                    }
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

    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setDob(Date dob){
        this.dob = dob;
    } 
    public void setProfilePic(String profilePic){
        this.profilePic = profilePic;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setUId(String uId) {
        this.uId = uId;
    }

    public void setCity(City city){
        this.city = city;
    }
    public void setRole(Boolean role){
        this.role = role;
    }
    public String getUId() {
        return uId;
    }

    public void setStatus(Status status){
        this.status = status;
    }
    public void setActivationCode(String activationCode){
        this.activationCode = activationCode;
    }
    public void setRating(Integer rating){
        this.rating = rating;
    }

    public Integer getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }
    public Date getDob(){
        return dob;
    } 
    public String getProfilePic(){
        return profilePic;
    }
    public String getPassword(){
        return password;
    }
    public City getCity(){
        return city;
    }
    public Boolean getRole(){
        return role;
    }
    public Status getStatus(){
        return status;
    }
    public String getActivationCode(){
        return activationCode;
    }
    public Integer getRating(){
        return rating;
    }
}