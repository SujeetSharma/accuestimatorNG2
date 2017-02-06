import { Component, OnInit, OnDestroy,EventEmitter,Input,Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Factors } from './factors.model';
import { FactorsPopupService } from './factors-popup.service';
import { FactorsService } from './factors.service';
import {StorageService} from '../shared/storage.service'
import { Subscription } from 'rxjs/Subscription';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-factors-dialog',
    templateUrl: './factors-dialog.component.html'
})
export class FactorsDialogComponent implements OnInit {
    factors: Factors;
    authorities: any[];
    isSaving: boolean;
    private sub;
    private catid;
    subscription: Subscription;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private factorsService: FactorsService,
        private eventManager: EventManager,
        private route: ActivatedRoute,
        private router: Router,
        private storageService: StorageService
    ) {
        this.jhiLanguageService.setLocations(['factors', 'tYPEENUM', 'sTATEENUM']);
        this.subscription = this.storageService.getFCatDetails().subscribe(message => { this.catid = message.id; });
    }
   
    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        /* this.sub = this.route.params.subscribe(params => {
            this.catid = params['id'];// (+) converts string 'id' to a number
            console.log('cat in dialog pop is -' + this.catid );
        });*/
         console.log('cat in dialog pop is -' + this.catid);
    }

    ngOnDestroy() {
       // this.sub.unsubscribe();
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    save () {
        this.isSaving = true;
        if (this.factors.id !== undefined) {
            this.factorsService.update(this.factors)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.factors.category = this.catid;
            this.factorsService.create(this.factors)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'factorsListModification', content: 'OK'});
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
    selector: 'jhi-factors-popup',
    template: ''
})
export class FactorsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;
   
    constructor (   
        private route: ActivatedRoute,
        private factorsPopupService: FactorsPopupService,
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.factorsPopupService.open(FactorsDialogComponent, params['id']);
            } else {
                this.modalRef = this.factorsPopupService.open(FactorsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
