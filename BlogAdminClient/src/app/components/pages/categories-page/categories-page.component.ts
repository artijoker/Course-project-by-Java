import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PostService} from 'src/app/services/post/post.service';
import {DataService} from 'src/app/services/data/data.service';
import {CategoryService} from 'src/app/services/category/category.service';
import {CategoryModel} from "../../../models/CategoryModel";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CategoryModalWindowComponent} from "../../category-modal-window/category-modal-window.component";
import JWTDecode from "jwt-decode";

@Component({
    selector: 'app-categories-page',
    templateUrl: './categories-page.component.html',
    styleUrls: ['./categories-page.component.css']
})
export class CategoriesPageComponent implements OnInit {

    categories: CategoryModel[] = [];

    isLoad: boolean = false;

    constructor(
        private _router: Router,
        private _categoryService: CategoryService,
        private _postService: PostService,
        private _dataService: DataService,
        private _modalService: NgbModal) {
    }

    ngOnInit(): void {
        let token = localStorage.getItem("token");
        if (token !== null) {
            let tokenDecode: { role: string } = JWTDecode(token);
            if (tokenDecode.role === "ROLE_ADMIN") {
                this.getCategories();
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

    getCategories() {
        this._categoryService.getCategories()
            .subscribe(response => {
                console.dir(response);
                if (response.succeeded) {
                    this.categories = response.result;
                } else {
                    alert(response.message);
                }
            })
    }

    addCategory() {
        let modalRef = this._modalService.open(CategoryModalWindowComponent,
            {size: "lg"});
        modalRef.result.then(
            result => {
                console.log(result);
                if (result !== undefined) {
                    this._categoryService.addNewCategory(result)
                        .subscribe(response => {
                            if (response.succeeded){
                                  this.getCategories();
                            }
                            else{
                                alert(response.message);
                            }
                        })
                }
            }).catch(reason => {
        });
    }

    editCategory(category:CategoryModel, event:Event) {
        event.stopPropagation();
        let modalRef = this._modalService.open(CategoryModalWindowComponent,
            {size: "lg"});
        modalRef.componentInstance.currentName = category.categoryName;
        modalRef.result.then(
            result => {
                if (result !== undefined) {
                    this._categoryService.editCategory(category.categoryId, result).subscribe(
                        response => {
                            if (response.succeeded){
                                this.getCategories();
                            }
                            else{
                                alert(response.message);
                            }
                        }
                    )
                }
            }).catch(reason => {
        });
        this._categoryService.getCategories()
            .subscribe(response => {
                console.dir(response);
                if (response.succeeded) {
                    this.categories = response.result;
                } else {
                    alert(response.message);
                }
            })
    }

    deleteCategory(category:CategoryModel, event:Event) {
        event.stopPropagation();
        this._categoryService.deleteCategory(category.categoryId)
            .subscribe(response => {
                if (response.succeeded) {
                    this.getCategories();
                } else {
                    alert(response.message);
                }
            })
    }


    goToPostsByCategory(category: CategoryModel) {
        this._postService.getPostsByCategoryId(category.categoryId)
            .subscribe(response => {
                console.dir(response);
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result)
                } else {
                    alert(response.message);
                    this._router.navigate(
                        ['/']
                    );
                }

            });
        this._router.navigate(
            ['/posts']
        );
    }


    sortName() {
        this.categories.sort((a, b) => {
            if (a.categoryName < b.categoryName)
                return 1;
            if (a.categoryName > b.categoryName)
                return -1;
            return 0;
        });
    }

    sortQuantityPosts() {
        this.categories.sort((a, b) => {
            if (a.quantityPublishedPosts < b.quantityPublishedPosts)
                return 1;
            if (a.quantityPublishedPosts > b.quantityPublishedPosts)
                return -1;
            return 0;
        });
    }
}
