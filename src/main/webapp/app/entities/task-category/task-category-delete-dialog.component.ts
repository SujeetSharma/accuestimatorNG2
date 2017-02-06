import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { TaskCategory } from './task-category.model';
import { TaskCategoryPopupService } from './task-category-popup.service';
import { TaskCategoryService } from './task-category.service';

@Component({
    selector: 'jhi-task-category-delete-dialog',
    templateUrl: './task-category-delete-dialog.component.html'
})
export class TaskCategoryDeleteDialogComponent {

    taskCategory: TaskCategory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskCategoryService: TaskCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['taskCategory', 'tYPEENUM', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.taskCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'taskCategoryListModification', content: 'Deleted an taskCategory'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-task-category-delete-popup',
    template: ''
})
export class TaskCategoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private taskCategoryPopupService: TaskCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.taskCategoryPopupService.open(TaskCategoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
