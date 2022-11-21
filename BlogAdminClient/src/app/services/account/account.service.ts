import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {IResponse} from '../../responses/IResponse';
import {AccountModel} from "../../models/AccountModel";


@Injectable({
    providedIn: 'root'
})
export class AccountService {

    private prefixAdmin = "admin/accounts/";
    private prefixUser = "accounts/";

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }

    getAllAccount(): Observable<IResponse<AccountModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<AccountModel[]>>(
            this._host + this.prefixAdmin + "get-all-account",
            {headers: headers});
    }

    getAccount(): Observable<IResponse<AccountModel>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<AccountModel>>(
            this._host + this.prefixUser + "get-account",
            {headers: headers});
    }

    getAccountById(accountId: number): Observable<IResponse<AccountModel>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");
        return this._client.post<IResponse<AccountModel>>(
            this._host + this.prefixAdmin + "get-account",
            accountId,
            {headers: headers});
    }

    addNewAccount(email: string, login: string, password: string, roleId: number)
        : Observable<IResponse<AccountModel>> {
        let body = {
            email: email,
            login: login,
            password: password,
            roleId: roleId
        }
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse<AccountModel>>(
            this._host + this.prefixAdmin + "add-new-account", body, {headers: headers}
        );
    }

    updateAccount(accountId: number, email: string, login: string, newPassword: string, roleId: number)
        : Observable<IResponse<AccountModel>> {
        let body = {
            accountId: accountId,
            email: email,
            login: login,
            newPassword: newPassword,
            roleId: roleId
        }
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse<AccountModel>>(
            this._host + this.prefixAdmin + "update-account", body, {headers: headers}
        );
    }


    bannedAccount(accountId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");
        return this._client.post<IResponse>(this._host + this.prefixAdmin + "banned-account",
            accountId,
            {headers: headers});
    }

    unlockAccount(accountId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");
        return this._client.post<IResponse>(this._host + this.prefixAdmin + "unlock-account",
            accountId,
            {headers: headers});
    }

    deleteAccount(accountId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");
        return this._client.post<IResponse>(this._host + this.prefixAdmin + "delete-account",
            accountId,
            {headers: headers});
    }
}
