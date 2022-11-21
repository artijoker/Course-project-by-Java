import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from './services/authentication/authentication.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

    isAuthorized: boolean;

    constructor(private _router: Router, private _authenticationService: AuthenticationService) {
        this.isAuthorized = false;
    }

    ngOnInit(): void {
        if (localStorage.getItem("token"))
            this.isAuthorized = true;
        else {
            this._authenticationService.authenticator
                .subscribe(value => {
                        this.isAuthorized = value;
                    }
                );
        }

    }

    singUp() {
        this._router.navigate(
            ['/sing-up']
        );
    }

    singIn() {
        this._router.navigate(
            ['/sing-in']
        );
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
            ['/blog']
        );
    }
}
