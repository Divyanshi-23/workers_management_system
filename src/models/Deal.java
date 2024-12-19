package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Deal{
    private Integer dealId;
    private WorkerSkill workerSkill;
    private User user;
    private Timestamp registrationDate;
    private String workDescription;
    private Date startDate;
    private Date endDate;
    private Integer dealAmount;;
    private Boolean paymentStatus;
    private WorkStatus workStatus;
    private DealStatus dealStatus;
    private Timestamp reviewDate;
    private String review;

    public Deal() {
    }

    public Deal(Integer dealId) {
        this.dealId = dealId;
    }
    
    public Deal(WorkerSkill workerSkill, User user) {
        this.workerSkill = workerSkill;
        this.user = user;
    }

    public Deal(WorkerSkill workerSkill, User user, Timestamp registrationDate, String workDescription, Date startDate,
            Date endDate, Integer dealAmount, Boolean paymentStatus, WorkStatus workStatus, DealStatus dealStatus,
            Timestamp reviewDate, String review) {
        this.workerSkill = workerSkill;
        this.user = user;
        this.registrationDate = registrationDate;
        this.workDescription = workDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dealAmount = dealAmount;
        this.paymentStatus = paymentStatus;
        this.workStatus = workStatus;
        this.dealStatus = dealStatus;
        this.reviewDate = reviewDate;
        this.review = review;
    }

    public boolean paymentCompleted(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update deals set payment_status=? where deal_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setBoolean(1,true);
                ps.setInt(2,dealId);

                int val = ps.executeUpdate();

                if(val == 1){
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

    public boolean updateWorkStatus(){
        Boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update deals set work_status_id=? where deal_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,workStatus.getWorkStatusId());
                ps.setInt(2,dealId);

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

    public void getDealDetail(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where deal_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, dealId);    
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    this.setDealId(rs.getInt("deal_id"));
                    WorkerSkill workerSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    workerSkill.getSkillDetailsWithWorkerSkillId();
                    this.setWorkerSkill(workerSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    this.setUser(us);
                    this.setRegistrationDate(rs.getTimestamp("registration_date"));
                    this.setWorkDescription(rs.getString("work_description"));
                    this.setStartDate(rs.getDate("start_date"));
                    this.setEndDate(rs.getDate("end_date"));
                    this.setDealAmount(rs.getInt("deal_amount"));
                    this.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    this.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    this.setDealStatus(dealStatus);
                    this.setReviewDate(rs.getTimestamp("review_date"));
                    this.setReview(rs.getString("review"));
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean getEnquiredDeal(){
        boolean result = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where worker_skill_id=? and user_id=? and deal_status_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, workerSkill.getWorkerSkillId());    
                ps.setInt(2, user.getUserId());    
                ps.setInt(3, DealStatus.ENQUIRED);    
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    this.setDealId(rs.getInt("deal_id"));
                    WorkerSkill workerSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    workerSkill.getSkillDetailsWithWorkerSkillId();
                    this.setWorkerSkill(workerSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    this.setUser(us);
                    this.setRegistrationDate(rs.getTimestamp("registration_date"));
                    this.setWorkDescription(rs.getString("work_description"));
                    this.setStartDate(rs.getDate("start_date"));
                    this.setEndDate(rs.getDate("end_date"));
                    this.setDealAmount(rs.getInt("deal_amount"));
                    this.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    this.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    this.setDealStatus(dealStatus);
                    this.setReviewDate(rs.getTimestamp("review_date"));
                    this.setReview(rs.getString("review"));

                    result = true;
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }

    public void getDealDetailWithDealId(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where deal_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, dealId);    
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    this.setDealId(rs.getInt("deal_id"));
                    WorkerSkill workerSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    workerSkill.getSkillDetailsWithWorkerSkillId();
                    this.setWorkerSkill(workerSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    this.setUser(us);
                    this.setRegistrationDate(rs.getTimestamp("registration_date"));
                    this.setWorkDescription(rs.getString("work_description"));
                    this.setStartDate(rs.getDate("start_date"));
                    this.setEndDate(rs.getDate("end_date"));
                    this.setDealAmount(rs.getInt("deal_amount"));
                    this.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    this.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    this.setDealStatus(dealStatus);
                    this.setReviewDate(rs.getTimestamp("review_date"));
                    this.setReview(rs.getString("review"));
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Deal> getDealsByWorkerSkill(WorkerSkill workerSkill){
        ArrayList<Deal> deals = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where worker_skill_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, workerSkill.getWorkerSkillId());    
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Deal deal = new Deal();
                    deal.setDealId(rs.getInt("deal_id"));
                    WorkerSkill wSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    wSkill.getSkillDetailsWithWorkerSkillId();
                    deal.setWorkerSkill(wSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    deal.setUser(us);
                    deal.setRegistrationDate(rs.getTimestamp("registration_date"));
                    deal.setWorkDescription(rs.getString("work_description"));
                    deal.setStartDate(rs.getDate("start_date"));
                    deal.setEndDate(rs.getDate("end_date"));
                    deal.setDealAmount(rs.getInt("deal_amount"));
                    deal.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    deal.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    deal.setDealStatus(dealStatus);
                    deal.setReviewDate(rs.getTimestamp("review_date"));
                    deal.setReview(rs.getString("review"));
                    deals.add(deal);
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return deals;
    }

    public static ArrayList<Deal> getAllDeals(User user){
        ArrayList<Deal> deals = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where user_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, user.getUserId());    
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Deal deal = new Deal();
                    deal.setDealId(rs.getInt("deal_id"));
                    WorkerSkill workerSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    workerSkill.getSkillDetailsWithWorkerSkillId();
                    deal.setWorkerSkill(workerSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    deal.setUser(us);
                    deal.setRegistrationDate(rs.getTimestamp("registration_date"));
                    deal.setWorkDescription(rs.getString("work_description"));
                    deal.setStartDate(rs.getDate("start_date"));
                    deal.setEndDate(rs.getDate("end_date"));
                    deal.setDealAmount(rs.getInt("deal_amount"));
                    deal.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    deal.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    deal.setDealStatus(dealStatus);
                    deal.setReviewDate(rs.getTimestamp("review_date"));
                    deal.setReview(rs.getString("review"));
                    deals.add(deal);
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return deals;
    }
    
    public boolean rejectDeal(){
        Boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update deals set deal_status_id=? where deal_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,DealStatus.REJECTED);
                ps.setInt(2,dealId);

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

    public boolean acceptDeal(){
        Boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update deals set deal_status_id=? where deal_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,DealStatus.INITIATED);
                ps.setInt(2,dealId);

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
    
    public static ArrayList<Deal> getCompletionRequestedDeals(User user){
        ArrayList<Deal> deals = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where user_id=? and deal_status_id=? and payment_status=";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, user.getUserId());    
                ps.setInt(2,3);    
                ps.setBoolean(3,true);    
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Deal deal = new Deal();
                    deal.setDealId(rs.getInt("deal_id"));
                    WorkerSkill workerSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    workerSkill.getSkillDetailsWithWorkerSkillId();
                    deal.setWorkerSkill(workerSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    deal.setUser(us);
                    deal.setRegistrationDate(rs.getTimestamp("registration_date"));
                    deal.setWorkDescription(rs.getString("work_description"));
                    deal.setStartDate(rs.getDate("start_date"));
                    deal.setEndDate(rs.getDate("end_date"));
                    deal.setDealAmount(rs.getInt("deal_amount"));
                    deal.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    deal.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    deal.setDealStatus(dealStatus);
                    deal.setReviewDate(rs.getTimestamp("review_date"));
                    deal.setReview(rs.getString("review"));
                    deals.add(deal);
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return deals;
    }

    public static ArrayList<Deal> getInitiationRequestedDeals(User user){
        ArrayList<Deal> deals = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from deals where user_id=? and deal_status_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
            PreparedStatement ps = con.prepareStatement(query);){
                ps.setInt(1, user.getUserId());    
                ps.setInt(2,4);    
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Deal deal = new Deal();
                    deal.setDealId(rs.getInt("deal_id"));
                    WorkerSkill workerSkill = new WorkerSkill(rs.getInt("worker_skill_id"));
                    workerSkill.getSkillDetailsWithWorkerSkillId();
                    deal.setWorkerSkill(workerSkill);
                    User us = new User(rs.getInt("user_id"));
                    us.getDetails();
                    deal.setUser(us);
                    deal.setRegistrationDate(rs.getTimestamp("registration_date"));
                    deal.setWorkDescription(rs.getString("work_description"));
                    deal.setStartDate(rs.getDate("start_date"));
                    deal.setEndDate(rs.getDate("end_date"));
                    deal.setDealAmount(rs.getInt("deal_amount"));
                    deal.setPaymentStatus(rs.getBoolean("payment_status"));
                    WorkStatus workStatus = new WorkStatus(rs.getInt("work_status_id"));
                    workStatus.getWorkStatus();
                    deal.setWorkStatus(workStatus);
                    DealStatus dealStatus = new DealStatus(rs.getInt("deal_status_id"));
                    dealStatus.getDealStatus();
                    deal.setDealStatus(dealStatus);
                    deal.setReviewDate(rs.getTimestamp("review_date"));
                    deal.setReview(rs.getString("review"));
                    deals.add(deal);
                }    

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return deals;
    }

    public boolean createDeal(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into deals (registration_date,deal_status_id,worker_skill_id,user_id) values (?,?,?,?)";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);){
    
                ps.setTimestamp(1, registrationDate);    
                ps.setInt(2, DealStatus.ENQUIRED);    
                ps.setInt(3, workerSkill.getWorkerSkillId());    
                ps.setInt(4, user.getUserId());

                int val = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                
                if(val==1){
                    if(rs.next()){
                        this.dealId = rs.getInt(1);
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

    public boolean startDeal(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into deals (registration_date,work_description,start_date,end_date,deal_amount,payment_status,deal_status_id,worker_skill_id,user_id) values (?,?,?,?,?,?,?,?,?)";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){
                ps.setTimestamp(1, registrationDate);    
                ps.setString(2, workDescription);
                ps.setDate(3, startDate);    
                ps.setDate(4, endDate); 
                ps.setInt(5, dealAmount);    
                ps.setBoolean(6, false);
                ps.setInt(7, DealStatus.REQUESTED);    
                ps.setInt(8, workerSkill.getWorkerSkillId());    
                ps.setInt(9, user.getUserId());

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

    public boolean initiateDeal(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update deals set registration_date=?,work_description=?,start_date=?,end_date=?,deal_amount=?,payment_status=?,deal_status_id=? where worker_skill_id=? and user_id=? and deal_status_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){
                ps.setTimestamp(1, registrationDate);    
                ps.setString(2, workDescription);
                ps.setDate(3, startDate);    
                ps.setDate(4, endDate); 
                ps.setInt(5, dealAmount);    
                ps.setBoolean(6, false);
                ps.setInt(7, DealStatus.REQUESTED);    
                ps.setInt(8, workerSkill.getWorkerSkillId());    
                ps.setInt(9, user.getUserId());
                ps.setInt(10, DealStatus.ENQUIRED);    

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

    public void setDealId(Integer dealId){
        this.dealId = dealId;
    }
    public void setWorkerSkill(WorkerSkill workerSkill){
        this.workerSkill = workerSkill;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setRegistrationDate(Timestamp registrationDate){
        this.registrationDate = registrationDate;
    }
    public void setWorkDescription(String workDescription){
        this.workDescription = workDescription;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
    public void setDealAmount(Integer dealAmount){
        this.dealAmount = dealAmount;
    }
    public void setPaymentStatus(Boolean paymentStatus){
        this.paymentStatus = paymentStatus;
    }
    public void setWorkStatus (WorkStatus workStatus){
        this.workStatus = workStatus;
    }
    public void setDealStatus (DealStatus dealStatus){
        this.dealStatus = dealStatus;
    }
    public void setReviewDate(Timestamp reviewDate){
        this.reviewDate = reviewDate;
    }
    public void setReview(String review){
        this.review = review;
    }

    public Integer getDealId(){
        return dealId;
    }
    public WorkerSkill getWorkerSkill(){
        return workerSkill;
    }
    public User getUser(){
        return user;
    }
    public Timestamp getRegistrationDate(){
        return registrationDate;
    }
    public String getWorkDescription(){
        return workDescription;
    }
    public Date getStartDate(){
        return startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
    public Integer getDealAmount(){
        return dealAmount;
    }
    public Boolean getPaymentStatus(){
        return paymentStatus;
    }
    public WorkStatus getWorkStatus (){
        return workStatus;
    }
    public DealStatus getDealStatus (){
        return dealStatus;
    }
    public Timestamp getReviewDate(){
        return reviewDate;
    }
    public String getReview(){
        return review;
    }
}