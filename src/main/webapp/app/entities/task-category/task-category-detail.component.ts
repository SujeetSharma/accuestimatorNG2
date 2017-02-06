import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { TaskCategory } from './task-category.model';
import { TaskCategoryService } from './task-category.service';

@Component({
    selector: 'jhi-task-category-detail',
    templateUrl: './task-category-detail.component.html'
})
export class TaskCategoryDetailComponent implements OnInit, OnDestroy {

    taskCategory: TaskCategory;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskCategoryService: TaskCategoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['taskCategory', 'tYPEENUM', 'sTATEENUM']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.taskCategoryService.find(id).subscribe(taskCategory => {
            this.taskCategory = taskCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
