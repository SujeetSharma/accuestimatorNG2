import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Template } from './template.model';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-detail',
    templateUrl: './template-detail.component.html'
})
export class TemplateDetailComponent implements OnInit, OnDestroy {

    template: Template;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private templateService: TemplateService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['template', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.templateService.find(id).subscribe(template => {
            this.template = template;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
