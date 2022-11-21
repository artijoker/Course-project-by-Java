import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PostModel} from 'src/app/models/PostModel';
import {PostService} from 'src/app/services/post/post.service';
import {AccountService} from "../../../services/account/account.service";
import {Location} from '@angular/common';
import {DataService} from "../../../services/data/data.service";

@Component({
    selector: 'app-post-details-page',
    templateUrl: './post-details-page.component.html',
    styleUrls: ['./post-details-page.component.css']
})
export class PostDetailsPageComponent implements OnInit {

    post?: PostModel;
    id: number;
    isEdited: boolean = false;

    constructor(private _activateRoute: ActivatedRoute,
                private _router: Router,
                private _location: Location,
                private _postService: PostService,
                private _accountService: AccountService,
                private _dataService: DataService) {
        this.id = this._activateRoute.snapshot.params['id'];
    }

    ngOnInit(): void {
        this._postService.getPost(this.id)
            .subscribe(response => {
                if (response.succeeded) {
                    this.post = response.result
                    if (localStorage.getItem("token") !== null){
                        this._accountService.getAccount()
                            .subscribe(response => {
                                if (response.succeeded) {
                                    this.isEdited = response.result.id === this.post?.accountId
                                } else {
                                    alert(response.message);
                                }
                            })
                    }
                } else {
                    alert(response.message);
                }
            })

    }

    goToPostEditor() {
        this._router.navigate(
            [this._router.url + '/edit-post', this.post?.postId]
        );
    }

    goToPostsByAuthor() {
        if (this.post?.accountId !== undefined) {
            this._postService.getAuthorPosts(this.post?.accountId)
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

    sendPostToDraft() {
        this._postService.sendPostToDraft(this.id)
            .subscribe(response => {
                if (response.succeeded) {
                    alert("Статья перемещена в черновики");
                } else {
                    alert(response.message);
                }
            })
    }

    deletePost() {
        this._postService.removePost(this.id)
            .subscribe(response => {
                if (response.succeeded) {
                    alert("Статья удалена");
                } else {
                    alert(response.message);
                }
            })
        this._location.back();
    }
}
