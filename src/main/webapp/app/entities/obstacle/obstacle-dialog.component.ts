import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Obstacle } from './obstacle.model';
import { ObstaclePopupService } from './obstacle-popup.service';
import { ObstacleService } from './obstacle.service';

@Component({
    selector: 'jhi-obstacle-dialog',
    templateUrl: './obstacle-dialog.component.html'
})
export class ObstacleDialogComponent implements OnInit {

    obstacle: Obstacle;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private obstacleService: ObstacleService,
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
        if (this.obstacle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.obstacleService.update(this.obstacle));
        } else {
            this.subscribeToSaveResponse(
                this.obstacleService.create(this.obstacle));
        }
    }

    private subscribeToSaveResponse(result: Observable<Obstacle>) {
        result.subscribe((res: Obstacle) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Obstacle) {
        this.eventManager.broadcast({ name: 'obstacleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-obstacle-popup',
    template: ''
})
export class ObstaclePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private obstaclePopupService: ObstaclePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.obstaclePopupService
                    .open(ObstacleDialogComponent as Component, params['id']);
            } else {
                this.obstaclePopupService
                    .open(ObstacleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
