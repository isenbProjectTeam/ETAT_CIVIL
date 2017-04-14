import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PieceJointeComponent } from './piece-jointe.component';
import { PieceJointeDetailComponent } from './piece-jointe-detail.component';
import { PieceJointePopupComponent } from './piece-jointe-dialog.component';
import { PieceJointeDeletePopupComponent } from './piece-jointe-delete-dialog.component';

import { Principal } from '../../shared';

export const pieceJointeRoute: Routes = [
  {
    path: 'piece-jointe',
    component: PieceJointeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pieceJointe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'piece-jointe/:id',
    component: PieceJointeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pieceJointe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pieceJointePopupRoute: Routes = [
  {
    path: 'piece-jointe-new',
    component: PieceJointePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pieceJointe.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'piece-jointe/:id/edit',
    component: PieceJointePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pieceJointe.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'piece-jointe/:id/delete',
    component: PieceJointeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'etatCivilApp.pieceJointe.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
