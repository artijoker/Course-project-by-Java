import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {PostModel} from 'src/app/models/PostModel';
import {DataService} from 'src/app/services/data/data.service';

@Component({
    selector: 'app-posts',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

    isLoadPost: boolean = true;
    posts: PostModel[] = [];

    constructor(private _dataService: DataService) {
    }

    ngOnInit(): void {
        this._dataService.currentPosts.subscribe(
            posts => {
                this.posts = posts;
                this.isLoadPost = false;
                this.posts.sort(
                    (a, b) => {
                        if (a.lastChange < b.lastChange)
                            return 1;
                        if (a.lastChange > b.lastChange)
                            return -1;
                        return 0;
                    })
            }
        )
    }

}
