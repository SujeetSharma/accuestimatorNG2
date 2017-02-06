import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Factors } from './factors.model';
import { FactorsPopupService } from './factors-popup.service';
import { FactorsService } from './factors.service';

@Component({
    selector: 'jhi-factors-delete-dialog',
    templateUrl: './factors-delete-dialog.component.html'
})
export class FactorsDeleteDialogComponent {

    factors: Factors;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorsService: FactorsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['factors', 'tYPEENUM', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.factorsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'factorsListModification', content: 'Deleted an factors'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-factors-delete-popup',
    template: ''
})
export class FactorsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private factorsPopupService: FactorsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.factorsPopupService.open(FactorsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
