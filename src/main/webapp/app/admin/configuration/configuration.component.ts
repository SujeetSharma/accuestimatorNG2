import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { JhiConfigurationService } from './configuration.service';

@Component({
    selector: 'jhi-configuration',
    templateUrl: './configuration.component.html'
})
export class JhiConfigurationComponent {
    allConfiguration: any = null;
    configuration: any = null;
    configKeys: any[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private configurationService: JhiConfigurationService
    ) {
        this.configKeys = [];
        this.filter = '';
        this.orderProp = 'prefix';
        this.reverse = false;
        this.jhiLanguageService.setLocations(['configuration']);
    }

    keys(dict): Array<string> {
        return Object.keys(dict);
    }

    ngOnInit() {
        this.configurationService.get().subscribe((configuration) => {
            this.configuration = configuration;

            for (let config of configuration) {
                this.configKeys.push(Object.keys(config.properties));
            }
        });

        this.configurationService.getEnv().subscribe((configuration) => {
            this.allConfiguration = configuration;
        });
    }
}
