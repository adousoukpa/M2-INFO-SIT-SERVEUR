import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Obstacle } from './obstacle.model';
import { ObstacleService } from './obstacle.service';

@Component({
    selector: 'jhi-obstacle-detail',
    templateUrl: './obstacle-detail.component.html'
})
export class ObstacleDetailComponent implements OnInit, OnDestroy {

    obstacle: Obstacle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private obstacleService: ObstacleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInObstacles();
    }

    load(id) {
        this.obstacleService.find(id).subscribe((obstacle) => {
            this.obstacle = obstacle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInObstacles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'obstacleListModification',
            (response) => this.load(this.obstacle.id)
        );
    }
}
