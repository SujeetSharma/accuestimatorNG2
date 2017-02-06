import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { ProjectTemplateMapping } from './project-template-mapping.model';
import { ProjectTemplateMappingService } from './project-template-mapping.service';

@Injectable()
export class ProjectTemplateMappingPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private projectTemplateMappingService: ProjectTemplateMappingService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.projectTemplateMappingService.find(id).subscribe(projectTemplateMapping => this.projectTemplateMappingModalRef(component, projectTemplateMapping));
        } else {
            return this.projectTemplateMappingModalRef(component, new ProjectTemplateMapping());
        }
    }

    projectTemplateMappingModalRef(component: Component, projectTemplateMapping: ProjectTemplateMapping): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.projectTemplateMapping = projectTemplateMapping;
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
