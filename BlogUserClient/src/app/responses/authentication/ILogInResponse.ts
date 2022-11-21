import { AccountModel } from "src/app/models/AccountModel";
import { IResponse } from "../IResponse";

export interface ILogInResponse extends IResponse<AccountModel> {
    token: string;
}
