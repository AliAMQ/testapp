import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Business } from 'app/shared/model/business.model';
import { BusinessService } from './business.service';
import { BusinessComponent } from './business.component';
import { BusinessDetailComponent } from './business-detail.component';
import { BusinessUpdateComponent } from './business-update.component';
import { BusinessDeletePopupComponent } from './business-delete-dialog.component';
import { IBusiness } from 'app/shared/model/business.model';

@Injectable({ providedIn: 'root' })
export class BusinessResolve implements Resolve<IBusiness> {
    constructor(private service: BusinessService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((business: HttpResponse<Business>) => business.body));
        }
        return of(new Business());
    }
}

export const businessRoute: Routes = [
    {
        path: 'business',
        component: BusinessComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'testappApp.business.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'business/:id/view',
        component: BusinessDetailComponent,
        resolve: {
            business: BusinessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testappApp.business.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'business/new',
        component: BusinessUpdateComponent,
        resolve: {
            business: BusinessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testappApp.business.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'business/:id/edit',
        component: BusinessUpdateComponent,
        resolve: {
            business: BusinessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testappApp.business.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const businessPopupRoute: Routes = [
    {
        path: 'business/:id/delete',
        component: BusinessDeletePopupComponent,
        resolve: {
            business: BusinessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testappApp.business.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
