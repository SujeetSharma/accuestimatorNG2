import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Projects } from './projects.model';
import { ProjectsService } from './projects.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-projects',
    templateUrl: './projects.component.html'
})
export class ProjectsComponent implements OnInit {
    projects: Projects[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectsService: ProjectsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['projects', 'tYPEENUM', 'sTATEENUM']);
    }

    loadAll() {
        this.projectsService.query().subscribe(
            (res: Response) => {
                this.projects = res.json();
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
        this.registerChangeInProjects();
    }

    trackId (index: number, item: Projects) {
        return item.id;
    }

    registerChangeInProjects() {
        this.eventManager.subscribe('projectsListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
