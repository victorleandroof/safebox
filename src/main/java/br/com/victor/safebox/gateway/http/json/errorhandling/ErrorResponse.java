package br.com.victor.safebox.gateway.http.json.errorhandling;

import br.com.victor.safebox.domain.error.ErroField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

    public static final String ERR_VALIDATION = "error.validation";
    public static final String ERR_METHOD_NOT_SUPPORTED = "error.methodNotSuported";
    public static final String ERR_INTERNAL_SERVER_ERROR = "error.internalServerError";
    public static final String ERR_FEATURE = "error.feature";
    public static final String ERR_TIMEOUT = "error.timeout";
    public static final String ERR_NOT_FOUND = "error.notFound";
    public static final String ERR_BAD_REQUEST = "error.badRequest";
    public static final String ERR_DUPLICATE_KEY = "error.duplicateKey";

    private final String message;
    private final String description;
    private List<ErroField> errorField;

    ErrorResponse(){
        message = "";
        description = "";
    }

    public ErrorResponse(String message) {
        this(message,null);
    }

    public ErrorResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public ErrorResponse(String message, String description, List<ErroField> errorField) {
        this.message = message;
        this.description = description;
        this.errorField = errorField;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<ErroField> getErrorField() {
        return errorField;
    }

    public void setErrorField(List<ErroField> errorField) {
        this.errorField = errorField;
    }
}
