import { IResponse } from "../IResponse";
import {AccountModel} from "../../models/AccountModel";

export interface ILogInResponse extends IResponse<AccountModel> {
    token: string;
}
