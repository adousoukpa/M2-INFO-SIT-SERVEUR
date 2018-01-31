import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MissionComponent } from './mission.component';
import { MissionDetailComponent } from './mission-detail.component';
import { MissionPopupComponent } from './mission-dialog.component';
import { MissionDeletePopupComponent } from './mission-delete-dialog.component';

@Injectable()
export class MissionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const missionRoute: Routes = [
    {
        path: 'mission',
        component: MissionComponent,
        resolve: {
            'pagingParams': MissionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Missions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mission/:id',
        component: MissionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Missions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const missionPopupRoute: Routes = [
    {
        path: 'mission-new',
        component: MissionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Missions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mission/:id/edit',
        component: MissionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Missions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mission/:id/delete',
        component: MissionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Missions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
