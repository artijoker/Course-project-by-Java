import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from './services/authentication/authentication.service';
import JWTDecode from "jwt-decode";
import jwt_decode from "jwt-decode";


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

    isAuthorized: boolean = false;

    constructor(private _router: Router, private _authenticationService: AuthenticationService) {
    }

    ngOnInit(): void {
        if (localStorage.getItem("token")){
            let token = localStorage.getItem("token");
            if (token !== null) {
                let tokenDecode: { role: string } = JWTDecode(token);
                if (tokenDecode.role === "ROLE_ADMIN"){
                    this.isAuthorized = true;
                    this._router.navigate(
                        ['/publications']
                    );
                }
            }
            else{
                this._router.navigate(
                    ['/sing-in']
                );
            }
        }
        else {
            this._authenticationService.authenticator
                .subscribe(value => {
                        this.isAuthorized = value;
                    }
                );
        }

    }

    addPost() {
        this._router.navigate(
            ['/add-post']
        );
    }

    myProfile() {
        this._router.navigate(
            ['/my-profile']
        );
    }

    myPosts() {
        this._router.navigate(
            ['/my-posts']
        );
    }

    exit() {
        localStorage.clear();
        this.isAuthorized = false;
        this._router.navigate(
            ['/']
        );
    }
}
