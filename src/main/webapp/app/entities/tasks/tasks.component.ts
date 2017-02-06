import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Tasks } from './tasks.model';
import { TasksService } from './tasks.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {StorageService} from '../shared/storage.service'
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'jhi-tasks',
    templateUrl: './tasks.component.html'
})
export class TasksComponent implements OnInit {
    tasks: Tasks[];
    currentAccount: any;
    searchQuery: any;
    subscription: Subscription;
    catid : String;
    catname : String;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private tasksService: TasksService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private storageService: StorageService
    ) {
        this.jhiLanguageService.setLocations(['tasks', 'tYPEENUM', 'sTATEENUM']);
        this.subscription = this.storageService.getTCatDetails().subscribe(message => { 
             console.log("Task component subscribe - name - " + this.catname + " id - " + this.catid);
            this.catname = message.name; 
            this.catid = message.id
            //this.subscription.unsubscribe;
        });
        console.log("Task component constructor - name - " + this.catname + " id - " + this.catid);
    }

    loadAll() {
        this.tasksService.query().subscribe(
            (res: Response) => {
                this.tasks = res.json();
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
        this.registerChangeInTasks();
          console.log('Task component ngOninit - cat in dialog pop is -' + this.catid);
    }

    trackId (index: number, item: Tasks) {
        return item.id;
    }

    registerChangeInTasks() {
        this.eventManager.subscribe('tasksListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
