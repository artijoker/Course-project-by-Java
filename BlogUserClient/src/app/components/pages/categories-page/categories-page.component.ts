import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryModel } from 'src/app/models/CategoryModel';
import { PostService } from 'src/app/services/post/post.service';
import { DataService } from 'src/app/services/data/data.service';
import { CategoryService } from 'src/app/services/category/category.service';

@Component({
	selector: 'app-categories-page',
	templateUrl: './categories-page.component.html',
	styleUrls: ['./categories-page.component.css']
})
export class CategoriesPageComponent implements OnInit {

	categories: CategoryModel[] = [];



	constructor(
		private _router: Router,
		private _categoryService: CategoryService,
		private _postService: PostService,
		private _dataService: DataService) {
	}

	ngOnInit(): void {
		this.getCategories();
	}

	getCategories() {
        this._categoryService.getCategories()
            .subscribe(response => {
                console.dir(response);
                if (response.succeeded) {
                    this.categories = response.result;
                }
                else {
                    alert(response.message);
                }
            })
    }


	goToPostsByCategory(category: CategoryModel) {
        console.dir(category);
		this._postService.getPostsByCategoryId(category.categoryId)
			.subscribe(response => {
				console.dir(response);
				if (response.succeeded) {
					this._dataService.sendOutPosts(response.result)
				}
				else {
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
