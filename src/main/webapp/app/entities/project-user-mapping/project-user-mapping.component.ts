import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ProjectUserMapping } from './project-user-mapping.model';
import { ProjectUserMappingService } from './project-user-mapping.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-project-user-mapping',
    templateUrl: './project-user-mapping.component.html'
})
export class ProjectUserMappingComponent implements OnInit {
    projectUserMappings: ProjectUserMapping[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectUserMappingService: ProjectUserMappingService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['projectUserMapping', 'sTATEENUM']);
    }

    loadAll() {
        this.projectUserMappingService.query().subscribe(
            (res: Response) => {
                this.projectUserMappings = res.json();
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
        this.registerChangeInProjectUserMappings();
    }

    trackId (index: number, item: ProjectUserMapping) {
        return item.id;
    }

    registerChangeInProjectUserMappings() {
        this.eventManager.subscribe('projectUserMappingListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
