package com.example.blog.server.controllers.admin;

import com.example.blog.domain.exceptions.*;
import com.example.blog.domain.services.AccountService;
import com.example.blog.domain.services.RegistrationService;
import com.example.blog.http.models.requests.AdminAddingAccountRequestModel;
import com.example.blog.http.models.requests.AdminUpdateAccountRequestModel;
import com.example.blog.http.models.responses.AccountResponseModel;
import com.example.blog.http.models.responses.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin/accounts")
@CrossOrigin("*")
public class AdminAccountController {

    private final AccountService accountService;
    private final RegistrationService registrationService;

    @Autowired
    public AdminAccountController(AccountService accountService, RegistrationService registrationService) {
        this.accountService = accountService;
        this.registrationService = registrationService;
    }

    @PostMapping("/get-account")
    public ResponseModel<AccountResponseModel> getAccount(@RequestBody int accountId)
            throws NotFoundAccountException {
        var response = new ResponseModel<AccountResponseModel>();
        response.setSucceeded(true);
        response.setResult(accountService.getAccountById(accountId));

        return response;
    }

    @GetMapping("/get-all-account")
    public ResponseModel<List<AccountResponseModel>> getAllAccounts() {
        var response = new ResponseModel<List<AccountResponseModel>>();
        response.setSucceeded(true);
        response.setResult(accountService.getAccountsAndCountPublishedPostsForEach());

        return response;
    }

    @PostMapping("/add-new-account")
    public ResponseModel<?> addAccount(@RequestBody @Valid AdminAddingAccountRequestModel model,
                                       BindingResult bindingResult)
            throws DuplicateEmailException, DuplicateLoginException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var response = new ResponseModel<>();
        registrationService.adminAddNewAccount(
                model.getEmail(),
                model.getLogin(),
                model.getPassword(),
                model.getRoleId()
        );
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/update-account")
    public ResponseModel<?> updateAccount(@RequestBody @Valid AdminUpdateAccountRequestModel model,
                                          BindingResult bindingResult)
            throws NotFoundAccountException, DuplicateEmailException,
            DefaultAdminException, DuplicateLoginException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var response = new ResponseModel<>();
        accountService.adminUpdateAccount(
                model.getAccountId(),
                model.getEmail(),
                model.getLogin(),
                model.getNewPassword(),
                model.getRoleId()
        );
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/banned-account")
    public ResponseModel<?> bannedAccount(@RequestBody int accountId)
            throws NotFoundAccountException, DefaultAdminException {
        var response = new ResponseModel<>();
        accountService.banAccount(accountId);
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/unlock-account")
    public ResponseModel<?> unlockAccount(@RequestBody int accountId)
            throws NotFoundAccountException {
        var response = new ResponseModel<>();
        accountService.unlockAccount(accountId);
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/delete-account")
    public ResponseModel<?> deleteAccount(@RequestBody int accountId)
            throws DefaultAdminException, NotFoundAccountException {
        var response = new ResponseModel<>();
        accountService.removeAccount(accountId);
        response.setSucceeded(true);

        return response;
    }


}
