import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Factors } from './factors.model';
import { FactorsService } from './factors.service';

@Component({
    selector: 'jhi-factors-detail',
    templateUrl: './factors-detail.component.html'
})
export class FactorsDetailComponent implements OnInit, OnDestroy {

    factors: Factors;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorsService: FactorsService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['factors', 'tYPEENUM', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.factorsService.find(id).subscribe(factors => {
            this.factors = factors;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
