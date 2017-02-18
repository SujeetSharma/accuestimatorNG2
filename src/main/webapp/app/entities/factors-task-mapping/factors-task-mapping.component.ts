import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { FactorsTaskMapping } from './factors-task-mapping.model';
import { FactorsTaskMappingService } from './factors-task-mapping.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {StorageService} from '../shared/storage.service';

@Component({
    selector: 'jhi-factors-task-mapping',
    templateUrl: './factors-task-mapping.component.html'
})
export class FactorsTaskMappingComponent implements OnInit {
    factorsTaskMappings: FactorsTaskMapping[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorsTaskMappingService: FactorsTaskMappingService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private storageService : StorageService
    ) {
        this.jhiLanguageService.setLocations(['factorsTaskMapping', 'sTATEENUM', 'eSTTYPEENUM']);
    }

    loadAll() {
        this.factorsTaskMappingService.query().subscribe(
            (res: Response) => {
                this.factorsTaskMappings = res.json();
                this.searchQuery = null;
            },
            (res: Response) => this.onError(res.json())
        );
    }

    setFTMappingInfo(factorCategory: string, taskCategory: string)
    {
         this.storageService.setFTMappingDetails(factorCategory, taskCategory);
    }
    
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFactorsTaskMappings();
    }

    trackId (index: number, item: FactorsTaskMapping) {
        return item.id;
    }

    registerChangeInFactorsTaskMappings() {
        this.eventManager.subscribe('factorsTaskMappingListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
