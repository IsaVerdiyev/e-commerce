package ibar.task.ecommerce.demo.utils;

import ibar.task.ecommerce.demo.controllers.ApiError;
import ibar.task.ecommerce.demo.controllers.ApiSubError;
import ibar.task.ecommerce.demo.controllers.ApiValidationError;
import ibar.task.ecommerce.demo.controllers.CommonException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class LengthNotValidException extends CommonException {

    public LengthNotValidException(){
        super( HttpStatus.BAD_REQUEST, "PASSWORD LENGTH NOT VALID");
    }

    @Override
    public ApiError getApiError() {
        ApiSubError apiSubError = new ApiValidationError("merchant", null, null, "password length is too small");
        List<ApiSubError> apiSubErrorList = new ArrayList<>();
        apiSubErrorList.add(apiSubError);
        ApiError apiError = super.getApiError();
        apiError.setSubErrors(apiSubErrorList);
        return apiError;
    }
}
