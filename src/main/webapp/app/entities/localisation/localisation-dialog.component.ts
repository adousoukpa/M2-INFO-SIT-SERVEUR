import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Localisation } from './localisation.model';
import { LocalisationPopupService } from './localisation-popup.service';
import { LocalisationService } from './localisation.service';

@Component({
    selector: 'jhi-localisation-dialog',
    templateUrl: './localisation-dialog.component.html'
})
export class LocalisationDialogComponent implements OnInit {

    localisation: Localisation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private localisationService: LocalisationService,
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
        if (this.localisation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.localisationService.update(this.localisation));
        } else {
            this.subscribeToSaveResponse(
                this.localisationService.create(this.localisation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Localisation>) {
        result.subscribe((res: Localisation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Localisation) {
        this.eventManager.broadcast({ name: 'localisationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-localisation-popup',
    template: ''
})
export class LocalisationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private localisationPopupService: LocalisationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.localisationPopupService
                    .open(LocalisationDialogComponent as Component, params['id']);
            } else {
                this.localisationPopupService
                    .open(LocalisationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
