package utils;


public class MessageTemplate{

    public static String getIncompleteEmailVerificationMessage(String email){
        String hiddenEmail = AppUtils.partiallyHiddenEmail(email);

        String message = "Your Email Verification is pending...kindly verify your email to continue " + 
        "Please Click Over the Link : <a href='resend_verification_mail.do?email=" + email + "'>Resend Verification " + 
        "Mail</a> to resend the Email Verification Link.... We will send you a verification mail " + 
        "on " + hiddenEmail;
        return message;
    }

    public static String getErrorMessage(){
        return "An error occurred";
    }

    public static String resendEmailSuccessful(String email){
        String hiddenEmail = AppUtils.partiallyHiddenEmail(email);

        String message = "We have successfuly resent the verification email to your email account " + hiddenEmail;
        return message;
    }

    public static String incorrectPasswordMessage(){
        return "Incorrect Password ...Please try again";
    }

    public static String sessionExpiredMessage(){
        return "Your session has been expired please login again.";
    }
}