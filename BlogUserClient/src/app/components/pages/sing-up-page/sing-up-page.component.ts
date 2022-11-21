import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ILogInResponse } from 'src/app/responses/authentication/ILogInResponse';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

import { AccountService } from '../../../services/account/account.service'

@Component({
    selector: 'app-sing-up-page',
    templateUrl: './sing-up-page.component.html',
    styleUrls: ['./sing-up-page.component.css']
})
export class SingUpPageComponent implements OnInit {

    formSingUp: FormGroup;

    constructor(private _router: Router,
        private _authenticationService: AuthenticationService) {

        this.formSingUp = new FormGroup({
            "email": new FormControl("", [
                Validators.required,
                Validators.email
            ]),
            "login": new FormControl("", Validators.required),
            "password": new FormControl("", Validators.required)
        });
    }
    ngOnInit(): void {

    }

    submit() {
        if (this.formSingUp.valid) {
            let value = this.formSingUp.value;
            this._authenticationService.registration(value.email, value.login, value.password)
                .subscribe((response: ILogInResponse) => {
                    if (response.succeeded) {
                        localStorage.setItem("account", JSON.stringify({
                            id: response.result.id,
                            login: response.result.login,
                            email: response.result.email,
                            registered: response.result.registered
                        }));
                        localStorage.setItem("token", response.token);
                        this._authenticationService.authenticateAccount(true);
                        this._router.navigate(
                            ['/blog']
                        );
                    }
                    else {
                        alert(response.message);
                        this._authenticationService.authenticateAccount(false);
                    }
                });
        }
        else{
            if (!this.formSingUp.controls['email'].valid)
                alert("Невалидный  email!");
            else if (!this.formSingUp.controls['login'].valid)
                alert("Невалидный логин!");
            else
                alert("Невалидный пароль!");
        }

    }

}
