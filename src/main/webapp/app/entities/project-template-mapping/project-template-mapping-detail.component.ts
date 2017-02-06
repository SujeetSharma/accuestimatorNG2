import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ProjectTemplateMapping } from './project-template-mapping.model';
import { ProjectTemplateMappingService } from './project-template-mapping.service';

@Component({
    selector: 'jhi-project-template-mapping-detail',
    templateUrl: './project-template-mapping-detail.component.html'
})
export class ProjectTemplateMappingDetailComponent implements OnInit, OnDestroy {

    projectTemplateMapping: ProjectTemplateMapping;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectTemplateMappingService: ProjectTemplateMappingService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['projectTemplateMapping', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.projectTemplateMappingService.find(id).subscribe(projectTemplateMapping => {
            this.projectTemplateMapping = projectTemplateMapping;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
