import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TasksComponent } from './tasks.component';
import { TasksDetailComponent } from './tasks-detail.component';
import { TasksPopupComponent } from './tasks-dialog.component';
import { TasksDeletePopupComponent } from './tasks-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TasksResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    return {
        page: this.paginationUtil.parsePage('1'),
        sort: 'asc',
        search: null,
        predicate: this.paginationUtil.parsePredicate('id,asc'),
        ascending: this.paginationUtil.parseAscending('id,asc')
    };
  }
}

export const tasksRoute: Routes = [
  {
    path: 'tasks',
    component: TasksComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.tasks.home.title'
    }
  }, {
    path: 'tasks/:id',
    component: TasksDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.tasks.home.title'
    }
  }
];

export const tasksPopupRoute: Routes = [
  {
    path: 'tasks-new',
    component: TasksPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.tasks.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'tasks-edit/:id',
    component: TasksPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.tasks.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'tasks-delete/:id',
    component: TasksDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.tasks.home.title'
    },
    outlet: 'popup'
  }
];
