import { NgModule } from '@angular/core';
import { RouterModule, Routes, Resolve } from '@angular/router';

import { homeRoute } from '../home';
import { estimatorRoute } from '../estimator';
import { navbarRoute } from '../app.state';
import { errorRoute } from './';

let LAYOUT_ROUTES = [
    homeRoute,
    estimatorRoute,
    navbarRoute,
    ...errorRoute
];

@NgModule({
  imports: [
    RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
  ],
  exports: [
    RouterModule
  ]
})
export class LayoutRoutingModule {}
