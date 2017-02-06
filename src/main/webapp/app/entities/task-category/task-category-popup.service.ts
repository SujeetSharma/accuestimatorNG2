import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { TaskCategory } from './task-category.model';
import { TaskCategoryService } from './task-category.service';

@Injectable()
export class TaskCategoryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private taskCategoryService: TaskCategoryService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taskCategoryService.find(id).subscribe(taskCategory => this.taskCategoryModalRef(component, taskCategory));
        } else {
            return this.taskCategoryModalRef(component, new TaskCategory());
        }
    }

    taskCategoryModalRef(component: Component, taskCategory: TaskCategory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taskCategory = taskCategory;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.isOpen = false;
        });
        return modalRef;
    }
}
