import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CategoryModel} from '../../models/CategoryModel';
import {IResponse} from '../../responses/IResponse';

@Injectable({
    providedIn: 'root'
})
export class CategoryService {

    private prefixAdmin = "admin/categories/";
    private prefixUser = "categories/";

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }


    getCategories(): Observable<IResponse<CategoryModel[]>> {
        return this._client.get<IResponse<CategoryModel[]>>(
            this._host + this.prefixUser + "get-categories");
    }

    addNewCategory(categoryName: string): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");
        return this._client.post<IResponse>(
            this._host + this.prefixAdmin + "add-new-category",
            {categoryName: categoryName},
            {headers: headers}
        );
    }

    editCategory(categoryId: number, categoryName: string): Observable<IResponse> {
        let body = {
            categoryId: categoryId,
            categoryName: categoryName
        }

        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");

        return this._client.post<IResponse>(
            this._host + this.prefixAdmin + "update-category",
            body,
            {headers: headers}
        );
    }

    deleteCategory(categoryId: number): Observable<IResponse> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token)
            .set("content-type", "application/json");
        return this._client.post<IResponse>(
            this._host + this.prefixAdmin + "delete-category",
            categoryId,
            {headers: headers}
        );
    }
}


