import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProjectUserMappingComponent } from './project-user-mapping.component';
import { ProjectUserMappingDetailComponent } from './project-user-mapping-detail.component';
import { ProjectUserMappingPopupComponent } from './project-user-mapping-dialog.component';
import { ProjectUserMappingDeletePopupComponent } from './project-user-mapping-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProjectUserMappingResolvePagingParams implements Resolve<any> {

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

export const projectUserMappingRoute: Routes = [
  {
    path: 'project-user-mapping',
    component: ProjectUserMappingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectUserMapping.home.title'
    }
  }, {
    path: 'project-user-mapping/:id',
    component: ProjectUserMappingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectUserMapping.home.title'
    }
  }
];

export const projectUserMappingPopupRoute: Routes = [
  {
    path: 'projectUserMapping-new',
    component: ProjectUserMappingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectUserMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'projectUserMapping-edit/:id',
    component: ProjectUserMappingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectUserMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'projectUserMapping-delete/:id',
    component: ProjectUserMappingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectUserMapping.home.title'
    },
    outlet: 'popup'
  }
];
