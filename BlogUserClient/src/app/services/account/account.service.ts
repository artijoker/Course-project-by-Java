import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AccountModel} from '../../models/AccountModel'
import {IResponse} from '../../responses/IResponse';


@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) { }

    private prefix = "accounts/";

    private getHttpHeaders(){
        let token = localStorage.getItem("token");
        return new HttpHeaders().set("Authorization", "Bearer " + token);

    }

    getAuthors(): Observable<IResponse<AccountModel[]>> {
        return this._client.get<IResponse<AccountModel[]>>(
            this._host + this.prefix +  "get-authors"
        );
    }


    getAccount(): Observable<IResponse<AccountModel>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<AccountModel>>(this._host + this.prefix +  "get-account",
        {headers:headers});
    }


    updateAccount(email: string, login: string,  newPassword: string): Observable<IResponse> {
        let body = {
            email: email,
            login: login,
            newPassword: newPassword
        }
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(
            this._host + this.prefix +  "update-account", body,{headers:headers}
        );
    }

    deleteAccount(): Observable<IResponse> {

        return this._client.get<IResponse>(this._host + this.prefix +  "delete-account",
            {headers:this.getHttpHeaders()});
    }
}
