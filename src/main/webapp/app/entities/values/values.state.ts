import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ValuesComponent } from './values.component';
import { ValuesDetailComponent } from './values-detail.component';
import { ValuesPopupComponent } from './values-dialog.component';
import { ValuesDeletePopupComponent } from './values-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ValuesResolvePagingParams implements Resolve<any> {

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

export const valuesRoute: Routes = [
  {
    path: 'values',
    component: ValuesComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.values.home.title'
    }
  }, {
    path: 'values/:id',
    component: ValuesDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.values.home.title'
    }
  }
];

export const valuesPopupRoute: Routes = [
  {
    path: 'values-new',
    component: ValuesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.values.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'values-edit/:id',
    component: ValuesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.values.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'values-delete/:id',
    component: ValuesDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.values.home.title'
    },
    outlet: 'popup'
  }
];
