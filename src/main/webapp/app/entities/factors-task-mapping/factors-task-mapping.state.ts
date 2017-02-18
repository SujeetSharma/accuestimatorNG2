import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FactorsTaskMappingComponent } from './factors-task-mapping.component';
import { FactorsTaskMappingDetailComponent } from './factors-task-mapping-detail.component';
import { FactorsTaskMappingPopupComponent } from './factors-task-mapping-dialog.component';
import {FactorsTaskMappingBaselinePopupComponent} from './factors-task-mapping-baseline.component'
import { FactorsTaskMappingDeletePopupComponent } from './factors-task-mapping-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FactorsTaskMappingResolvePagingParams implements Resolve<any> {

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

export const factorsTaskMappingRoute: Routes = [
  {
    path: 'factors-task-mapping',
    component: FactorsTaskMappingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorsTaskMapping.home.title'
    }
  }, {
    path: 'factors-task-mapping/:id',
    component: FactorsTaskMappingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorsTaskMapping.home.title'
    }
  }
];

export const factorsTaskMappingPopupRoute: Routes = [
  {
    path: 'factorsTaskMapping-new',
    component: FactorsTaskMappingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorsTaskMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factorsTaskMapping-edit/:id',
    component: FactorsTaskMappingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorsTaskMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factorsTaskMapping-delete/:id',
    component: FactorsTaskMappingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorsTaskMapping.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'factorsTaskMapping-baseline',
    component: FactorsTaskMappingBaselinePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'accuestimatorNg2App.factorsTaskMapping.home.title'
    },
    outlet: 'popup'
  }

];
