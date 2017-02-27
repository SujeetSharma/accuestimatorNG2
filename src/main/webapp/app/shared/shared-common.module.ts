import { NgModule, Sanitizer } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { TranslateService } from 'ng2-translate';
import { AlertService } from 'ng-jhipster';
import {ValuesPipe} from './values.pipe';
import {
    AccuestimatorNg2SharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';


export function alertServiceProvider(sanitizer: Sanitizer, translateService: TranslateService) {
    // set below to true to make alerts look like toast
    let isToast = false;
    return new AlertService(sanitizer, isToast, translateService);
}

@NgModule({
    imports: [
        AccuestimatorNg2SharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        ValuesPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer, TranslateService]
        },
        Title
    ],
    exports: [
        AccuestimatorNg2SharedLibsModule,
        FindLanguageFromKeyPipe,
        ValuesPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class AccuestimatorNg2SharedCommonModule {}
