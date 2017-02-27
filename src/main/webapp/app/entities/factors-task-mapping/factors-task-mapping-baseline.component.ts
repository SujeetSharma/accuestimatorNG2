import { Component, OnInit, OnDestroy,Pipe, PipeTransform } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
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
import {StorageService} from '../shared/storage.service'
import { Subscription } from 'rxjs/Subscription';
import { FTEstimates } from './estimates.model';
import { FTEstimate } from './estimate.model';
import { Values } from './values.model';
import { CustomerModel, RegionModel } from './customer.model';

// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker


@Component({
    selector: 'jhi-factors-task-mapping-baseline',
    templateUrl: './factors-task-mapping-baseline.component.html'
   })
export class FactorsTaskMappingBaselineComponent implements OnInit {

    factorsTaskMapping: FactorsTaskMapping;
    authorities: any[];
    isSaving: boolean;
    factors: Factors[];
    tasks: Tasks[];
    subscription: Subscription;
    factorCat : string;
    taskCat : string;
    obj:any = Object;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private factorsTaskMappingService: FactorsTaskMappingService,
        private eventManager: EventManager,
        private router: Router,
        private factorsService: FactorsService,
        private tasksService: TasksService,
        private storageService: StorageService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['factorsTaskMapping', 'sTATEENUM']);
        this.subscription = this.storageService.getFTMappingDetails().subscribe(message => { 
            // console.log("inside factor - " + this.factor + " task - " + this.task);
            console.log("Message is - "+ message);
            this.factorCat = message.factorCat; 
            this.taskCat = message.taskCat;
            //this.subscription.unsubscribe;
        });
        //console.log("mapping is -constructor" + this.factorsTaskMapping.id);
         }
    
    loadAll(){
      
        this.factorsService.findByCat(this.factorCat).subscribe(
            (res: Factors[]) => {
                this.factors = res;
                console.log(res);
                
            }
        );
        this.tasksService.findByCat(this.taskCat).subscribe(
                    (res: Tasks[]) => {
                        this.tasks = res;
                        console.log(res);
                }
            );
        
    }

    ngOnInit() {
        this.loadAll();
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
       this.registerChangeInFactorsTaskMappings();
        //console.log("mapping is -nginit " + this.factorsTaskMapping.estimates.estimates);
        
    }

    registerChangeInFactorsTaskMappings() {
        this.eventManager.subscribe('factorsTaskBaselineModification', (response) => this.loadAll());
    }
    
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    save () {
        this.isSaving = true;
       // if (this.factorsTaskMapping.id !== undefined) {
          // this.factorsTaskMapping.estimates = JSON.stringify(this.estimates);
            this.factorsTaskMappingService.update(this.factorsTaskMapping)
                .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
       // } else {
       //     this.factorsTaskMappingService.create(this.factorsTaskMapping)
       //         .subscribe((res: any) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
       // }
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
    selector: 'jhi-factors-task-mapping-basline-popup',
    template: ''
})
export class FactorsTaskMappingBaselinePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private factorsTaskMappingPopupService: FactorsTaskMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            //if ( params['factor'] ) {
               this.modalRef = this.factorsTaskMappingPopupService.open(FactorsTaskMappingBaselineComponent, params['id']);
           // } else {
            //    this.modalRef = this.factorsTaskMappingPopupService.open(FactorsTaskMappingBaselineComponent);
            //}

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
