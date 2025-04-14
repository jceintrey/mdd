import { Routes } from '@angular/router';
import { LoginComponent } from './feature/auth/login/login.component';
import { MeComponent } from './feature/user/component/me/me.component';
import { LandingPageComponent } from './shared/landing-page/landing-page.component';
import { AuthGuard } from './guards/auth.guards';
import { RegisterComponent } from './feature/auth/register/register.component';
import { TopicsComponent } from './feature/topics/main/topics.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { PostComponent } from './feature/post/main/post.component';
import { CreatepostComponent } from './feature/post/createpost/createpost.component';
import { PostDetailsComponent } from './feature/post/postdetails/postdetails.component';


export const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: 'posts', component: PostComponent, canActivate: [AuthGuard], },
            { path: 'topics', component: TopicsComponent, canActivate: [AuthGuard], },
            { path: 'me', component: MeComponent, canActivate: [AuthGuard], },
            { path: 'posts/create', component: CreatepostComponent, canActivate: [AuthGuard], },
            { path: 'posts/:id', component: PostDetailsComponent, canActivate: [AuthGuard], }


        ]
    },
    { path: 'landing', component: LandingPageComponent },
    { path: 'login', component: LoginComponent, },
    { path: 'register', component: RegisterComponent, },

];
