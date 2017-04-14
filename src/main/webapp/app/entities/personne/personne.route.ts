import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonneComponent } from './personne.component';
import { PersonneDetailComponent } from './personne-detail.component';
import { PersonnePopupComponent } from './personne-dialog.component';
import { PersonneDeletePopupComponent } from './personne-delete-dialog.component';

import { Principal } from '../../shared';

export const personneRoute: Routes = [
  {
    path: 'personne',
    component: PersonneComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.personne.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'personne/:id',
    component: PersonneDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.personne.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personnePopupRoute: Routes = [
  {
    path: 'personne-new',
    component: PersonnePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.personne.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'personne/:id/edit',
    component: PersonnePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.personne.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'personne/:id/delete',
    component: PersonneDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.personne.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
