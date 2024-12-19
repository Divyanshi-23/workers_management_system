package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkerSkill{
    private Integer workerSkillId;
    private User user;
    private Skill skill;
    private Integer experience;
    private Integer rating;
    private Integer jobCount;
    private Boolean expert;

    public WorkerSkill(){}

    public WorkerSkill(Integer workerSkillId) {
        this.workerSkillId = workerSkillId;
    }

    public WorkerSkill(User user, Skill skill) {
        this.user = user;
        this.skill = skill;
    }

    public WorkerSkill(User user, Skill skill, Integer experience) {
        this.user = user;
        this.skill = skill;
        this.experience = experience;
    }
    
    public WorkerSkill(Integer workerSkillId, User user, Skill skill, Integer experience, Integer rating,
            Integer jobCount, Boolean expert) {
        this.workerSkillId = workerSkillId;
        this.user = user;
        this.skill = skill;
        this.experience = experience;
        this.rating = rating;
        this.jobCount = jobCount;
        this.expert = expert;
    }
    public void getSkillDetailsWithWorkerSkillId(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select skill_id,experience,rating,job_count,expert,user_id from worker_skills where worker_skill_id=?";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, workerSkillId);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                   Skill  s = new Skill(rs.getInt(1));
                   s.getSkillDetails();
                   this.skill = s;
                   this.experience = rs.getInt(2);
                   this.rating = rs.getInt(3);
                   this.jobCount = rs.getInt(4);
                   this.expert = rs.getBoolean(5);
                   User u = new User(rs.getInt(6));
                   u.getDetails();
                   this.user = u;
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public void getSkillDetails(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select worker_skill_id,experience,rating,job_count,expert from worker_skills where user_id=? and  skill_id=? ";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, user.getUserId());
                ps.setInt(2, skill.getSkillId());
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                   this.workerSkillId = rs.getInt(1);
                   this.experience = rs.getInt(2);
                   this.rating = rs.getInt(3);
                   this.jobCount = rs.getInt(4);
                   this.expert = rs.getBoolean(5);
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<WorkerSkill> getAllSkills(User user){
        ArrayList<WorkerSkill> workerSkills = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from worker_skills where user_id=? ";
            try( 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1, user.getUserId());
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Skill skill = new Skill(rs.getInt(3));
                    skill.getSkillDetails();
                    workerSkills.add(new WorkerSkill(rs.getInt(1),user,skill,rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getBoolean("expert")));
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return workerSkills;
    }
    
    public static ArrayList<WorkerSkill> findWorkers(City city, Integer skillId){
        ArrayList<WorkerSkill> workersSkills = new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from worker_skills inner join users on worker_skills.user_id=users.user_id where city_id=? and worker_skills.skill_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,city.getCityId());
                ps.setInt(2,skillId);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    WorkerSkill workerSkill = new WorkerSkill();
                    workerSkill.setWorkerSkillId((rs.getInt(1)));
                    User u = new User((rs.getInt(2)));
                    u.getDetails();
                    workerSkill.setUser(u);
                    Skill s = new Skill(rs.getInt(3));
                    s.getSkillDetails();
                    workerSkill.setSkill(s);
                    workerSkill.setExperience(rs.getInt(4));
                    workerSkill.setRating(rs.getInt(5));
                    workerSkill.setJobCount(rs.getInt(6));
                    workerSkill.setExpert(rs.getBoolean(7));
                    
                    workersSkills.add(workerSkill);
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return workersSkills;
    }

    public boolean saveSkill(){
        boolean flag = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into worker_skills(user_id,skill_id,experience) values (?,?,?)";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);
            ){
                ps.setInt(1,user.getUserId());
                ps.setInt(2,skill.getSkillId());
                ps.setInt(3, experience);

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

    public void setWorkerSkillId(Integer workerSkillId){
        this.workerSkillId = workerSkillId;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setSkill(Skill skill){
        this.skill = skill;
    }
    public void setExperience(Integer experience){
        this.experience = experience;
    }
    public void setJobCount(Integer jobCount){
        this.jobCount = jobCount;
    }
    public void setExpert(Boolean expert){
        this.expert = expert;
    }
    public void setRating(Integer rating){
        this.rating = rating;
    }

    public Integer getWorkerSkillId(){
        return workerSkillId;
    }
    public User getUser(){
        return user;
    }
    public Skill getSkill(){
        return skill;
    }
    public Integer getExperience(){
        return experience;
    }
    public Integer getJobCount(){
        return jobCount;
    }
    public Boolean getExpert(){
        return expert;
    }
    public Integer getRating(){
        return rating;
    }
}