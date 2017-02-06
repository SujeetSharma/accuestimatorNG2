import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaskCategoryComponent } from './task-category.component';
import { TaskCategoryDetailComponent } from './task-category-detail.component';
import { TaskCategoryPopupComponent } from './task-category-dialog.component';
import { TaskCategoryDeletePopupComponent } from './task-category-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TaskCategoryResolvePagingParams implements Resolve<any> {

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

export const taskCategoryRoute: Routes = [
  {
    path: 'task-category',
    component: TaskCategoryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.taskCategory.home.title'
    }
  }, {
    path: 'task-category/:id',
    component: TaskCategoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.taskCategory.home.title'
    }
  }
];

export const taskCategoryPopupRoute: Routes = [
  {
    path: 'taskCategory-new',
    component: TaskCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.taskCategory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'taskCategory-edit/:id',
    component: TaskCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.taskCategory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'taskCategory-delete/:id',
    component: TaskCategoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.taskCategory.home.title'
    },
    outlet: 'popup'
  }
];
