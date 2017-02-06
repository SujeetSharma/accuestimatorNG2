import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { FactorCategory } from './factor-category.model';
import { FactorCategoryService } from './factor-category.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {StorageService} from '../shared/storage.service'

@Component({
    selector: 'jhi-factor-category',
    templateUrl: './factor-category.component.html'
})
export class FactorCategoryComponent implements OnInit {
    factorCategories: FactorCategory[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorCategoryService: FactorCategoryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private storageService : StorageService
    ) {
        this.jhiLanguageService.setLocations(['factorCategory', 'tYPEENUM', 'sTATEENUM']);
    }

    setInfo(name: string, id:string) {
        this.storageService.setFCatDetails(name, id);
    }
    
    loadAll() {
        this.factorCategoryService.query().subscribe(
            (res: Response) => {
                this.factorCategories = res.json();
                this.searchQuery = null;
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFactorCategories();
    }

    trackId (index: number, item: FactorCategory) {
        return item.id;
    }

    registerChangeInFactorCategories() {
        this.eventManager.subscribe('factorCategoryListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
