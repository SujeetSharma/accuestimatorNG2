import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProjectsComponent } from './projects.component';
import { ProjectsDetailComponent } from './projects-detail.component';
import { ProjectsPopupComponent } from './projects-dialog.component';
import { ProjectsDeletePopupComponent } from './projects-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProjectsResolvePagingParams implements Resolve<any> {

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

export const projectsRoute: Routes = [
  {
    path: 'projects',
    component: ProjectsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projects.home.title'
    }
  }, {
    path: 'projects/:id',
    component: ProjectsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projects.home.title'
    }
  }
];

export const projectsPopupRoute: Routes = [
  {
    path: 'projects-new',
    component: ProjectsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projects.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'projects-edit/:id',
    component: ProjectsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projects.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'projects-delete/:id',
    component: ProjectsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projects.home.title'
    },
    outlet: 'popup'
  }
];
