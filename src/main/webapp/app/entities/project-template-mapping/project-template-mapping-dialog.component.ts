import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProjectTemplateMapping } from './project-template-mapping.model';
import { ProjectTemplateMappingPopupService } from './project-template-mapping-popup.service';
import { ProjectTemplateMappingService } from './project-template-mapping.service';


// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-project-template-mapping-dialog',
    templateUrl: './project-template-mapping-dialog.component.html'
})
export class ProjectTemplateMappingDialogComponent implements OnInit {

    projectTemplateMapping: ProjectTemplateMapping;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private projectTemplateMappingService: ProjectTemplateMappingService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['projectTemplateMapping', 'sTATEENUM']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    save () {
        this.isSaving = true;
        if (this.projectTemplateMapping.id !== undefined) {
            this.projectTemplateMappingService.update(this.projectTemplateMapping)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.projectTemplateMappingService.create(this.projectTemplateMapping)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'projectTemplateMappingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-project-template-mapping-popup',
    template: ''
})
export class ProjectTemplateMappingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private projectTemplateMappingPopupService: ProjectTemplateMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.projectTemplateMappingPopupService.open(ProjectTemplateMappingDialogComponent, params['id']);
            } else {
                this.modalRef = this.projectTemplateMappingPopupService.open(ProjectTemplateMappingDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
