import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Inject, Injectable} from '@angular/core';
import {Observable, BehaviorSubject} from 'rxjs';
import {PostModel} from '../../models/PostModel';
import {AccountModel} from '../../models/AccountModel'
import {CategoryModel} from '../../models/CategoryModel';
import {IResponse} from '../../responses/IResponse';

@Injectable({
    providedIn: 'root'
})

export class PostService {

    private prefixAdmin = "admin/posts/";
    private prefixUser = "posts/";

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }


    getPosts(): Observable<IResponse<PostModel[]>> {
        return this._client.get<IResponse<PostModel[]>>(this._host + this.prefixUser +  "get-posts");
    }

    getPendingPosts(): Observable<IResponse<PostModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<PostModel[]>>(this._host + this.prefixAdmin +"get-pending-posts",
            {headers: headers}
        );
    }


    getAuthorPosts(accountId: number): Observable<IResponse<PostModel[]>> {
        let headers = new HttpHeaders().set('content-type', 'application/json');
        return this._client.post<IResponse<PostModel[]>>(
            this._host + this.prefixUser + "get-author-posts", accountId, {headers: headers});
    }

    getPostsByCategoryId(categoryId: number): Observable<IResponse<PostModel[]>> {
        let headers = new HttpHeaders().set('content-type', 'application/json');
        return this._client.post<IResponse<PostModel[]>>(
            this._host + this.prefixUser + "get-posts-by-category", categoryId, {headers: headers});
    }


    getPost(postId: number): Observable<IResponse<PostModel>> {
        let headers = new HttpHeaders().set('content-type', 'application/json');
        return this._client.post<IResponse<PostModel>>(
            this._host + this.prefixUser + "get-post", postId, {headers: headers});
    }

    getPublishedPostsByAccount(): Observable<IResponse<PostModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<PostModel[]>>(
            this._host + this.prefixUser + "get-published-posts-by-account", {headers: headers});
    }

    getDraftPostsByAccount(): Observable<IResponse<PostModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<PostModel[]>>(
            this._host + this.prefixUser + "get-draft-posts-by-account", {headers: headers});
    }

    getPendingPostsByAccount(): Observable<IResponse<PostModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<PostModel[]>>(
            this._host + this.prefixUser + "get-pending-posts-by-account", {headers: headers});
    }

    getRejectedPostsByAccount(): Observable<IResponse<PostModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<PostModel[]>>(
            this._host + this.prefixUser + "get-rejected-posts-by-account", {headers: headers});
    }



    addNewPost(title: string, anons: string, fullText: string, categoryId: number): Observable<IResponse> {
        let body = {
            title: title,
            anons: anons,
            fullText: fullText,
            categoryId: categoryId

        };

        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixUser + "add-post",
            body, {headers: headers}
        );
    }

    addPostAndPublished(title: string, anons: string, fullText: string, categoryId: number): Observable<IResponse> {
        let body = {
            title: title,
            anons: anons,
            fullText: fullText,
            categoryId: categoryId

        };

        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixAdmin + "add-post-and-published",
            body, {headers: headers}
        );
    }

    updatePost(postId: number, title: string, anons: string, fullText: string, categoryId: number): Observable<IResponse<Object>> {
        let body = {
            postId: postId,
            title: title,
            anons: anons,
            fullText: fullText,
            categoryId: categoryId

        };

        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixUser + "update-post",
            body, {headers: headers});
    }

    removePost(postId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixUser + "delete-post",
            postId, {headers: headers});
    }


    publishPost(postId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixAdmin + "publish-post",
            postId, {headers: headers});
    }

    rejectPost(postId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixAdmin + "reject-post",
            postId, {headers: headers});
    }

    sendPostToDraft(postId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(this._host + this.prefixUser + "send-post-to-draft",
            postId, {headers: headers});
    }


}
