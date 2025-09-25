import { Routes } from '@angular/router';
import {ModuleComponent} from './modul-service/module/module.component';
import {AddModuleComponent} from './modul-service/add-module/add-module.component';
import {StatisticsComponent} from './modul-service/statistics/statistics.component';
import {ModulServiceComponent} from './modul-service/modul-service.component';
import {LoginServiceComponent} from './auth-service/login-service/login-service.component';
import {AppLayoutComponent} from './app-layout/app-layout.component';
import {authenticationGuard} from './guard/authentication.guard';
import {RegisterServiceComponent} from './auth-service/register-service/register-service.component';
import {PwServiceComponent} from './auth-service/pw-service/pw-service.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {GeneralComponent} from './modul-service/statistics/general/general.component';
import {ChartsComponent} from './modul-service/statistics/charts/charts.component';
import {KarteiServiceComponent} from './kartei-service/kartei-service.component';
import {LernenComponent} from './kartei-service/lernen/lernen.component';
import {StapelErstellenComponent} from './kartei-service/stapel-erstellen/stapel-erstellen.component';
import {StapelDetailsComponent} from './kartei-service/stapel-details/stapel-details.component';
import {ModulDetailsComponent} from './modul-service/module/modul-details/modul-details.component';
import {TermineDetailsComponent} from './modul-service/module/termine-details/termine-details.component';
import {AdminServiceComponent} from './auth-service/admin-service/admin-service.component';
import {authorityGuard} from './guard/authority.guard';
import {UnauthorizedComponent} from './auth-service/unauthorized/unauthorized.component';
import { AgbComponent } from './app-layout/footer/agb/agb.component'
import { ImpressumComponent } from './app-layout/footer/impressum/impressum.component'
import { DsgvoComponent } from './app-layout/footer/dsgvo/dsgvo.component'
import {
  MetricServiceComponent
} from './auth-service/admin-service/actuator-service/metric-service/metric-service.component';
import {MindmapServiceComponent} from './mindmap-service/mindmap-service.component';
import {MindmapDetailsComponent} from './mindmap-service/mindmap-details/mindmap-details.component';
import {HelpComponent} from './app-layout/header/help/help.component';
import {KontaktComponent} from './app-layout/footer/kontakt/kontakt.component';
import {PlanServiceComponent} from './plan-service/plan-service.component';
import {PlanCreateComponent} from './plan-service/create/create.component';
import {PlanWeekComponent} from './plan-service/week/week.component';
import {HomeComponent} from './home/home.component';
import {SessionComponent} from './session-service/session.component';
import {SessionStartComponent} from './session-service/start/start.component';
import {SessionCreateComponent} from './session-service/create/create.component';
import {SessionDeleteComponent} from './session-service/delete/delete.component';
import {GroupSessionComponent} from './session-service/group-session/group-session.component';
import {PlanEditComponent} from './plan-service/edit/edit.component';
import {LernplanResolver} from './plan-service/edit/lernplan.resolver';
import {SessionOverviewComponent} from './session-service/overview/overview.component';
import {LernsessionResolver} from './session-service/edit/lernsession.resolver';
import {LernsessionEditComponent} from './session-service/edit/edit.component';

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
    path: 'unauthorized',
    component: UnauthorizedComponent
  },
  {
    path: 'agb',
    component: AgbComponent
  },
  {
    path: 'impressum',
    component: ImpressumComponent
  },
  {
    path: 'datenschutz',
    component: DsgvoComponent
  },
  {
    path: 'kontakt',
    component: KontaktComponent
  },
  {
    path: "hilfe",
    component: HelpComponent
  },
  {
    path: '',
    component: AppLayoutComponent,
    children:
      [
        {
          path: 'home',
          component: HomeComponent,
          canActivate: [authenticationGuard]
        },
        {
          path: 'profil',
          component: UserProfileComponent,
          canActivate: [authenticationGuard]
        },
        {
          path: 'meine-woche',
          component: PlanWeekComponent,
          canActivate: [authenticationGuard]
        },
        {
          path: 'lernplan',
          component: PlanServiceComponent,
        },
        {
          path: 'lernplan/erstellen',
          component: PlanCreateComponent,
        },
        {
          path: 'lernplan/bearbeiten/:lernplanId',
          component: PlanEditComponent,
          resolve: { lernplanToEdit: LernplanResolver }
        },
        {
          path: 'admin',
          component: AdminServiceComponent,
          canActivate: [authenticationGuard, authorityGuard]
        },
        {
          path: 'metrics/:service',
          component: MetricServiceComponent,
          canActivate: [authenticationGuard, authorityGuard]
        },
        {
          path: 'session',
          component: SessionComponent
        },
        {
          path: 'session/overview',
          component: SessionOverviewComponent
        },
        {
          path: 'session/start',
          component: SessionStartComponent
        },
        {
          path: 'session/group/setup',
          component: GroupSessionComponent
        },
        {
          path: 'session/start/:sessionId',
          component: SessionStartComponent
        },
        {
          path: 'session/create',
          component: SessionCreateComponent
        },
        {
          path: 'session/delete',
          component: SessionDeleteComponent
        },
        {
          path: 'session/bearbeiten/:lernsessionId',
          component: LernsessionEditComponent,
          resolve: { lernsessionToEdit: LernsessionResolver }
        },
        {
        path: 'module',
        component: ModulServiceComponent,
        canActivate: [authenticationGuard],
        children:
          [
            { path: 'meine-module',
              component: ModuleComponent,
            },
            {
              path: 'modul-details/:fachId',
              component: ModulDetailsComponent
            },
            {
              path: 'termine-details',
              component: TermineDetailsComponent
            },
            { path: 'add-modul',
              component: AddModuleComponent,
            },
            {
              path: 'statistiken',
              component: StatisticsComponent,
              canActivate: [authenticationGuard],
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
          canActivate: [authenticationGuard]
        },
        {
          path: 'lernen/:fachId',
          component: LernenComponent,
          canActivate: [authenticationGuard]
        },
        {
          path: 'neuer-stapel',
          component: StapelErstellenComponent,
          canActivate: [authenticationGuard]
        },
        {
          path: 'stapel-details/:fachId',
          component: StapelDetailsComponent,
          canActivate: [authenticationGuard]
        },
        {
          path: 'mindmap',
          component: MindmapServiceComponent,
          canActivate: [authenticationGuard],
          children: [
            {
              path: "map-details/:modulId",
              component: MindmapDetailsComponent
            }
          ]
        }]
  },
  { path: '**', redirectTo: 'login' }
];
