import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { FactorCategory } from './factor-category.model';
import { FactorCategoryService } from './factor-category.service';

@Component({
    selector: 'jhi-factor-category-detail',
    templateUrl: './factor-category-detail.component.html'
})
export class FactorCategoryDetailComponent implements OnInit, OnDestroy {

    factorCategory: FactorCategory;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorCategoryService: FactorCategoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['factorCategory', 'tYPEENUM', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.factorCategoryService.find(id).subscribe(factorCategory => {
            this.factorCategory = factorCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
