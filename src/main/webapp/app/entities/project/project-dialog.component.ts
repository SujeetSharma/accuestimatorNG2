import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Project } from './project.model';
import { ProjectPopupService } from './project-popup.service';
import { ProjectService } from './project.service';
import { TemplateService } from '../template/template.service';
import { Template } from '../template/template.model';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-project-dialog',
    templateUrl: './project-dialog.component.html'
})
export class ProjectDialogComponent implements OnInit {

    project: Project;
    authorities: any[];
    isSaving: boolean;
    templates: Template[];
    values:Array<any>= [];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private projectService: ProjectService,
        private eventManager: EventManager,
        private templateService : TemplateService,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['project', 'sTATEENUM']);
    }

    checkedTemplates(value, event) {
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
        this.templateService.query().subscribe(
            (res: Response) => {
                this.templates = res.json();
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
        this.project.templateId = this.values;
        if (this.project.id !== undefined) {
            this.projectService.update(this.project)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.projectService.create(this.project)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'projectListModification', content: 'OK'});
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
    selector: 'jhi-project-popup',
    template: ''
})
export class ProjectPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private projectPopupService: ProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.projectPopupService.open(ProjectDialogComponent, params['id']);
            } else {
                this.modalRef = this.projectPopupService.open(ProjectDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
