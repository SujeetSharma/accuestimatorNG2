import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Tasks } from './tasks.model';
import { TasksService } from './tasks.service';

@Component({
    selector: 'jhi-tasks-detail',
    templateUrl: './tasks-detail.component.html'
})
export class TasksDetailComponent implements OnInit, OnDestroy {

    tasks: Tasks;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private tasksService: TasksService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['tasks', 'tYPEENUM', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.tasksService.find(id).subscribe(tasks => {
            this.tasks = tasks;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
