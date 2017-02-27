import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ProjectUserMapping } from './project-user-mapping.model';
import { ProjectUserMappingService } from './project-user-mapping.service';

@Component({
    selector: 'jhi-project-user-mapping-detail',
    templateUrl: './project-user-mapping-detail.component.html'
})
export class ProjectUserMappingDetailComponent implements OnInit, OnDestroy {

    projectUserMapping: ProjectUserMapping;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectUserMappingService: ProjectUserMappingService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['projectUserMapping', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.projectUserMappingService.find(id).subscribe(projectUserMapping => {
            this.projectUserMapping = projectUserMapping;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
