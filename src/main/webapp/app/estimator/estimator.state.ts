import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { EstimatorComponent } from './';

export const estimatorRoute: Route = {
  path: 'estimator',
  component: EstimatorComponent,
  data: {
    authorities: [],
    pageTitle: 'estimator.title'
  },
  canActivate: [UserRouteAccessService]
};
