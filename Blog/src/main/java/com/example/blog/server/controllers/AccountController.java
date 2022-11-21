package com.example.blog.server.controllers;

import com.example.blog.domain.exceptions.*;
import com.example.blog.domain.services.AccountService;
import com.example.blog.domain.services.AuthorizationService;
import com.example.blog.domain.services.RegistrationService;
import com.example.blog.http.models.requests.UpdateAccountRequestModel;
import com.example.blog.http.models.requests.LogInRequestModel;
import com.example.blog.http.models.requests.RegistrationRequestModel;
import com.example.blog.http.models.responses.AccountResponseModel;
import com.example.blog.http.models.responses.LogInResponseModel;
import com.example.blog.http.models.responses.ResponseModel;
import com.example.blog.server.security.AccountDetails;
import com.example.blog.server.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
public class AccountController {

    private final AccountService accountService;
    private final RegistrationService registrationService;
    private final AuthorizationService authorizationService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AccountController(AccountService accountService,
                             RegistrationService registrationService,
                             AuthorizationService authorizationService, JwtTokenUtil jwtTokenUtil) {
        this.accountService = accountService;
        this.registrationService = registrationService;
        this.authorizationService = authorizationService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @GetMapping("/get-authors")
    public ResponseModel<List<AccountResponseModel>> getAuthors() {
        var response = new ResponseModel<List<AccountResponseModel>>();
        response.setSucceeded(true);
        response.setResult(accountService.getAccountsThatHavePublishedPosts());

        return response;
    }



    @PostMapping("/registration")
    public LogInResponseModel registerAccount(@RequestBody @Valid RegistrationRequestModel model,
                                              BindingResult bindingResult)
            throws DuplicateEmailException, DuplicateLoginException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var accountModel = registrationService.registerAccount(
                model.getEmail(),
                model.getLogin(),
                model.getPassword()
        );
        String token = jwtTokenUtil.generateToken(accountModel.getId(), accountModel.getRoleName());

        var response = new LogInResponseModel();
        response.setSucceeded(true);
        response.setResult(accountModel);
        response.setToken(token);
        return response;
    }

    @PostMapping("/login")
    public LogInResponseModel logIn(@RequestBody @Valid LogInRequestModel model,
                                    BindingResult bindingResult)
            throws InvalidLoginException, InvalidPasswordException,
            BannedAccountException, DeletedAccountException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var accountModel = authorizationService.Authorize(
                model.getLogin(),
                model.getPassword()
        );
        String token = jwtTokenUtil.generateToken(accountModel.getId(), accountModel.getRoleName());

        var response = new LogInResponseModel();
        response.setSucceeded(true);
        response.setToken(token);
        response.setResult(authorizationService.Authorize(model.getLogin(), model.getPassword()));

        return response;
    }

    @GetMapping("/get-account")
    public ResponseModel<AccountResponseModel> getAccount()
            throws NotFoundAccountException {

        AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

                var response = new ResponseModel<AccountResponseModel>();
        response.setSucceeded(true);
        response.setResult(accountService.getAccountByIdAndCountPublishedPosts(accountDetails.account().getId()));

        return response;
    }

    @PostMapping("/update-account")
    public ResponseModel<?> updateAccount(@RequestBody @Valid UpdateAccountRequestModel model,
                                          BindingResult bindingResult)
            throws NotFoundAccountException, DuplicateEmailException,
            DuplicateLoginException, ValidationException, DefaultAdminException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<>();

        accountService.userUpdateAccount(accountDetails.account().getId(), model.getEmail(), model.getLogin(), model.getNewPassword());
        response.setSucceeded(true);

        return response;
    }

    @GetMapping("/delete-account")
    public ResponseModel<?> removeAccount() throws DefaultAdminException, NotFoundAccountException {

        AccountDetails personDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<>();
        accountService.removeAccount(personDetails.account().getId());
        response.setSucceeded(true);

        return response;
    }
}
