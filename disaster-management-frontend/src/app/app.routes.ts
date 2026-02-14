import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { RoleSelectionComponent } from './auth/role-selection/role-selection';
import { Login } from './auth/login/login';
import { RegisterComponent } from './auth/register/register';
import { HomeLayoutComponent } from './home/home-layout';
import { DashboardComponent } from './home/dashboard/dashboard';
import { ReportComponent } from './home/report/report';
import { ManageAlertsComponent } from './home/manage-alerts/manage-alerts';
import { UsersComponent } from './home/users/users';
import { AssignmentsComponent } from './home/assignments/assignments';

export const routes: Routes = [
  { path: '', redirectTo: 'role', pathMatch: 'full' },
  { path: 'role', component: RoleSelectionComponent },
  { path: 'login', component: Login },
  { path: 'register', component: RegisterComponent },
  {
    path: 'home',
    component: HomeLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', component: DashboardComponent },
      { path: 'report', component: ReportComponent },
      { path: 'alerts-manage', component: ManageAlertsComponent },
      { path: 'users', component: UsersComponent },
      { path: 'assignments', component: AssignmentsComponent },
    ],
  },
  { path: '**', redirectTo: 'role' },
];
