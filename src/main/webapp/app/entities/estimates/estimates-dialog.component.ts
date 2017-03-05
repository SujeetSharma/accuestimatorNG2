import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Estimates } from './estimates.model';
import { EstimatesPopupService } from './estimates-popup.service';
import { EstimatesService } from './estimates.service';

import {StorageService} from '../shared/storage.service'
import { Subscription } from 'rxjs/Subscription';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-estimates-dialog',
    templateUrl: './estimates-dialog.component.html'
})
export class EstimatesDialogComponent implements OnInit {

    estimates: Estimates;
    authorities: any[];
    isSaving: boolean;
    subscription: Subscription;
    projectid: string;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private estimatesService: EstimatesService,
        private eventManager: EventManager,
        private router: Router,
        private storageService: StorageService,
    ) {
        this.jhiLanguageService.setLocations(['estimates', 'tYPEENUM', 'sTATEENUM']);
        this.subscription = this.storageService.getProjectidDetails().subscribe(message => { 
            console.log("Project id Message is - "+ message.projectid);
            this.projectid = message.projectid; 
        });
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
        this.estimates.projectId = this.projectid;
        if (this.estimates.id !== undefined) {
            this.estimatesService.update(this.estimates)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.estimatesService.create(this.estimates)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'estimatesListModification', content: 'OK'});
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
    selector: 'jhi-estimates-popup',
    template: ''
})
export class EstimatesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private estimatesPopupService: EstimatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.estimatesPopupService.open(EstimatesDialogComponent, params['id']);
            } else {
                this.modalRef = this.estimatesPopupService.open(EstimatesDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
