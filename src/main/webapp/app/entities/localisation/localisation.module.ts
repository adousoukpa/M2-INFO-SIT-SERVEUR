import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServeurSharedModule } from '../../shared';
import {
    LocalisationService,
    LocalisationPopupService,
    LocalisationComponent,
    LocalisationDetailComponent,
    LocalisationDialogComponent,
    LocalisationPopupComponent,
    LocalisationDeletePopupComponent,
    LocalisationDeleteDialogComponent,
    localisationRoute,
    localisationPopupRoute,
    LocalisationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...localisationRoute,
    ...localisationPopupRoute,
];

@NgModule({
    imports: [
        ServeurSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LocalisationComponent,
        LocalisationDetailComponent,
        LocalisationDialogComponent,
        LocalisationDeleteDialogComponent,
        LocalisationPopupComponent,
        LocalisationDeletePopupComponent,
    ],
    entryComponents: [
        LocalisationComponent,
        LocalisationDialogComponent,
        LocalisationPopupComponent,
        LocalisationDeleteDialogComponent,
        LocalisationDeletePopupComponent,
    ],
    providers: [
        LocalisationService,
        LocalisationPopupService,
        LocalisationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServeurLocalisationModule {}
