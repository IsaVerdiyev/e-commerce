package ibar.task.ecommerce.demo.services;

import ibar.task.ecommerce.demo.exceptions.CommonException;
import ibar.task.ecommerce.demo.models.Merchant;
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

    private boolean checkIfMerchantExists(Merchant merchant){
        return merchantRepository.findByName(merchant.getName()) == null;
    }
}
