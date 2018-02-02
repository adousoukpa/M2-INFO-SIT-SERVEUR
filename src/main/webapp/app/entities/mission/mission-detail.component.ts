import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Mission } from './mission.model';
import { MissionService } from './mission.service';

@Component({
    selector: 'jhi-mission-detail',
    templateUrl: './mission-detail.component.html'
})
export class MissionDetailComponent implements OnInit, OnDestroy {

    mission: Mission;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private missionService: MissionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMissions();
    }

    load(id) {
        this.missionService.find(id).subscribe((mission) => {
            this.mission = mission;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMissions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'missionListModification',
            (response) => this.load(this.mission.id)
        );
    }
}
