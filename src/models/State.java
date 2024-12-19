package models;

public class State{
    private Integer stateId;
    private String state;

    public State(){}


    public State(String state) {
        this.state = state;
    }


    public void setStateId(Integer stateId){
        this.stateId = stateId;
    }
    public void setState(String state){
        this.state = state;
    }
    public Integer getStateId(){
        return stateId;
    }
    public String getState(){
        return state;
    }
}