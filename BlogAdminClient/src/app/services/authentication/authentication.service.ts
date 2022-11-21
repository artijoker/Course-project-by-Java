import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Inject, Injectable} from '@angular/core';
import {BehaviorSubject, catchError, Observable, Subject} from 'rxjs';
import {ILogInResponse} from 'src/app/responses/authentication/ILogInResponse';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private prefixUser = "accounts/";
    private source = new BehaviorSubject<boolean>(false);
    authenticator = this.source.asObservable();

    authenticateAccount(value: boolean) {
        this.source.next(value);
    }

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }

    authorize(login: string, password: string): Observable<ILogInResponse> {
        return this._client.post<ILogInResponse>(this._host + this.prefixUser + "login", {
            login: login,
            password: password
        }).pipe(catchError(err => {
            alert(err.message);
            return [];
        }));
    }


}
