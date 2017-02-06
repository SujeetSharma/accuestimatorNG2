import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Tasks } from './tasks.model';
import { TasksPopupService } from './tasks-popup.service';
import { TasksService } from './tasks.service';
import {StorageService} from '../shared/storage.service'
import { Subscription } from 'rxjs/Subscription';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-tasks-dialog',
    templateUrl: './tasks-dialog.component.html'
})
export class TasksDialogComponent implements OnInit {

    tasks: Tasks;
    authorities: any[];
    isSaving: boolean;
    private sub;
    private catid;
    subscription: Subscription;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private tasksService: TasksService,
        private eventManager: EventManager,
        private router: Router,
         private storageService: StorageService
    ) {
        this.jhiLanguageService.setLocations(['tasks', 'tYPEENUM', 'sTATEENUM']);
        this.subscription = this.storageService.getTCatDetails().subscribe(message => { 
            this.catid = message.id; 
            console.log('Task dialog subscribe - cat in dialog pop is -' + this.catid);
        });
         console.log("Task dialog constructor - id - " + this.catid);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        console.log('Task dialog ngOninit - cat in dialog pop is -' + this.catid);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    save () {
        this.isSaving = true;
        if (this.tasks.id !== undefined) {
            this.tasksService.update(this.tasks)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.tasks.category = this.catid;
            this.tasksService.create(this.tasks)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'tasksListModification', content: 'OK'});
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
    selector: 'jhi-tasks-popup',
    template: ''
})
export class TasksPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tasksPopupService: TasksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.tasksPopupService.open(TasksDialogComponent, params['id']);
            } else {
                this.modalRef = this.tasksPopupService.open(TasksDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
