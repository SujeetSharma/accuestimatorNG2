import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { ProjectUserMapping } from './project-user-mapping.model';
import { ProjectUserMappingService } from './project-user-mapping.service';

@Injectable()
export class ProjectUserMappingPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private projectUserMappingService: ProjectUserMappingService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.projectUserMappingService.find(id).subscribe(projectUserMapping => this.projectUserMappingModalRef(component, projectUserMapping));
        } else {
            return this.projectUserMappingModalRef(component, new ProjectUserMapping());
        }
    }

    projectUserMappingModalRef(component: Component, projectUserMapping: ProjectUserMapping): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.projectUserMapping = projectUserMapping;
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
