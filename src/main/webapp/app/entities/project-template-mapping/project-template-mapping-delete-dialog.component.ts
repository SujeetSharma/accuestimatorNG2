import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProjectTemplateMapping } from './project-template-mapping.model';
import { ProjectTemplateMappingPopupService } from './project-template-mapping-popup.service';
import { ProjectTemplateMappingService } from './project-template-mapping.service';

@Component({
    selector: 'jhi-project-template-mapping-delete-dialog',
    templateUrl: './project-template-mapping-delete-dialog.component.html'
})
export class ProjectTemplateMappingDeleteDialogComponent {

    projectTemplateMapping: ProjectTemplateMapping;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private projectTemplateMappingService: ProjectTemplateMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['projectTemplateMapping', 'sTATEENUM']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    confirmDelete (id: number) {
        this.projectTemplateMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({ name: 'projectTemplateMappingListModification', content: 'Deleted an projectTemplateMapping'});
            this.router.navigate([{ outlets: { popup: null }}]);
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-template-mapping-delete-popup',
    template: ''
})
export class ProjectTemplateMappingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private projectTemplateMappingPopupService: ProjectTemplateMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.projectTemplateMappingPopupService.open(ProjectTemplateMappingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
