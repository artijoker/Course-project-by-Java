import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {IResponse} from "../../responses/IResponse";
import {CategoryModel} from "../../models/CategoryModel";
import {RoleModel} from "../../models/RoleModel";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

    private prefixAdmin = "admin/roles/";

    constructor(private _client: HttpClient,
                @Inject('HOST_URL') private _host: string) {
    }


    getRoles(): Observable<IResponse<RoleModel[]>> {
        let token = localStorage.getItem("token");
        let headers = new HttpHeaders().set("Authorization", "Bearer " + token);
        return this._client.get<IResponse<RoleModel[]>>(
            this._host + this.prefixAdmin + "get-roles",
            {headers:headers}
        );
    }
}
