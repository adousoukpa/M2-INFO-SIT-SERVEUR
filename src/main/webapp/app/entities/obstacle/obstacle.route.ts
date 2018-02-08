import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ObstacleComponent } from './obstacle.component';
import { ObstacleDetailComponent } from './obstacle-detail.component';
import { ObstaclePopupComponent } from './obstacle-dialog.component';
import { ObstacleDeletePopupComponent } from './obstacle-delete-dialog.component';

@Injectable()
export class ObstacleResolvePagingParams implements Resolve<any> {

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

export const obstacleRoute: Routes = [
    {
        path: 'obstacle',
        component: ObstacleComponent,
        resolve: {
            'pagingParams': ObstacleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obstacles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'obstacle/:id',
        component: ObstacleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obstacles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const obstaclePopupRoute: Routes = [
    {
        path: 'obstacle-new',
        component: ObstaclePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obstacles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'obstacle/:id/edit',
        component: ObstaclePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obstacles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'obstacle/:id/delete',
        component: ObstacleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obstacles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
