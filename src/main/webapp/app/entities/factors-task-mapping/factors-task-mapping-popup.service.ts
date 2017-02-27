import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { FactorsTaskMapping } from './factors-task-mapping.model';
import { FactorsTaskMappingService } from './factors-task-mapping.service';
import { Response } from '@angular/http';

@Injectable()
export class FactorsTaskMappingPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private factorsTaskMappingService: FactorsTaskMappingService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.factorsTaskMappingService.find(id).subscribe(factorsTaskMapping => this.factorsTaskMappingModalRef(component, factorsTaskMapping));
        } else {
            return this.factorsTaskMappingModalRef(component, new FactorsTaskMapping());
        }
    }

    factorsTaskMappingModalRef(component: Component, factorsTaskMapping: FactorsTaskMapping): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.factorsTaskMapping = factorsTaskMapping;
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
