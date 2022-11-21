import { Component, OnInit } from '@angular/core';
import {AccountModel} from "../../../models/AccountModel";
import {Router} from "@angular/router";
import {AccountService} from "../../../services/account/account.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ILogInResponse} from "../../../responses/authentication/ILogInResponse";

@Component({
  selector: 'app-account-editor-page',
  templateUrl: './account-editor-page.component.html',
  styleUrls: ['./account-editor-page.component.css']
})
export class AccountEditorPageComponent implements OnInit {

    form: FormGroup;
    accountMode?: AccountModel;
    constructor(private _router: Router, private _accountService: AccountService) {
        this.form = new FormGroup({
            "email": new FormControl("", Validators.email
            ),
            "login": new FormControl(""),
            "newPassword": new FormControl("")
        });
    }

    ngOnInit(): void {
        this._accountService.getAccount().subscribe(response => {
            this.accountMode = response.result;
        })
    }

    submit() {
        if (this.form.valid) {
            let value = this.form.value;
            let email = value.email === "" ? this.accountMode!.email : value.email;
            let login = value.login === "" ? this.accountMode!.login : value.login;
            let newPassword = value.newPassword;
            this._accountService.updateAccount(email, login, newPassword)
                .subscribe((response) => {
                    if (response.succeeded) {
                        this._router.navigate(
                            ['/my-profile']
                        );
                    }
                    else {
                        alert(response.message);
                    }
                });
        }
        else{
            if (!this.form.controls['email'].valid)
                alert("Невалидный  email!");
        }

    }

}
