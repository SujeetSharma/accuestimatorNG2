import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FactorCategoryComponent } from './factor-category.component';
import { FactorCategoryDetailComponent } from './factor-category-detail.component';
import { FactorCategoryPopupComponent } from './factor-category-dialog.component';
import { FactorCategoryDeletePopupComponent } from './factor-category-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FactorCategoryResolvePagingParams implements Resolve<any> {

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

export const factorCategoryRoute: Routes = [
  {
    path: 'factor-category',
    component: FactorCategoryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorCategory.home.title'
    }
  }, {
    path: 'factor-category/:id',
    component: FactorCategoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorCategory.home.title'
    }
  }
];

export const factorCategoryPopupRoute: Routes = [
  {
    path: 'factorCategory-new',
    component: FactorCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorCategory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factorCategory-edit/:id',
    component: FactorCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorCategory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factorCategory-delete/:id',
    component: FactorCategoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorCategory.home.title'
    },
    outlet: 'popup'
  }
];
