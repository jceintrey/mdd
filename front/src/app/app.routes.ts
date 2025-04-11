import { Routes } from '@angular/router';
import { LoginComponent } from './feature/auth/login/login.component';
import { MeComponent } from './feature/user/component/me/me.component';
import { HomeComponent } from './feature/home/home.component';
import { LandingPageComponent } from './shared/landing-page/landing-page.component';
import { AuthGuard } from './guards/auth.guards';
import { RegisterComponent } from './feature/auth/register/register.component';
import { TopicsComponent } from './feature/topics/topics.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { PostComponent } from './feature/post/post.component';


export const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: 'posts', component: PostComponent, canActivate: [AuthGuard], },
            { path: 'topics', component: TopicsComponent, canActivate: [AuthGuard], },
            { path: 'me', component: MeComponent, canActivate: [AuthGuard], },



        ]
    },
    { path: 'landing', component: LandingPageComponent },
    { path: 'login', component: LoginComponent, },
    { path: 'register', component: LoginComponent, },

];

// export const routes: Routes = [
//     {
//         path: '',
//         redirectTo: 'dashboard',
//         pathMatch: 'full'
//     },
//     {
//         path: 'landing',
//         component: LandingPageComponent
//     },
//     {
//         path: 'home',
//         component: HomeComponent,
//         canActivate: [AuthGuard] // Protection avec AuthGuard
//     },
//     {
//         path: 'me',
//         component: MeComponent,
//         canActivate: [AuthGuard] // Protection avec AuthGuard
//     },
//     {
//         path: 'topics',
//         component: TopicsComponent,
//         canActivate: [AuthGuard] // Protection avec AuthGuard
//     },
//     {
//         path: 'posts',
//         component: PostsComponent,
//         canActivate: [AuthGuard] // Protection avec AuthGuard
//     },
//     {
//         path: 'login',
//         component: LoginComponent,
//     },
//     {
//         path: 'layout',
//         component: LayoutComponent,
//     },
//     {
//         path: '**',
//         redirectTo: 'landing' // Redirection vers landing pour les routes inconnues
//     }
// ];
