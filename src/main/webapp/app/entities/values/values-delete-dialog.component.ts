import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Values } from './values.model';
import { ValuesPopupService } from './values-popup.service';
import { ValuesService } from './values.service';

@Component({
    selector: 'jhi-values-delete-dialog',
    templateUrl: './values-delete-dialog.component.html'
})
export class ValuesDeleteDialogComponent {

    values: Values;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private valuesService: ValuesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['values', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.valuesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'valuesListModification', content: 'Deleted an values'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-values-delete-popup',
    template: ''
})
export class ValuesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private valuesPopupService: ValuesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.valuesPopupService.open(ValuesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
