import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mission } from './mission.model';
import { MissionPopupService } from './mission-popup.service';
import { MissionService } from './mission.service';

@Component({
    selector: 'jhi-mission-dialog',
    templateUrl: './mission-dialog.component.html'
})
export class MissionDialogComponent implements OnInit {

    mission: Mission;
    isSaving: boolean;
    dateDebutDp: any;
    dateFinDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private missionService: MissionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mission.id !== undefined) {
            this.subscribeToSaveResponse(
                this.missionService.update(this.mission));
        } else {
            this.subscribeToSaveResponse(
                this.missionService.create(this.mission));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mission>) {
        result.subscribe((res: Mission) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mission) {
        this.eventManager.broadcast({ name: 'missionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mission-popup',
    template: ''
})
export class MissionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private missionPopupService: MissionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.missionPopupService
                    .open(MissionDialogComponent as Component, params['id']);
            } else {
                this.missionPopupService
                    .open(MissionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
