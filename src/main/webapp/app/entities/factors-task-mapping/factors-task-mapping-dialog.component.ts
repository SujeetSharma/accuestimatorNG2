import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { FactorsTaskMapping } from './factors-task-mapping.model';
import { FactorsTaskMappingPopupService } from './factors-task-mapping-popup.service';
import { FactorsTaskMappingService } from './factors-task-mapping.service';
import {FactorCategoryService} from '../factor-category/factor-category.service';
import {FactorsService} from '../factors/factors.service';
import {TaskCategoryService} from '../task-category/task-category.service';
import {TasksService} from '../tasks/tasks.service';
import {FactorCategory } from '../factor-category/factor-category.model';
import {TaskCategory } from '../task-category/task-category.model';
import {Factors } from '../factors/factors.model';
import {Tasks } from '../tasks/tasks.model';
import { FTEstimates } from './estimates.model';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-factors-task-mapping-dialog',
    templateUrl: './factors-task-mapping-dialog.component.html'
})
export class FactorsTaskMappingDialogComponent implements OnInit {

    factorsTaskMapping: FactorsTaskMapping;
    authorities: any[];
    isSaving: boolean;
    factorCategory: FactorCategory
    taskCategory: TaskCategoryService;
    factors: Factors[];
    tasks: Tasks[];
    factorsByCat: Factors[];
    tasksByCat: Tasks[];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private factorsTaskMappingService: FactorsTaskMappingService,
        private eventManager: EventManager,
        private router: Router,
        private factorCategoryService: FactorCategoryService,
        private taskCategoryService: TaskCategoryService,
        private factorsService: FactorsService,
        private tasksService: TasksService
    ) {
        this.jhiLanguageService.setLocations(['factorsTaskMapping', 'sTATEENUM']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.factorCategoryService.query().subscribe(
            (res: Response) => {
                this.factorCategory = res.json();
        });
        this.taskCategoryService.query().subscribe(
            (res: Response) => {
                this.taskCategory = res.json();
        });
        this.factorsService.query().subscribe(
            (res: Response) => {
                this.factors = res.json();
        });
        this.tasksService.query().subscribe(
            (res: Response) => {
                this.tasks = res.json();
        });
    }
    
    onFactorCatSelect(countryid) {
       // this.states = this._dataService.getStates().filter((item)=> item.countryid == countryid);
        this.factorsService.query().subscribe(
            (res: Response) => {
                this.factors = res.json();
        });
    }
    
    onTaskCatSelect(countryid) {
        // this.states = this._dataService.getStates().filter((item)=> item.countryid == countryid);
         this.tasksService.query().subscribe(
            (res: Response) => {
                this.tasks = res.json();
        });
    }
   
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }


    save () {
        this.isSaving = true;
       this.factorsService.findByCat(this.factorsTaskMapping.factorCategory).subscribe(
            (res: Factors[]) => {
                this.factorsByCat = res;
                console.log(res);
                this.tasksService.findByCat(this.factorsTaskMapping.taskCategory).subscribe(
                    (res: Tasks[]) => {
                        this.tasksByCat  = res;
                        console.log(res);
                        this.factorsTaskMappingService.getEstimates(this.factorsByCat, this.tasksByCat).subscribe(
                            (res: FTEstimates) => {
                                this.factorsTaskMapping.estimates = res;
                                if (this.factorsTaskMapping.id !== undefined) {
                                    this.factorsTaskMappingService.update(this.factorsTaskMapping)
                                        .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
                                } else {
                                    this.factorsTaskMappingService.create(this.factorsTaskMapping)
                                        .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
                                }

                            }
                        );
                    }
                );
            }
       );
       

    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'factorsTaskMappingListModification', content: 'OK'});
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
    selector: 'jhi-factors-task-mapping-popup',
    template: ''
})
export class FactorsTaskMappingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private factorsTaskMappingPopupService: FactorsTaskMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.factorsTaskMappingPopupService.open(FactorsTaskMappingDialogComponent, params['id']);
            } else {
                this.modalRef = this.factorsTaskMappingPopupService.open(FactorsTaskMappingDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
