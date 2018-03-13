import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Obstacle } from './obstacle.model';
import { ObstaclePopupService } from './obstacle-popup.service';
import { ObstacleService } from './obstacle.service';

@Component({
    selector: 'jhi-obstacle-delete-dialog',
    templateUrl: './obstacle-delete-dialog.component.html'
})
export class ObstacleDeleteDialogComponent {

    obstacle: Obstacle;

    constructor(
        private obstacleService: ObstacleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.obstacleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'obstacleListModification',
                content: 'Deleted an obstacle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-obstacle-delete-popup',
    template: ''
})
export class ObstacleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private obstaclePopupService: ObstaclePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.obstaclePopupService
                .open(ObstacleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
