import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Estimates } from './estimates.model';
import { EstimatesService } from './estimates.service';


@Component({
    selector: 'jhi-estimates',
    templateUrl: './estimates.component.html'
})
export class EstimatesComponent implements OnInit {
    estimates: Estimates[];
    searchQuery: any;
   
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private estimatesService: EstimatesService,
        private alertService: AlertService,
        private eventManager: EventManager,
    ) {
        this.jhiLanguageService.setLocations(['estimates', 'tYPEENUM', 'sTATEENUM']);
    }

    /*setProjectId(projectid: string)
    {
         this.storageService.setProjectidDetails(projectid);
    }*/

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
