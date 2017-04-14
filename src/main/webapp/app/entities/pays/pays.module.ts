import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtatCivilSharedModule } from '../../shared';
import {
    PaysService,
    PaysPopupService,
    PaysComponent,
    PaysDetailComponent,
    PaysDialogComponent,
    PaysPopupComponent,
    PaysDeletePopupComponent,
    PaysDeleteDialogComponent,
    paysRoute,
    paysPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paysRoute,
    ...paysPopupRoute,
];

@NgModule({
    imports: [
        EtatCivilSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaysComponent,
        PaysDetailComponent,
        PaysDialogComponent,
        PaysDeleteDialogComponent,
        PaysPopupComponent,
        PaysDeletePopupComponent,
    ],
    entryComponents: [
        PaysComponent,
        PaysDialogComponent,
        PaysPopupComponent,
        PaysDeleteDialogComponent,
        PaysDeletePopupComponent,
    ],
    providers: [
        PaysService,
        PaysPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilPaysModule {}
