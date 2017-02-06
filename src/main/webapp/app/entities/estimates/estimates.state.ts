import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EstimatesComponent } from './estimates.component';
import { EstimatesDetailComponent } from './estimates-detail.component';
import { EstimatesPopupComponent } from './estimates-dialog.component';
import { EstimatesDeletePopupComponent } from './estimates-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class EstimatesResolvePagingParams implements Resolve<any> {

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

export const estimatesRoute: Routes = [
  {
    path: 'estimates',
    component: EstimatesComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.estimates.home.title'
    }
  }, {
    path: 'estimates/:id',
    component: EstimatesDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.estimates.home.title'
    }
  }
];

export const estimatesPopupRoute: Routes = [
  {
    path: 'estimates-new',
    component: EstimatesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.estimates.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'estimates-edit/:id',
    component: EstimatesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.estimates.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'estimates-delete/:id',
    component: EstimatesDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.estimates.home.title'
    },
    outlet: 'popup'
  }
];
