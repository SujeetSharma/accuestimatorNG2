import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Values } from './values.model';
import { ValuesService } from './values.service';

@Component({
    selector: 'jhi-values-detail',
    templateUrl: './values-detail.component.html'
})
export class ValuesDetailComponent implements OnInit, OnDestroy {

    values: Values;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private valuesService: ValuesService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['values', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.valuesService.find(id).subscribe(values => {
            this.values = values;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
