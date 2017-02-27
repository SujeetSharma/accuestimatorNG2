import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProjectUserMapping } from './project-user-mapping.model';
import { ProjectUserMappingPopupService } from './project-user-mapping-popup.service';
import { ProjectUserMappingService } from './project-user-mapping.service';
import { ProjectService} from '../project/project.service';
import { Project} from '../project/project.model';
import { UserService} from '../../admin/user-management/user.service';
import { User} from '../../admin/user-management/user.model';
// TODO replace ng-file-upload dependency by an ng2 depedency
// TODO Find a better way to format dates so that it works with NgbDatePicker
@Component({
    selector: 'jhi-project-user-mapping-dialog',
    templateUrl: './project-user-mapping-dialog.component.html'
})
export class ProjectUserMappingDialogComponent implements OnInit {

    projectUserMapping: ProjectUserMapping;
    authorities: any[];
    isSaving: boolean;
    projects: Project[];
    users: User[];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private projectUserMappingService: ProjectUserMappingService,
        private eventManager: EventManager,
        private router: Router,
        private projectService: ProjectService,
        private userService: UserService,
    ) {
        this.jhiLanguageService.setLocations(['projectUserMapping', 'sTATEENUM']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.projectService.query().subscribe(
            (res: Response) => {
                this.projects = res.json();
                //this.searchQuery = null;
            },
            (res: Response) => this.onError(res.json())
        );
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json()},
            (res: Response) => this.onError(res.json())
        );
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    save () {
        this.isSaving = true;
        if (this.projectUserMapping.id !== undefined) {
            this.projectUserMappingService.update(this.projectUserMapping)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.projectUserMappingService.create(this.projectUserMapping)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'projectUserMappingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
        this.router.navigate([{ outlets: { popup: null }}]);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-project-user-mapping-popup',
    template: ''
})
export class ProjectUserMappingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private projectUserMappingPopupService: ProjectUserMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.projectUserMappingPopupService.open(ProjectUserMappingDialogComponent, params['id']);
            } else {
                this.modalRef = this.projectUserMappingPopupService.open(ProjectUserMappingDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
