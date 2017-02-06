import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ProjectTemplateMapping } from './project-template-mapping.model';
import { ProjectTemplateMappingService } from './project-template-mapping.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-project-template-mapping',
    templateUrl: './project-template-mapping.component.html'
})
export class ProjectTemplateMappingComponent implements OnInit {
    projectTemplateMappings: ProjectTemplateMapping[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectTemplateMappingService: ProjectTemplateMappingService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['projectTemplateMapping', 'sTATEENUM']);
    }

    loadAll() {
        this.projectTemplateMappingService.query().subscribe(
            (res: Response) => {
                this.projectTemplateMappings = res.json();
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
        this.registerChangeInProjectTemplateMappings();
    }

    trackId (index: number, item: ProjectTemplateMapping) {
        return item.id;
    }

    registerChangeInProjectTemplateMappings() {
        this.eventManager.subscribe('projectTemplateMappingListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
