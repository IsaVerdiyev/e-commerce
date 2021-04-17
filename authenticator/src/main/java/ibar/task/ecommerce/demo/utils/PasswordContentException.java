package ibar.task.ecommerce.demo.utils;

import ibar.task.ecommerce.demo.controllers.ApiError;
import ibar.task.ecommerce.demo.controllers.ApiSubError;
import ibar.task.ecommerce.demo.controllers.ApiValidationError;
import ibar.task.ecommerce.demo.controllers.CommonException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class PasswordContentException extends CommonException {

    public PasswordContentException(){
        super( HttpStatus.BAD_REQUEST, "PASSWORD CONTENT IS VALID");
    }

    @Override
    public ApiError getApiError() {
        ApiSubError apiSubError = new ApiValidationError("merchant", null, null, "password content is not valid");
        List<ApiSubError> apiSubErrorList = new ArrayList<>();
        apiSubErrorList.add(apiSubError);
        ApiError apiError = super.getApiError();
        apiError.setSubErrors(apiSubErrorList);
        return apiError;
    }
}

