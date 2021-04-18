package ibar.task.ecommerce.demo.controllers;

import ibar.task.ecommerce.demo.domain.AuthenticationInfo;
import ibar.task.ecommerce.demo.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/authenticator")
public class TokenController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenService tokenService;

    @PostMapping("/getToken")
    public ResponseEntity<Object> getToken(@Valid @RequestBody AuthenticationInfo authenticationInfo){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("token", tokenService.generateToken(authenticationInfo));
        return ResponseEntity.ok().headers(responseHeaders).body("");
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Object> validateToken(@RequestHeader String token){
        tokenService.validateToken(token);
        return ResponseEntity.ok("");
    }
}
