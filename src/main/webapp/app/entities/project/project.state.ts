import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProjectComponent } from './project.component';
import { ProjectDetailComponent } from './project-detail.component';
import { ProjectPopupComponent } from './project-dialog.component';
import { ProjectDeletePopupComponent } from './project-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProjectResolvePagingParams implements Resolve<any> {

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

export const projectRoute: Routes = [
  {
    path: 'project',
    component: ProjectComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.project.home.title'
    }
  }, {
    path: 'project/:id',
    component: ProjectDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.project.home.title'
    }
  }
];

export const projectPopupRoute: Routes = [
  {
    path: 'project-new',
    component: ProjectPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.project.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'project-edit/:id',
    component: ProjectPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.project.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'project-delete/:id',
    component: ProjectDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.project.home.title'
    },
    outlet: 'popup'
  }
];
