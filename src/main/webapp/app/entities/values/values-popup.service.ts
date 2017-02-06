import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Values } from './values.model';
import { ValuesService } from './values.service';

@Injectable()
export class ValuesPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private valuesService: ValuesService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.valuesService.find(id).subscribe(values => this.valuesModalRef(component, values));
        } else {
            return this.valuesModalRef(component, new Values());
        }
    }

    valuesModalRef(component: Component, values: Values): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.values = values;
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
