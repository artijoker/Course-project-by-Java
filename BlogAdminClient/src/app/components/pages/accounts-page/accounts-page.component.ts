import {Component, OnInit} from '@angular/core';

import {AccountService} from 'src/app/services/account/account.service';

import {AccountModel} from "../../../models/AccountModel";
import {RoleModel} from "../../../models/RoleModel";
import {CategoryModel} from "../../../models/CategoryModel";
import {PostService} from "../../../services/post/post.service";
import {DataService} from "../../../services/data/data.service";
import {Router} from "@angular/router";
import JWTDecode from "jwt-decode";

@Component({
    selector: 'app-accounts-page',
    templateUrl: './accounts-page.component.html',
    styleUrls: ['./accounts-page.component.css']
})
export class AccountsPageComponent implements OnInit {

    accounts: AccountModel[] = [];

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _postService: PostService,
                private _dataService: DataService) {
    }

    ngOnInit(): void {
        let token = localStorage.getItem("token");
        if (token !== null) {
            let tokenDecode: { role: string } = JWTDecode(token);
            if (tokenDecode.role === "ROLE_ADMIN"){
                this._accountService.getAllAccount()
                    .subscribe(response => {
                        if (response.succeeded) {
                            this.accounts = response.result.sort((a, b) => {
                                if (a.registered < b.registered)
                                    return 1;
                                if (a.registered > b.registered)
                                    return -1;
                                return 0;
                            });
                        } else {
                            alert(response.message);
                        }
                    });
            }
            else
                this._router.navigate(
                    ['/']
                );
        } else {
            this._router.navigate(
                ['/']
            );
        }

    }

    addAccount() {
        this._router.navigate(
            ['/add-account']
        )
    }

    editAccount(accountId: number, event:Event) {
        event.stopPropagation();
        this._router.navigate(
            [this._router.url + '/edit-account/', accountId]
        )
    }

    bannedAccount(account: AccountModel) {
        this._accountService.bannedAccount(account.id)
            .subscribe(response => {
                if (response.succeeded) {
                    this._router.navigate(
                        ['/accounts']
                    )
                } else {
                    alert(response.message);
                }
            });
    }

    unlockAccount(account: AccountModel) {
        this._accountService.unlockAccount(account.id)
            .subscribe(response => {
                if (response.succeeded) {
                    this._router.navigate(
                        ['/accounts']
                    )
                } else {
                    alert(response.message);
                }
            });
    }

    deleteAccount(accountId: number) {
        this._accountService.deleteAccount(accountId)
            .subscribe(response => {
                if (response.succeeded) {
                    this._router.navigate(
                        ['/accounts']
                    )
                } else {
                    alert(response.message);
                }
            });
    }

    goToPostsByAccountId(id: number) {
        this._postService.getAuthorPosts(id)
            .subscribe(response => {
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result)
                } else {
                    alert(response.message);
                    this._router.navigate(
                        ['/publications']
                    );
                }
            });

        this._router.navigate(
            ['/posts']
        );
    }

    sortEmail() {
        this.accounts.sort((a, b) => {
            if (a.email > b.email)
                return 1;
            if (a.email < b.email)
                return -1;
            return 0;
        });
    }

    sortName() {
        this.accounts.sort((a, b) => {
            if (a.login > b.login)
                return 1;
            if (a.login < b.login)
                return -1;
            return 0;
        });
    }

    sortRole() {
        this.accounts.sort((a, b) => {
            if (a.roleName > b.roleName)
                return 1;
            if (a.roleName < b.roleName)
                return -1;
            return 0;
        });
    }

    sortDate() {
        this.accounts.sort((a, b) => {
            if (a.registered < b.registered)
                return 1;
            if (a.registered > b.registered)
                return -1;
            return 0;
        });
    }


    sortPublishedPosts() {
        this.accounts.sort((a, b) => {
            if (a.quantityPosts < b.quantityPosts)
                return 1;
            if (a.quantityPosts > b.quantityPosts)
                return -1;
            return 0;
        });
    }

    sortBanned() {
        this.accounts.sort((a, b) => {
            if (a.isBanned < b.isBanned)
                return 1;
            if (a.isBanned > b.isBanned)
                return -1;
            return 0;
        });
    }

    sortDeleted() {
        this.accounts.sort((a, b) => {
            if (a.isDeleted < b.isDeleted)
                return 1;
            if (a.isDeleted > b.isDeleted)
                return -1;
            return 0;
        });
    }
}
