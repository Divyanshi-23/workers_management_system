package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DealStatus{
    private Integer dealStatusId;
    private String status;
    public static final int INITIATED = 1;
    public static final int CANCELLED = 2;
    public static final int COMPLETED = 3;
    public static final int REQUESTED = 4;
    public static final int REJECTED = 5;
    public static final int ENQUIRED = 6;

    public DealStatus(){}

    public DealStatus(Integer dealStatusId) {
        this.dealStatusId = dealStatusId;
    }
    public void getDealStatus(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select status from deal_status where deal_status_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){     
                ps.setInt(1,this.dealStatusId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    this.status = rs.getString("status");
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void setDealStatusId(Integer dealStatusId){
        this.dealStatusId = dealStatusId;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public Integer getDealStatusId(){
        return dealStatusId;
    }
    public String getStatus(){
        return status;
    }
}