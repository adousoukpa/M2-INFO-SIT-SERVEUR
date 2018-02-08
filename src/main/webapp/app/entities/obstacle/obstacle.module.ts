import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServeurSharedModule } from '../../shared';
import {
    ObstacleService,
    ObstaclePopupService,
    ObstacleComponent,
    ObstacleDetailComponent,
    ObstacleDialogComponent,
    ObstaclePopupComponent,
    ObstacleDeletePopupComponent,
    ObstacleDeleteDialogComponent,
    obstacleRoute,
    obstaclePopupRoute,
    ObstacleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...obstacleRoute,
    ...obstaclePopupRoute,
];

@NgModule({
    imports: [
        ServeurSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ObstacleComponent,
        ObstacleDetailComponent,
        ObstacleDialogComponent,
        ObstacleDeleteDialogComponent,
        ObstaclePopupComponent,
        ObstacleDeletePopupComponent,
    ],
    entryComponents: [
        ObstacleComponent,
        ObstacleDialogComponent,
        ObstaclePopupComponent,
        ObstacleDeleteDialogComponent,
        ObstacleDeletePopupComponent,
    ],
    providers: [
        ObstacleService,
        ObstaclePopupService,
        ObstacleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServeurObstacleModule {}
