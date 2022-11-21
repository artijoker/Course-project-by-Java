import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/services/data/data.service';
import { PostService } from 'src/app/services/post/post.service';
import { CategoryService } from 'src/app/services/category/category.service';
import { CategoryModel } from 'src/app/models/CategoryModel';
@Component({
    selector: 'app-blog',
    templateUrl: './blog-page.component.html',
    styleUrls: ['./blog-page.component.css']
})
export class BlogPageComponent implements OnInit {

    categories: CategoryModel[] = [];
    test: string = "false";
    constructor(private _postService: PostService,
        private _categoryService: CategoryService,
        private _dataService: DataService) { }

    ngOnInit(): void {
        this.getCategories();
        this.getAllPosts();

    }

    getCategories() {
        this._categoryService.getCategories()
            .subscribe(response => {
                console.dir(response);
                if (response.succeeded) {
                    this.categories = response.result;
                    this.test = this.categories[0].categoryName;
                }
                else {
                    alert(response.message);
                }
            })
    }

    getAllPosts() {
        this._postService.getPosts()
            .subscribe(response => {
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result)
                }
                else {
                    alert(response.message);
                }
            });
    }

    getPostsByCategory(category: CategoryModel) {
        console.log(" getPostsByCategory(category: CategoryModel)")
        console.dir(category);
        this._postService.getPostsByCategoryId(category.categoryId)
            .subscribe(response => {
                if (response.succeeded) {
                    this._dataService.sendOutPosts(response.result)
                }
                else {
                    alert(response.message);
                }
            });
    }
}


