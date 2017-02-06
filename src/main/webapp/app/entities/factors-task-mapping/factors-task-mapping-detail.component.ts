import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { FactorsTaskMapping } from './factors-task-mapping.model';
import { FactorsTaskMappingService } from './factors-task-mapping.service';

@Component({
    selector: 'jhi-factors-task-mapping-detail',
    templateUrl: './factors-task-mapping-detail.component.html'
})
export class FactorsTaskMappingDetailComponent implements OnInit, OnDestroy {

    factorsTaskMapping: FactorsTaskMapping;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorsTaskMappingService: FactorsTaskMappingService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['factorsTaskMapping', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.factorsTaskMappingService.find(id).subscribe(factorsTaskMapping => {
            this.factorsTaskMapping = factorsTaskMapping;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
