import { Component, OnInit } from '@angular/core';

import { AccountService } from 'src/app/services/account/account.service';
import {AccountModel} from "../../../models/AccountModel";

@Component({
  selector: 'app-authors-page',
  templateUrl: './authors-page.component.html',
  styleUrls: ['./authors-page.component.css']
})
export class AuthorsPageComponent implements OnInit {

  accountModels: AccountModel[] = [];

  constructor(private _accountService: AccountService) { }

  ngOnInit(): void {
    this._accountService.getAuthors()
      .subscribe(response => {
        if (response.succeeded) {
          this.accountModels = response.result;
            this.accountModels.sort(
                (a, b) => {
                    if (a.quantityPosts < b.quantityPosts)
                        return 1;
                    if (a.quantityPosts > b.quantityPosts)
                        return -1;
                    return 0;
                })
        }
        else {
          alert(response.message);
        }
      });
  }

}
