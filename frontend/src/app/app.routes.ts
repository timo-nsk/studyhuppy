import { Routes } from '@angular/router';
import {ModuleComponent} from './modul-service/module/module.component';
import {AddModuleComponent} from './modul-service/add-module/add-module.component';
import {OptionsComponent} from './modul-service/options/options.component';
import {StatisticsComponent} from './modul-service/statistics/statistics.component';
import {ModulServiceComponent} from './modul-service/modul-service.component';
import {LoginServiceComponent} from './auth-service/login-service/login-service.component';
import {AppLayoutComponent} from './app-layout/app-layout.component';
import {authGuard} from './guard/auth.guard';
import {RegisterServiceComponent} from './auth-service/register-service/register-service.component';
import {PwServiceComponent} from './auth-service/pw-service/pw-service.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {GeneralComponent} from './modul-service/statistics/general/general.component';
import {ChartsComponent} from './modul-service/statistics/charts/charts.component';
import {KarteiServiceComponent} from './kartei-service/kartei-service.component';
import {StapelDetailsComponent} from './kartei-service/stapel-details/stapel-details.component';

export const routes: Routes = [
  {
    path: 'register',
    component: RegisterServiceComponent
  },
  {
    path: 'login',
    component: LoginServiceComponent
  },
  {
    path: 'reset-pw',
    component: PwServiceComponent
  },
  {
    path: '',
    component: AppLayoutComponent,
    children:
      [
        {
          path: 'profil',
          component: UserProfileComponent,
          canActivate: [authGuard]
        },
        {
        path: 'module',
        component: ModulServiceComponent,
        canActivate: [authGuard],
        children:
          [
            { path: 'meine-module',
              component: ModuleComponent,
              canActivate: [authGuard]
            },
            { path: 'add-modul',
              component: AddModuleComponent,
              canActivate: [authGuard]
            },
            {
              path: 'einstellungen',
              component: OptionsComponent,
              canActivate: [authGuard]
            },
            {
              path: 'statistiken',
              component: StatisticsComponent,
              canActivate: [authGuard],
              children:
                [
                  {
                    path: 'general',
                    component: GeneralComponent
                  },
                  {
                    path: 'charts',
                    component: ChartsComponent
                  }
                ]
            }
          ]
      },
        {
          path: 'kartei',
          component: KarteiServiceComponent,
          canActivate: [authGuard]
        },
        {
          path: 'stapel-detail',
          component: StapelDetailsComponent,
          canActivate: [authGuard]
        }]
  },
  { path: '**', redirectTo: 'login' }
];
