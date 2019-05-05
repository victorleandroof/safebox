package br.com.victor.safebox.domain.exception;

import br.com.victor.safebox.domain.error.ErroField;

import java.util.List;

public class ValidationException extends  RuntimeException {

    private List<ErroField>  errorFields;

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(String message,List<ErroField> erroFields){
        super(message);
        this.errorFields = erroFields;
    }


    public List<ErroField> getErrorFields() {
        return errorFields;
    }
}
