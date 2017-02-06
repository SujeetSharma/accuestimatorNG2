import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplateService } from './template.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-template',
    templateUrl: './template.component.html'
})
export class TemplateComponent implements OnInit {
    templates: Template[];
    currentAccount: any;
    searchQuery: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private templateService: TemplateService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['template', 'sTATEENUM']);
    }

    loadAll() {
        this.templateService.query().subscribe(
            (res: Response) => {
                this.templates = res.json();
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
        this.registerChangeInTemplates();
    }

    trackId (index: number, item: Template) {
        return item.id;
    }

    registerChangeInTemplates() {
        this.eventManager.subscribe('templateListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
