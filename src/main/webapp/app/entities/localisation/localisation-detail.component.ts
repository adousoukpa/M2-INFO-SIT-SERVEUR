import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Localisation } from './localisation.model';
import { LocalisationService } from './localisation.service';

@Component({
    selector: 'jhi-localisation-detail',
    templateUrl: './localisation-detail.component.html'
})
export class LocalisationDetailComponent implements OnInit, OnDestroy {

    localisation: Localisation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private localisationService: LocalisationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocalisations();
    }

    load(id) {
        this.localisationService.find(id).subscribe((localisation) => {
            this.localisation = localisation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLocalisations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'localisationListModification',
            (response) => this.load(this.localisation.id)
        );
    }
}
