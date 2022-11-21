import {HttpClient} from '@angular/common/http';
import {Inject, Injectable} from '@angular/core';
import {BehaviorSubject, catchError, Observable, Subject} from 'rxjs';
import {ILogInResponse} from 'src/app/responses/authentication/ILogInResponse';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private prefix = "accounts/";

    private source = new Subject<boolean>();
    authenticator = this.source.asObservable();


    authenticateAccount(value: boolean) {
        this.source.next(value);
    }

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }

    registration(email: string, login: string, password: string): Observable<ILogInResponse> {
        return this._client.post<ILogInResponse>(this._host + this.prefix + "registration", {
            email: email,
            login: login,
            password: password
        }).pipe(catchError(err => {
            alert(err.message);
            return [];
        }));
    }

    authorize(login: string, password: string): Observable<ILogInResponse> {
        return this._client.post<ILogInResponse>(this._host + this.prefix + "login", {
            login: login,
            password: password
        }).pipe(catchError(err => {
            alert(err.message);
            return [];
        }));
    }

}
