import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { FactorCategory } from './factor-category.model';
import { FactorCategoryService } from './factor-category.service';

@Injectable()
export class FactorCategoryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private factorCategoryService: FactorCategoryService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.factorCategoryService.find(id).subscribe(factorCategory => this.factorCategoryModalRef(component, factorCategory));
        } else {
            return this.factorCategoryModalRef(component, new FactorCategory());
        }
    }

    factorCategoryModalRef(component: Component, factorCategory: FactorCategory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.factorCategory = factorCategory;
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
