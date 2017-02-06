import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Factors } from './factors.model';
import { FactorsService } from './factors.service';

@Injectable()
export class FactorsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private factorsService: FactorsService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.factorsService.find(id).subscribe(factors => this.factorsModalRef(component, factors));
        } else {
            return this.factorsModalRef(component, new Factors());
        }  
    }

    factorsModalRef(component: Component, factors: Factors): NgbModalRef {
        
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.factors = factors;
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
