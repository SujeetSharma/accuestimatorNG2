import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Estimates } from './estimates.model';
import { EstimatesService } from './estimates.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { Account} from '../../shared';
import {ProjectUserMappingService} from '../project-user-mapping/project-user-mapping.service';
import {ProjectUserMapping} from '../project-user-mapping/project-user-mapping.model';

@Component({
    selector: 'jhi-estimates',
    templateUrl: './estimates.component.html'
})
export class EstimatesComponent implements OnInit {
    estimates: Estimates[];
    currentAccount: Account;
    searchQuery: any;
    projectUserMappings: ProjectUserMapping[];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private estimatesService: EstimatesService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private projectUserMappingService: ProjectUserMappingService,
    ) {
        this.jhiLanguageService.setLocations(['estimates', 'tYPEENUM', 'sTATEENUM']);
    }

    loadAll() {
        this.estimatesService.query().subscribe(
            (res: Response) => {
                this.estimates = res.json();
                this.searchQuery = null;
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log("login info is -" + this.currentAccount.login);
            this.projectUserMappingService.findByUser(this.currentAccount.login).subscribe((res: ProjectUserMapping[]) => {
                this.projectUserMappings = res;
                console.log("login info is -" + this.projectUserMappings);
            });
        });
        this.registerChangeInEstimates();
    }

    trackId (index: number, item: Estimates) {
        return item.id;
    }

    registerChangeInEstimates() {
        this.eventManager.subscribe('estimatesListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
