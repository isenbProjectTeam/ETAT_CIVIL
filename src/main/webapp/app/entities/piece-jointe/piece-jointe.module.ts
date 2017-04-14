import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtatCivilSharedModule } from '../../shared';
import {
    PieceJointeService,
    PieceJointePopupService,
    PieceJointeComponent,
    PieceJointeDetailComponent,
    PieceJointeDialogComponent,
    PieceJointePopupComponent,
    PieceJointeDeletePopupComponent,
    PieceJointeDeleteDialogComponent,
    pieceJointeRoute,
    pieceJointePopupRoute,
} from './';

const ENTITY_STATES = [
    ...pieceJointeRoute,
    ...pieceJointePopupRoute,
];

@NgModule({
    imports: [
        EtatCivilSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PieceJointeComponent,
        PieceJointeDetailComponent,
        PieceJointeDialogComponent,
        PieceJointeDeleteDialogComponent,
        PieceJointePopupComponent,
        PieceJointeDeletePopupComponent,
    ],
    entryComponents: [
        PieceJointeComponent,
        PieceJointeDialogComponent,
        PieceJointePopupComponent,
        PieceJointeDeleteDialogComponent,
        PieceJointeDeletePopupComponent,
    ],
    providers: [
        PieceJointeService,
        PieceJointePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilPieceJointeModule {}
