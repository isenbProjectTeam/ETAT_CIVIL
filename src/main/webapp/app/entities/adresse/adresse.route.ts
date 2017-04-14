import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AdresseComponent } from './adresse.component';
import { AdresseDetailComponent } from './adresse-detail.component';
import { AdressePopupComponent } from './adresse-dialog.component';
import { AdresseDeletePopupComponent } from './adresse-delete-dialog.component';

import { Principal } from '../../shared';

export const adresseRoute: Routes = [
  {
    path: 'adresse',
    component: AdresseComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.adresse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'adresse/:id',
    component: AdresseDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.adresse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const adressePopupRoute: Routes = [
  {
    path: 'adresse-new',
    component: AdressePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.adresse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'adresse/:id/edit',
    component: AdressePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.adresse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'adresse/:id/delete',
    component: AdresseDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.adresse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
