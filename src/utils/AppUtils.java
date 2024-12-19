package utils;

import java.util.Random;

import java.util.Date;

public class AppUtils {
    static Random random = new Random();

    public static String generateEmailActivationCode(){
        return new Date().getTime() + "_" + Math.abs(random.nextLong());
    }

    public static String generatePasswordResetCode(){
        return new Date().getTime() + "_" + Math.abs(random.nextLong());
    }

    public static String partiallyHiddenEmail(String email){
        String[] s = email.split("@");
        for(int i=2 ; i<s[0].length(); i++){
            s[0] = s[0].replace(s[0].charAt(i), '*');
        }
        String hiddenEmail = s[0]+"@"+s[1];
        return hiddenEmail;
    }

    public static Date getDate(){
        return new Date();
    }

}
