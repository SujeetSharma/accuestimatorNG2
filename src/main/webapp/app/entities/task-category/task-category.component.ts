import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { TaskCategory } from './task-category.model';
import { TaskCategoryService } from './task-category.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {StorageService} from '../shared/storage.service'

@Component({
    selector: 'jhi-task-category',
    templateUrl: './task-category.component.html'
})
export class TaskCategoryComponent implements OnInit {
    taskCategories: TaskCategory[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskCategoryService: TaskCategoryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private storageService : StorageService
    ) {
        this.jhiLanguageService.setLocations(['taskCategory', 'tYPEENUM', 'sTATEENUM']);
    }

    setInfo(name: string, id:string) {
        this.storageService.setTCatDetails(name, id);
        console.log("Settinf catname & id" + name + " - " + id);
    }
    loadAll() {
        this.taskCategoryService.query().subscribe(
            (res: Response) => {
                this.taskCategories = res.json();
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
        this.registerChangeInTaskCategories();
    }

    trackId (index: number, item: TaskCategory) {
        return item.id;
    }

    registerChangeInTaskCategories() {
        this.eventManager.subscribe('taskCategoryListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
