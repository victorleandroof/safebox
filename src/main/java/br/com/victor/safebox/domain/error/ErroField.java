package br.com.victor.safebox.domain.error;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ErroField implements Serializable {

    private String menssage;

    private List<String> field;

    public ErroField(String menssage, List<String> field) {
        this.menssage = menssage;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErroField)) return false;
        ErroField erroField = (ErroField) o;
        return Objects.equals(getMenssage(), erroField.getMenssage()) &&
                Objects.equals(getField(), erroField.getField());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenssage(), getField());
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }
}
