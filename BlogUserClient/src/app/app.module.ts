import {InjectionToken, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AngularEditorModule} from '@kolkov/angular-editor';
import {AppRoutingModule} from './app-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';

import {PostComponent} from './components/post/post.component';
import {PostDetailsPageComponent} from './components/pages/post-details-page/post-details-page.component';
import {PostsComponent} from './components/posts/posts.component';
import {PostEditorPageComponent} from './components/pages/post-editor-page/post-editor-page.component';


import {AccountEditorPageComponent} from './components/pages/account-editor-page/account-editor-page.component';
import {AuthorComponent} from './components/author/author.component';

import {BlogPageComponent} from './components/pages/blog-page/blog-page.component';
import {AuthorsPageComponent} from './components/pages/authors-page/authors-page.component';
import {CategoriesPageComponent} from './components/pages/categories-page/categories-page.component';

import {SingInPageComponent} from './components/pages/sing-in-page/sing-in-page.component';
import {SingUpPageComponent} from './components/pages/sing-up-page/sing-up-page.component';
import {ProfilePageComponent} from './components/pages/profile-page/profile-page.component';
import {AddPostPageComponent} from './components/pages/add-post-page/add-post-page.component';

import {RejectedPostsPageComponent} from "./components/pages/rejected-posts-page/rejected-posts-page.component";
import {PublishedPostsPageComponent} from "./components/pages/published-posts-page/published-posts-page.component";
import {DraftPostsPageComponent} from "./components/pages/draft-posts-page/draft-posts-page.component";
import {PendingPostsPageComponent} from './components/pages/pending-posts-page/pending-posts-page.component';

import {SafeHtmlPipe} from './pipes/safe-html.pipe';

import {registerLocaleData} from '@angular/common';
import localeRu from '@angular/common/locales/ru';

registerLocaleData(localeRu, 'ru');


@NgModule({
    declarations: [
        AppComponent,
        PostComponent,
        PostDetailsPageComponent,
        PostEditorPageComponent,
        AccountEditorPageComponent,
        BlogPageComponent,
        SingUpPageComponent,
        SingInPageComponent,
        AuthorsPageComponent,
        AuthorComponent,
        PostsComponent,
        CategoriesPageComponent,
        AddPostPageComponent,
        SafeHtmlPipe,
        ProfilePageComponent,

        RejectedPostsPageComponent,
        PublishedPostsPageComponent,
        DraftPostsPageComponent,
        PendingPostsPageComponent

    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        NgbModule,
        AngularEditorModule
    ],
    providers: [
        {provide: LOCALE_ID, useValue: 'ru'},
        {provide: 'HOST_URL', useValue: "http://localhost:8080/"},
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
