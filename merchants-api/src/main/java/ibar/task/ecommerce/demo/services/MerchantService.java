package ibar.task.ecommerce.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ibar.task.ecommerce.demo.exceptions.CommonException;
import ibar.task.ecommerce.demo.exceptions.InvalidPasswordException;
import ibar.task.ecommerce.demo.exceptions.MerchantNotFoundException;
import ibar.task.ecommerce.demo.models.AuthenticationInfo;
import ibar.task.ecommerce.demo.models.Merchant;
import ibar.task.ecommerce.demo.proxies.AuthenticatorProxy;
import ibar.task.ecommerce.demo.repositories.MerchantRepository;
import ibar.task.ecommerce.demo.exceptions.MerchantAlreadyExists;
import ibar.task.ecommerce.demo.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    AuthenticatorProxy authenticatorProxy;

    public MerchantService(MerchantRepository merchantRepository){
        this.merchantRepository = merchantRepository;
    }

    public Merchant addMerchant(Merchant merchant) throws CommonException {
        passwordValidator.validateSignUpPassword(merchant);
        if(checkIfMerchantExists(merchant)){
            return merchantRepository.save(merchant);
        }
        else{
            throw new MerchantAlreadyExists();
        }
    }

    public String getMerchantByAuthenticationInfo(AuthenticationInfo authenticationInfo) throws MerchantNotFoundException, InvalidPasswordException, JsonProcessingException {
        Merchant merchant = merchantRepository.findByName(authenticationInfo.getMerchantName());
        if(merchant == null){
            throw new MerchantNotFoundException();
        }
        if(!merchant.validatePassword(authenticationInfo.getPassword())){
            throw new InvalidPasswordException();
        }

        return authenticatorProxy.getToken(authenticationInfo);
    }

    private boolean checkIfMerchantExists(Merchant merchant){
        return merchantRepository.findByName(merchant.getName()) == null;
    }
}
