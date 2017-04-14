import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { NaissanceComponent } from './naissance.component';
import { NaissanceDetailComponent } from './naissance-detail.component';
import { NaissancePopupComponent } from './naissance-dialog.component';
import { NaissanceDeletePopupComponent } from './naissance-delete-dialog.component';

import { Principal } from '../../shared';

export const naissanceRoute: Routes = [
  {
    path: 'naissance',
    component: NaissanceComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.naissance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'naissance/:id',
    component: NaissanceDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.naissance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const naissancePopupRoute: Routes = [
  {
    path: 'naissance-new',
    component: NaissancePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.naissance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'naissance/:id/edit',
    component: NaissancePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.naissance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'naissance/:id/delete',
    component: NaissanceDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.naissance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
