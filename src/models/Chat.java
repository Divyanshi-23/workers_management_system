package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Chat {
    private Integer chatId;
    private WorkerSkill workerSkill;
    private User user;
    private Boolean fromTo;
    private Timestamp postDate;
    private String message;
    private Deal deal;

    public Chat(){}
    
    public Chat(WorkerSkill workerSkill, User user) {
        this.workerSkill = workerSkill;
        this.user = user;
    }

    public Chat(WorkerSkill workerSkill, User user, Boolean fromTo, Timestamp postDate, String message, Deal deal) {
        this.workerSkill = workerSkill;
        this.user = user;
        this.fromTo = fromTo;
        this.postDate = postDate;
        this.message = message;
        this.deal = deal;
    }

    public static ArrayList<Chat> getChats(Deal deal){
        ArrayList<Chat> chats = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from chats where deal_id=? order by post_date ";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,deal.getDealId());
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Chat chat = new Chat();
                    chat.setChatId(rs.getInt("chat_id"));
                    WorkerSkill wk = new WorkerSkill(rs.getInt("worker_skill_id"));
                    wk.getSkillDetailsWithWorkerSkillId();
                    chat.setWorkerSkill(wk);
                    User u = new User(rs.getInt("user_id"));
                    u.getDetails();
                    chat.setUser(u);
                    chat.setMessage(rs.getString("message"));
                    chat.setFromTo(rs.getBoolean("from_to"));
                    chat.setPostDate(rs.getTimestamp("post_date"));
                    Deal d = new Deal(rs.getInt("deal_id"));
                    d.getDealDetailWithDealId();
                    chats.add(chat);
                }
                
            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return chats;
    }
    
    public boolean saveChat(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into chats (worker_skilL_id,user_id,from_to,post_date,message,deal_id) values (?,?,?,?,?,?)";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, workerSkill.getWorkerSkillId());    
                ps.setInt(2, user.getUserId());   
                ps.setBoolean(3, fromTo);   
                ps.setTimestamp(4, postDate);    
                ps.setString(5, message); 
                ps.setInt(6, deal.getDealId());   
                
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
    
    
    public void setChatId(Integer chatId){
        this.chatId = chatId;
    }
    
    public void setPostDate(Timestamp postDate){
        this.postDate = postDate;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public Integer getChatId(){
        return chatId;
    }
    public Timestamp getPostDate(){
        return postDate;
    }  
    public String getMessage(){
        return message;
    }
    public void setWorkerSkill(WorkerSkill workerSkill) {
        this.workerSkill = workerSkill;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setFromTo(Boolean fromTo) {
        this.fromTo = fromTo;
    }

    public WorkerSkill getWorkerSkill() {
        return workerSkill;
    }

    public User getUser() {
        return user;
    }

    public Boolean getFromTo() {
        return fromTo;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    } 
    

}