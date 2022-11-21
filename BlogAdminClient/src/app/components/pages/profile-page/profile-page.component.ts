import {Component, OnInit} from '@angular/core';
import {AccountService} from "../../../services/account/account.service";
import {AccountModel} from "../../../models/AccountModel";
import {Router} from "@angular/router";
import JWTDecode from "jwt-decode";

@Component({
    selector: 'app-profile-page',
    templateUrl: './profile-page.component.html',
    styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

    accountMode?: AccountModel;

    constructor(private _router: Router, private _accountService: AccountService) {
    }

    ngOnInit(): void {
        let token = localStorage.getItem("token");
        if (token !== null) {
            let tokenDecode: { role: string } = JWTDecode(token);
            if (tokenDecode.role === "ROLE_ADMIN"){
                this._accountService.getAccount().subscribe(response => {
                    if (response.succeeded) {
                        this.accountMode = response.result;
                    } else {
                        alert(response.message);
                    }
                })
            } else
                this._router.navigate(
                    ['/']
                );
        } else {
            this._router.navigate(
                ['/']
            );
        }

    }

    editAccount() {
        this._router.navigate(
            [this._router.url + '/edit-account/', this.accountMode?.id]
        );
    }
}
