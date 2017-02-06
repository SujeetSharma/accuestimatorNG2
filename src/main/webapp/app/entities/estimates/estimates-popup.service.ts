import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Estimates } from './estimates.model';
import { EstimatesService } from './estimates.service';

@Injectable()
export class EstimatesPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private estimatesService: EstimatesService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.estimatesService.find(id).subscribe(estimates => this.estimatesModalRef(component, estimates));
        } else {
            return this.estimatesModalRef(component, new Estimates());
        }
    }

    estimatesModalRef(component: Component, estimates: Estimates): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.estimates = estimates;
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
