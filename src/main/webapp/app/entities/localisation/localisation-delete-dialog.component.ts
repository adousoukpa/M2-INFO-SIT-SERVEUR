import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Localisation } from './localisation.model';
import { LocalisationPopupService } from './localisation-popup.service';
import { LocalisationService } from './localisation.service';

@Component({
    selector: 'jhi-localisation-delete-dialog',
    templateUrl: './localisation-delete-dialog.component.html'
})
export class LocalisationDeleteDialogComponent {

    localisation: Localisation;

    constructor(
        private localisationService: LocalisationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.localisationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'localisationListModification',
                content: 'Deleted an localisation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-localisation-delete-popup',
    template: ''
})
export class LocalisationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private localisationPopupService: LocalisationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.localisationPopupService
                .open(LocalisationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
