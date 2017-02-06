import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Projects } from './projects.model';
import { ProjectsService } from './projects.service';

@Injectable()
export class ProjectsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private projectsService: ProjectsService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.projectsService.find(id).subscribe(projects => this.projectsModalRef(component, projects));
        } else {
            return this.projectsModalRef(component, new Projects());
        }
    }

    projectsModalRef(component: Component, projects: Projects): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.projects = projects;
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
