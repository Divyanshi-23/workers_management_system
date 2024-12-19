package models;

public class ContactType{
    private Integer contactTypeId;
    private String type;

    public static final int PHONE1 = 1;
    public static final int PHONE2 = 2;
    public static final int EMAIL1 = 3;
    public static final int EMAIL2 = 4;
    public static final int ADDRESS = 5;
    
    public static String[] types = {"contact1", "contact2" , "first_email", "secondary_email" , "address" };

    public ContactType(){}

    public ContactType(String type) {
        this.type = type;
    }

    public ContactType(Integer contactTypeId) {
        this.contactTypeId = contactTypeId;
    }
    
    public void setContactTypeId(Integer contactTypeId){
        this.contactTypeId = contactTypeId;
    }
    public void setType(String type){
        this.type = type;
    }
    public Integer getContactTypeId(){
        return contactTypeId;
    }
    public String getType(){
        return type;
    }
}