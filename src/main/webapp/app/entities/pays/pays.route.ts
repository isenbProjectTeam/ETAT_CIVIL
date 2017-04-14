import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PaysComponent } from './pays.component';
import { PaysDetailComponent } from './pays-detail.component';
import { PaysPopupComponent } from './pays-dialog.component';
import { PaysDeletePopupComponent } from './pays-delete-dialog.component';

import { Principal } from '../../shared';

export const paysRoute: Routes = [
  {
    path: 'pays',
    component: PaysComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pays.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'pays/:id',
    component: PaysDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pays.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paysPopupRoute: Routes = [
  {
    path: 'pays-new',
    component: PaysPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pays.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'pays/:id/edit',
    component: PaysPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pays.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'pays/:id/delete',
    component: PaysDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pays.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
