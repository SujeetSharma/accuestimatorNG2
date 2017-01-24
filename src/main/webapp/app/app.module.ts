import './vendor.ts';

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { AccuestimatorNg2SharedModule, UserRouteAccessService } from './shared';
import { AccuestimatorNg2AdminModule } from './admin/admin.module';
import { AccuestimatorNg2EntityModule } from './entities/entity.module';
import { AccuestimatorNg2AccountModule } from './account/account.module';

import { LayoutRoutingModule } from './layouts';
import { HomeComponent } from './home';
import { EstimatorComponent } from './estimator';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi'}),
        AccuestimatorNg2SharedModule,
        AccuestimatorNg2AdminModule,
        AccuestimatorNg2EntityModule,
        AccuestimatorNg2AccountModule
    ],
    declarations: [
        JhiMainComponent,
        HomeComponent,
        EstimatorComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        { provide: Window, useValue: window },
        { provide: Document, useValue: document },
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class AccuestimatorNg2AppModule {}
