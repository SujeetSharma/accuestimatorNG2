import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProjectTemplateMappingComponent } from './project-template-mapping.component';
import { ProjectTemplateMappingDetailComponent } from './project-template-mapping-detail.component';
import { ProjectTemplateMappingPopupComponent } from './project-template-mapping-dialog.component';
import { ProjectTemplateMappingDeletePopupComponent } from './project-template-mapping-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProjectTemplateMappingResolvePagingParams implements Resolve<any> {

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

export const projectTemplateMappingRoute: Routes = [
  {
    path: 'project-template-mapping',
    component: ProjectTemplateMappingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectTemplateMapping.home.title'
    }
  }, {
    path: 'project-template-mapping/:id',
    component: ProjectTemplateMappingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectTemplateMapping.home.title'
    }
  }
];

export const projectTemplateMappingPopupRoute: Routes = [
  {
    path: 'projectTemplateMapping-new',
    component: ProjectTemplateMappingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectTemplateMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'projectTemplateMapping-edit/:id',
    component: ProjectTemplateMappingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectTemplateMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'projectTemplateMapping-delete/:id',
    component: ProjectTemplateMappingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.projectTemplateMapping.home.title'
    },
    outlet: 'popup'
  }
];
