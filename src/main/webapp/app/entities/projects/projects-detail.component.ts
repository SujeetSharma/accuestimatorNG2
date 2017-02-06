import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Projects } from './projects.model';
import { ProjectsService } from './projects.service';

@Component({
    selector: 'jhi-projects-detail',
    templateUrl: './projects-detail.component.html'
})
export class ProjectsDetailComponent implements OnInit, OnDestroy {

    projects: Projects;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectsService: ProjectsService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['projects', 'tYPEENUM', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.projectsService.find(id).subscribe(projects => {
            this.projects = projects;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
