import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Template } from './template.model';
import { TemplateService } from './template.service';

@Injectable()
export class TemplatePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private templateService: TemplateService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.templateService.find(id).subscribe(template => this.templateModalRef(component, template));
        } else {
            return this.templateModalRef(component, new Template());
        }
    }

    templateModalRef(component: Component, template: Template): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.template = template;
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
