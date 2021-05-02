package ibar.task.ecommerce.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import ibar.task.ecommerce.demo.exceptions.CommonException;
import ibar.task.ecommerce.demo.models.AuthenticationInfo;
import ibar.task.ecommerce.demo.models.Merchant;
import ibar.task.ecommerce.demo.services.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/merchants")
public class MerchantController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MerchantService merchantService;

    @PostMapping
    public ResponseEntity<Object> signUp(@Valid @RequestBody Merchant merchant) throws CommonException {
        merchantService.addMerchant(merchant);
        return ResponseEntity.ok("");
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthenticationInfo authenticationInfo) throws CommonException, JsonProcessingException {
        String token = merchantService.getMerchantByAuthenticationInfo(authenticationInfo);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("token", token);
        return ResponseEntity.ok().headers(responseHeaders).body("");
    }
}
