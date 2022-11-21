import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ILogInResponse } from 'src/app/responses/authentication/ILogInResponse';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
    selector: 'app-sing-in-page',
    templateUrl: './sing-in-page.component.html',
    styleUrls: ['./sing-in-page.component.css']
})
export class SingInPageComponent implements OnInit {

    formSingIn: FormGroup;


    constructor(private _router: Router,
        private _authenticationService: AuthenticationService) {

        this.formSingIn = new FormGroup({
            "login": new FormControl("", Validators.required),
            "password": new FormControl("", Validators.required)
        });
    }
    ngOnInit(): void {

    }

    submit() {
        if (this.formSingIn.valid) {
            let value = this.formSingIn.value;
            this._authenticationService.authorize(value.login, value.password)
                .subscribe((response) => {
                    if (response.succeeded) {
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
            if (!this.formSingIn.controls['login'].valid)
                alert("Введите логин!");
            else
                alert("Введите пароль!");

        }
    }

}
