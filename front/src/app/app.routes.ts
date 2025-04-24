import { Routes } from '@angular/router';
import { LoginComponent } from './feature/auth/login/login.component';
import { MeComponent } from './feature/user/component/me/me.component';
import { GreetingComponent } from './shared/greeting-page/greeting-page.component';
import { AuthGuard } from './guards/auth.guards';
import { RegisterComponent } from './feature/auth/register/register.component';
import { TopicsComponent } from './feature/topics/main/topics.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { PostsComponent } from './feature/posts/main/posts.component';
import { CreatepostComponent } from './feature/posts/createpost/createpost.component';
import { PostDetailsComponent } from './feature/posts/postdetails/postdetails.component';


export const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: 'posts', component: PostsComponent, canActivate: [AuthGuard], },
            { path: 'topics', component: TopicsComponent, canActivate: [AuthGuard], },
            { path: 'me', component: MeComponent, canActivate: [AuthGuard], },
            { path: 'posts/create', component: CreatepostComponent, canActivate: [AuthGuard], },
            { path: 'posts/:id', component: PostDetailsComponent, canActivate: [AuthGuard], },
            { path: '', redirectTo: 'posts', pathMatch: 'full' }
        ]
    },
    { path: 'greeting', component: GreetingComponent },
    { path: 'login', component: LoginComponent, },
    { path: 'register', component: RegisterComponent, },

];
