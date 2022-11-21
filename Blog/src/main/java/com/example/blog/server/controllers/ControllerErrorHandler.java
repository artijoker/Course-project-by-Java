package com.example.blog.server.controllers;

import com.example.blog.domain.exceptions.*;
import com.example.blog.http.models.responses.ResponseModel;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseModel<?> handleException(ValidationException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(DeletedAccountException.class)
    public ResponseModel<?> handleException(DeletedAccountException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Аккаунт удален");
        return response;
    }

    @ExceptionHandler(BannedAccountException.class)
    public ResponseModel<?> handleException(BannedAccountException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Аккаунт заблокирован");
        return response;
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseModel<?> handleException(InvalidLoginException loginException) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Неверный логин или пароль");
        return response;
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseModel<?> handleException(InvalidPasswordException passwordException) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Неверный логин или пароль");
        return response;
    }

    @ExceptionHandler(DefaultAdminException.class)
    public ResponseModel<?> handleException(DefaultAdminException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Учетную запись главного администратора нельзя изменять, удалить или заблокировать");
        return response;
    }

    @ExceptionHandler(NotFoundAccountException.class)
    public ResponseModel<?> handleException(NotFoundAccountException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Учетная запись не найдена");
        return response;
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseModel<?> handleException(DuplicateEmailException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Пользователь с таким email или уже существует");
        return response;
    }

    @ExceptionHandler(DuplicateLoginException.class)
    public ResponseModel<?> handleException(DuplicateLoginException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Пользователь с таким логином уже существует");
        return response;
    }



    @ExceptionHandler(NotFoundPostException.class)
    protected ResponseModel<?> handleAllExceptions(NotFoundPostException ex) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Статья не найдена");
        return response;
    }


    @ExceptionHandler(NotFoundCategoryException.class)
    protected ResponseModel<?> handleAllExceptions(NotFoundCategoryException ex) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Категория не найдена");
        return response;
    }

    @ExceptionHandler(DefaultCategoryException.class)
    public ResponseModel<?> handleException(DefaultCategoryException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Категорию по умолчанию нельзя изменит или удалить");
        return response;
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseModel<?> handleException(DuplicateCategoryException e) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setMessage("Категоря с таким именем уже существует");
        return response;
    }



    @ExceptionHandler(Exception.class)
    protected ResponseModel<?> handleAllExceptions(Exception ex) {
        var response = new ResponseModel<>();
        response.setSucceeded(false);
        response.setBug(true);
        response.setMessage("Неизвестная ошибка БАГ");
        return response;
    }
}
