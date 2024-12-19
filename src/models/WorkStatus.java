package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkStatus{
    private Integer workStatusId;
    private String status;
    
    public static final String[] statuses = {"initiated","reached_client","working","completed","payment_completed"};
    
    public WorkStatus(){}

    public WorkStatus(Integer workStatusId) {
        this.workStatusId = workStatusId;
        if(workStatusId >1){
            this.status = statuses[workStatusId-1];
        }
    }

    public void getWorkStatus(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select status from work_status where work_status_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){     
                ps.setInt(1,this.workStatusId);
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

    public void setWorkStatusId(Integer workStatusId){
        this.workStatusId = workStatusId;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public Integer getWorkStatusId(){
        return workStatusId;
    }
    public String getStatus(){
        return status;
    }
}