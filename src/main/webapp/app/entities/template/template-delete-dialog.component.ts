import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplatePopupService } from './template-popup.service';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-delete-dialog',
    templateUrl: './template-delete-dialog.component.html'
})
export class TemplateDeleteDialogComponent {

    template: Template;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private templateService: TemplateService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['template', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.templateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'templateListModification', content: 'Deleted an template'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-template-delete-popup',
    template: ''
})
export class TemplateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private templatePopupService: TemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.templatePopupService.open(TemplateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
