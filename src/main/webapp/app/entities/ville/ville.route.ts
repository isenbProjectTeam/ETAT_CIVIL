import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { VilleComponent } from './ville.component';
import { VilleDetailComponent } from './ville-detail.component';
import { VillePopupComponent } from './ville-dialog.component';
import { VilleDeletePopupComponent } from './ville-delete-dialog.component';

import { Principal } from '../../shared';

export const villeRoute: Routes = [
  {
    path: 'ville',
    component: VilleComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.ville.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'ville/:id',
    component: VilleDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.ville.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const villePopupRoute: Routes = [
  {
    path: 'ville-new',
    component: VillePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.ville.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'ville/:id/edit',
    component: VillePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.ville.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'ville/:id/delete',
    component: VilleDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.ville.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
