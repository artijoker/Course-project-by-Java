import {Component, OnInit} from '@angular/core';
import {DataService} from 'src/app/services/data/data.service';
import {PostService} from 'src/app/services/post/post.service';
import {CategoryService} from 'src/app/services/category/category.service';
import {CategoryModel} from 'src/app/models/CategoryModel';
import JWTDecode from "jwt-decode";
import {Router} from "@angular/router";

@Component({
    selector: 'app-publications',
    templateUrl: './publications-page.component.html',
    styleUrls: ['./publications-page.component.css']
})
export class PublicationsPageComponent implements OnInit {

    constructor(private _router: Router,
                private _postService: PostService,
                private _dataService: DataService) {
    }

    ngOnInit(): void {

        let token = localStorage.getItem("token");
        if (token !== null) {
            let tokenDecode: { role: string } = JWTDecode(token);
            if (tokenDecode.role === "ROLE_ADMIN"){
                this.getPublishedPosts();
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

    getPublishedPosts() {
        this._postService.getPosts()
            .subscribe(response => {
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result);
                } else {
                    alert(response.message);
                }
            });
    }

    getPendingPosts() {
        this._postService.getPendingPosts()
            .subscribe(response => {
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result);
                } else {
                    alert(response.message);
                }
            });
    }
}


