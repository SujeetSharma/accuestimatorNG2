import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { FactorsTaskMapping } from './factors-task-mapping.model';
import { FactorsTaskMappingPopupService } from './factors-task-mapping-popup.service';
import { FactorsTaskMappingService } from './factors-task-mapping.service';

@Component({
    selector: 'jhi-factors-task-mapping-delete-dialog',
    templateUrl: './factors-task-mapping-delete-dialog.component.html'
})
export class FactorsTaskMappingDeleteDialogComponent {

    factorsTaskMapping: FactorsTaskMapping;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorsTaskMappingService: FactorsTaskMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['factorsTaskMapping', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.factorsTaskMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'factorsTaskMappingListModification', content: 'Deleted an factorsTaskMapping'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-factors-task-mapping-delete-popup',
    template: ''
})
export class FactorsTaskMappingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private factorsTaskMappingPopupService: FactorsTaskMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.factorsTaskMappingPopupService.open(FactorsTaskMappingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
