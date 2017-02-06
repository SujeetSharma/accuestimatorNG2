import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FactorsComponent } from './factors.component';
import { FactorsDetailComponent } from './factors-detail.component';
import { FactorsPopupComponent } from './factors-dialog.component';
import { FactorsDeletePopupComponent } from './factors-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FactorsResolvePagingParams implements Resolve<any> {

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

export const factorsRoute: Routes = [
 /* {
    path: 'factors/:cat/:id',
    component: FactorsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factors.home.title'
    }
  },*/
  {
    path: 'factors',
    component: FactorsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factors.home.title'
    }
  }, {
    path: 'factors/:id',
    component: FactorsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factors.home.title'
    }
  }
];

export const factorsPopupRoute: Routes = [
  {
    path: 'factors-new/:catid',
    component: FactorsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factors.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factors-edit/:id',
    component: FactorsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factors.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factors-delete/:id',
    component: FactorsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factors.home.title'
    },
    outlet: 'popup'
  }
];
