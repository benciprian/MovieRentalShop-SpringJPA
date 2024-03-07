package org.example.movierentalsjpa.common.model.validators;


public interface Validator<T> {
    void validate(T entity);
}
