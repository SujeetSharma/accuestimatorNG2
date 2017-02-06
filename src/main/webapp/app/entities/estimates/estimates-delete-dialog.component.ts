import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Estimates } from './estimates.model';
import { EstimatesPopupService } from './estimates-popup.service';
import { EstimatesService } from './estimates.service';

@Component({
    selector: 'jhi-estimates-delete-dialog',
    templateUrl: './estimates-delete-dialog.component.html'
})
export class EstimatesDeleteDialogComponent {

    estimates: Estimates;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private estimatesService: EstimatesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['estimates', 'tYPEENUM', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.estimatesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'estimatesListModification', content: 'Deleted an estimates'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estimates-delete-popup',
    template: ''
})
export class EstimatesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private estimatesPopupService: EstimatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.estimatesPopupService.open(EstimatesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
