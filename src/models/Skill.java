package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Skill{
    private Integer skillId;
    private String skill;

    public Skill(){}

    public Skill(Integer skillId) {
        this.skillId = skillId;
    }

    public Skill(Integer skillId, String skill) {
        this.skillId = skillId;
        this.skill = skill;
    }

    public void getSkillDetails(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select skill from skills where skill_id=?";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){     
                ps.setInt(1,this.skillId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    this.skill = rs.getString("skill");
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Skill> collectAllSkills(){

        ArrayList<Skill> skills = new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from skills";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){     

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    skills.add(new Skill(rs.getInt(1),rs.getString(2)));
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return skills;
    }

    public void setSkillId(Integer skillId){
        this.skillId = skillId;
    }
    public void setSkill(String skill){
        this.skill = skill;
    }
    public Integer getSkillId(){
        return skillId;
    }
    public String getSkill(){
        return skill;
    }
}