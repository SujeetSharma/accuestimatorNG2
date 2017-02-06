import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { FactorCategory } from './factor-category.model';
import { FactorCategoryPopupService } from './factor-category-popup.service';
import { FactorCategoryService } from './factor-category.service';

@Component({
    selector: 'jhi-factor-category-delete-dialog',
    templateUrl: './factor-category-delete-dialog.component.html'
})
export class FactorCategoryDeleteDialogComponent {

    factorCategory: FactorCategory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorCategoryService: FactorCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['factorCategory', 'tYPEENUM', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.factorCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'factorCategoryListModification', content: 'Deleted an factorCategory'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-factor-category-delete-popup',
    template: ''
})
export class FactorCategoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private factorCategoryPopupService: FactorCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.factorCategoryPopupService.open(FactorCategoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
