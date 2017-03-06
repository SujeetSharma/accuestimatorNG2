import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Estimates } from './estimates.model';
import { EstimatesService } from './estimates.service';

@Component({
    selector: 'jhi-estimates-detail',
    templateUrl: './estimates-detail.component.html'
})
export class EstimatesDetailComponent implements OnInit, OnDestroy {

    estimates: Estimates;
    private subscription: any;
    
    tableData: TableData[] = [
        new TableData('Canada','Ottawa',1),
        new TableData('USA','Washington DC',2),
        new TableData('Australia','Canberra',3),
        new TableData('UK','London',4)
        ];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private estimatesService: EstimatesService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['estimates', 'tYPEENUM', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.estimatesService.find(id).subscribe(estimates => {
            this.estimates = estimates;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}

export class TableData{
    constructor(
        public country: string,
        public capital: string, public id: number){ }
}