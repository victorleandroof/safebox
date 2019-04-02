package br.com.victor.safebox.usecase;

import br.com.victor.safebox.domain.error.ErroField;
import br.com.victor.safebox.domain.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Component
public class ModalValidator<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Validator validator;

    @Autowired
    public ModalValidator(Validator validator) {
        this.validator = validator;
    }

    public void execute(T typeToValidate){
        Set<ConstraintViolation<T>> validationsErro = validator.validate(typeToValidate);

        if(!validationsErro.isEmpty()){
            Map<String, List<String>> fieldMaps = new HashMap<>();
            validationsErro.forEach(tConstraintViolation ->
                    fieldMaps.computeIfAbsent(tConstraintViolation.getMessageTemplate(),
                            fields -> new ArrayList<>()).add(tConstraintViolation.getPropertyPath().toString()));
            List<ErroField> errors = new ArrayList<>();

            fieldMaps.forEach((message,field)->{
                errors.add(new ErroField(message,field));
            });

            log.info("Validation erro.  Reason: mandatory field not filled.");

            throw  new ValidationException("msg.error.fields.not.filled",errors);


        }

    }
}
