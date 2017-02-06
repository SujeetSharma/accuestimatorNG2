import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Tasks } from './tasks.model';
import { TasksService } from './tasks.service';

@Injectable()
export class TasksPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private tasksService: TasksService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tasksService.find(id).subscribe(tasks => this.tasksModalRef(component, tasks));
        } else {
            return this.tasksModalRef(component, new Tasks());
        }
    }

    tasksModalRef(component: Component, tasks: Tasks): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tasks = tasks;
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
