import { Routes } from '@angular/router';
import { LoginComponent } from './feature/auth/component/login/login.component';
import { MeComponent } from './feature/user/component/me/me.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'me', component: MeComponent },
    
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];
