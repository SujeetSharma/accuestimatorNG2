import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProjectUserMapping } from './project-user-mapping.model';
import { ProjectUserMappingPopupService } from './project-user-mapping-popup.service';
import { ProjectUserMappingService } from './project-user-mapping.service';

@Component({
    selector: 'jhi-project-user-mapping-delete-dialog',
    templateUrl: './project-user-mapping-delete-dialog.component.html'
})
export class ProjectUserMappingDeleteDialogComponent {

    projectUserMapping: ProjectUserMapping;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectUserMappingService: ProjectUserMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['projectUserMapping', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.projectUserMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'projectUserMappingListModification', content: 'Deleted an projectUserMapping'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-user-mapping-delete-popup',
    template: ''
})
export class ProjectUserMappingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private projectUserMappingPopupService: ProjectUserMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.projectUserMappingPopupService.open(ProjectUserMappingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
