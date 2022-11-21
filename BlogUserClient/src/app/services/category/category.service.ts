import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CategoryModel} from '../../models/CategoryModel';
import {IResponse} from '../../responses/IResponse';

@Injectable({
    providedIn: 'root'
})
export class CategoryService {

    private prefix = "categories/";

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }


    getCategories(): Observable<IResponse<CategoryModel[]>> {
        return this._client.get<IResponse<CategoryModel[]>>(
            this._host  + this.prefix +  "get-categories"
        );
    }

}
