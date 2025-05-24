package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kaio
 */
public class validarEmail {

    
    public static boolean validarEmail(String email) {

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}
