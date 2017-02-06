import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Factors } from './factors.model';
import { FactorsService } from './factors.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {StorageService} from '../shared/storage.service'
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'jhi-factors',
    templateUrl: './factors.component.html'
})
export class FactorsComponent implements OnInit {
    factors: Factors[];
    currentAccount: any;
    searchQuery: any;
    subscription: Subscription;
    catid : String;
    catname : String;
   
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private factorsService: FactorsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private route: ActivatedRoute,
        private storageService: StorageService
    ) {
        this.jhiLanguageService.setLocations(['factors', 'tYPEENUM', 'sTATEENUM']);
        this.subscription = this.storageService.getFCatDetails().subscribe(message => { 
             console.log("inside name - " + this.catname + " id - " + this.catid);
            console.log("Message is - "+ message);
            this.catname = message.name; 
            this.catid = message.id
            //this.subscription.unsubscribe;
        });
        console.log("name - " + this.catname + " id - " + this.catid);
    }

    loadAll() {
        this.factorsService.query().subscribe(
            (res: Response) => {
                this.factors = res.json();
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
        this.registerChangeInFactors();
        /*this.sub = this.route.params.subscribe(params => {
         this.catid = params['id']; 
         this.cat = params['cat'];// (+) converts string 'id' to a number
         console.log('cat is -' + this.cat + 'id is -' + this.catid);
        });*/
    }

    trackId (index: number, item: Factors) {
        return item.id;
    }

    registerChangeInFactors() {
        this.eventManager.subscribe('factorsListModification', (response) => this.loadAll());
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

     private ngOnDestroy() {
        //this.sub.unsubscribe();
    }
}
