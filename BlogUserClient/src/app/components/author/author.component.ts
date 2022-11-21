import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {DataService} from 'src/app/services/data/data.service';
import {PostService} from 'src/app/services/post/post.service';
import {AccountModel} from "../../models/AccountModel";

@Component({
    selector: 'app-author',
    templateUrl: './author.component.html',
    styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

    @Input() accountModel?: AccountModel

    constructor(
        private _router: Router,
        private _postService: PostService,
        private _dataService: DataService) {
    }

    ngOnInit(): void {
    }

    goToPostsByAuthorId(id: number) {
        this._postService.getAuthorPosts(id)
            .subscribe(response => {
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result)
                } else {
                    alert(response.message);
                    this._router.navigate(
                        ['/blog']
                    );
                }
            });

        this._router.navigate(
            ['/posts']
        );
    }
}
