import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RegistreNaissanceComponent } from './registre-naissance.component';
import { RegistreNaissanceDetailComponent } from './registre-naissance-detail.component';
import { RegistreNaissancePopupComponent } from './registre-naissance-dialog.component';
import { RegistreNaissanceDeletePopupComponent } from './registre-naissance-delete-dialog.component';

import { Principal } from '../../shared';

export const registreNaissanceRoute: Routes = [
  {
    path: 'registre-naissance',
    component: RegistreNaissanceComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.registreNaissance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'registre-naissance/:id',
    component: RegistreNaissanceDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.registreNaissance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const registreNaissancePopupRoute: Routes = [
  {
    path: 'registre-naissance-new',
    component: RegistreNaissancePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.registreNaissance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'registre-naissance/:id/edit',
    component: RegistreNaissancePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.registreNaissance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'registre-naissance/:id/delete',
    component: RegistreNaissanceDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.registreNaissance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
