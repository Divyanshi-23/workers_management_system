package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


public class City{
    private Integer cityId;
    private String city;
    private State state;

    public City(){}

    public City(Integer cityId) {
        this.cityId = cityId;
    }

    public City(Integer cityId, String city, State state) {
        this.cityId = cityId;
        this.city = city;
        this.state = state;
    }

    public static ArrayList<City> collectAllCities(){

        ArrayList<City> cities = new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select city_id, city, state from cities as c inner join states as s where c.state_id=s.state_id";
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wmsdb?user=root&password=2318");
                PreparedStatement ps = con.prepareStatement(query);){     

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    cities.add(new City(rs.getInt(1),rs.getString(2),new State(rs.getString(3))));
                }

            }catch(SQLException se){
                se.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return cities;
    }

    public void setCityId(Integer cityId){
        this.cityId = cityId;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(State state){
        this.state = state;
    }
    public Integer getCityId(){
        return cityId;
    }
    public String getCity(){
        return city;
    }
    public State getState(){
        return state;
    }
}