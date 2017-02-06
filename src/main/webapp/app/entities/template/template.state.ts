import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TemplateComponent } from './template.component';
import { TemplateDetailComponent } from './template-detail.component';
import { TemplatePopupComponent } from './template-dialog.component';
import { TemplateDeletePopupComponent } from './template-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TemplateResolvePagingParams implements Resolve<any> {

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

export const templateRoute: Routes = [
  {
    path: 'template',
    component: TemplateComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.template.home.title'
    }
  }, {
    path: 'template/:id',
    component: TemplateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.template.home.title'
    }
  }
];

export const templatePopupRoute: Routes = [
  {
    path: 'template-new',
    component: TemplatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.template.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'template-edit/:id',
    component: TemplatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.template.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'template-delete/:id',
    component: TemplateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.template.home.title'
    },
    outlet: 'popup'
  }
];
