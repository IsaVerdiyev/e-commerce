package ibar.task.ecommerce.demo.controllers;

import ibar.task.ecommerce.demo.exceptions.CommonException;
import ibar.task.ecommerce.demo.models.AuthenticationInfo;
import ibar.task.ecommerce.demo.models.Merchant;
import ibar.task.ecommerce.demo.services.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class MerchantController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MerchantService merchantService;

    @PostMapping("/signUp")
    public ResponseEntity<Merchant> signUp(@Valid @RequestBody Merchant merchant) throws CommonException {
        return new ResponseEntity<>(merchantService.addMerchant(merchant), HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Merchant> signIn(@Valid @RequestBody AuthenticationInfo authenticationInfo) throws CommonException {
        return new ResponseEntity<>(merchantService.getMerchantByAuthenticationInfo(authenticationInfo), HttpStatus.OK);
    }
}
