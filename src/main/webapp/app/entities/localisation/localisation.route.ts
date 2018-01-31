import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LocalisationComponent } from './localisation.component';
import { LocalisationDetailComponent } from './localisation-detail.component';
import { LocalisationPopupComponent } from './localisation-dialog.component';
import { LocalisationDeletePopupComponent } from './localisation-delete-dialog.component';

@Injectable()
export class LocalisationResolvePagingParams implements Resolve<any> {

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

export const localisationRoute: Routes = [
    {
        path: 'localisation',
        component: LocalisationComponent,
        resolve: {
            'pagingParams': LocalisationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Localisations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'localisation/:id',
        component: LocalisationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Localisations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const localisationPopupRoute: Routes = [
    {
        path: 'localisation-new',
        component: LocalisationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Localisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'localisation/:id/edit',
        component: LocalisationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Localisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'localisation/:id/delete',
        component: LocalisationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Localisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
