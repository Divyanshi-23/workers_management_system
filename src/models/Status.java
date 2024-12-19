package models;

public class Status{
    private Integer statusId;
    private String status;

    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;
    public static final int BLOCKED = 3;
    public static final int CLOSED = 4;
    public static final int EMAIL_VERIFIED = 5;

    public Status(){

    }
    
    public Status(String status) {
        this.status = status;
    }

    public Status(Integer statusId) {
        this.statusId = statusId;
    }
    public void setStatusId(Integer statusId){
        this.statusId = statusId;
    }
    public void getStatus(String status){
        this.status = status;
    }
    public Integer getStatusId(){
        return statusId;
    }
    public String setStatus(){
        return status;
    }
}