package ibar.task.ecommerce.demo.utils;

import ibar.task.ecommerce.demo.exceptions.PasswordContentException;
import ibar.task.ecommerce.demo.exceptions.PasswordLengthNotValidException;
import ibar.task.ecommerce.demo.models.Merchant;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    Integer minimumLength = 6;
    String regex = "^[a-zA-Z0-9]{6,}$";

    public void validateSignUpPassword(Merchant merchant) throws PasswordContentException, PasswordLengthNotValidException {
        checkLength(merchant.getPassword());
        checkSymbols(merchant.getPassword());
    }

    private void checkSymbols(String password) throws PasswordContentException {
        if(!password.matches(regex)){
            throw new PasswordContentException();
        }
    }

    private void checkLength(String password) throws PasswordLengthNotValidException {
         if(password.length() < minimumLength){
             throw new PasswordLengthNotValidException();
         }
    }
}
