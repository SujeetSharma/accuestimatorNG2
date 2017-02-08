import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplatePopupService } from './template-popup.service';
import { TemplateService } from './template.service';

import { FactorsTaskMappingService } from '../factors-task-mapping/factors-task-mapping.service';
import { FactorsTaskMapping } from '../factors-task-mapping/factors-task-mapping.model';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-template-dialog',
    templateUrl: './template-dialog.component.html'
})
export class TemplateDialogComponent implements OnInit {

    template: Template;
    authorities: any[];
    isSaving: boolean;
    factorsTaskMapping: FactorsTaskMapping;
    values:Array<any>= [];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private templateService: TemplateService,
        private eventManager: EventManager,
        private router: Router,
         private factorsTaskMappingService: FactorsTaskMappingService,
    ) {
        this.jhiLanguageService.setLocations(['template', 'sTATEENUM']);
    }

    checkedFactorsTasks(value, event) {
        console.log(value + "---" + event.target.checked);
        if (event.target.checked === true) {
            this.values.push(value);
        }
        else if (event.target.checked === false) {
            let indexx = this.values.indexOf(value);
            this.values.splice(indexx,1)
        }
        console.log(this.values)
    }


    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
           this.factorsTaskMappingService.query().subscribe(
            (res: Response) => {
                this.factorsTaskMapping = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    save () {
        this.isSaving = true;
         this.template.factorTaskId = this.values;
        if (this.template.id !== undefined) {
            this.templateService.update(this.template)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.templateService.create(this.template)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'templateListModification', content: 'OK'});
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
    selector: 'jhi-template-popup',
    template: ''
})
export class TemplatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private templatePopupService: TemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.templatePopupService.open(TemplateDialogComponent, params['id']);
            } else {
                this.modalRef = this.templatePopupService.open(TemplateDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
