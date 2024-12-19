package utils;


public class EmailTemplates {
    public static String generateWelcomeMail(String name, String email, String activationCode , Boolean role){
        String welcomeMail = "<h1>Welcome "+ name+"</h1><hr><p style='font-weight:bold;font-size:22px;color:red'>" +
        "Please click over the account activation link <a href='http://localhost:8080/wms/email_verify.do?email="+email+"&activation_code="+ activationCode+"&role="+ role +"'>" + 
        "Activation Link</a></p>";

        return welcomeMail;
    }

    public static String generatePasswordResetMail(String name, String email ,String userName,String activationCode){
        String passwordResetMail = "<h1>Hi "+ name+"</h1><hr><p style='font-weight:bold;font-size:22px;color:red'>" +
        "Please click over the reset link  <a href='http://localhost:8080/wms/reset_password.do?email="+ email +"&activation_code="+activationCode+"'>" +
        "reset link</a> to reset your password for your account named "+ userName +"</p>";
        
        return passwordResetMail;
    }
}
