import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtatCivilSharedModule } from '../../shared';
import {
    VilleService,
    VillePopupService,
    VilleComponent,
    VilleDetailComponent,
    VilleDialogComponent,
    VillePopupComponent,
    VilleDeletePopupComponent,
    VilleDeleteDialogComponent,
    villeRoute,
    villePopupRoute,
} from './';

const ENTITY_STATES = [
    ...villeRoute,
    ...villePopupRoute,
];

@NgModule({
    imports: [
        EtatCivilSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VilleComponent,
        VilleDetailComponent,
        VilleDialogComponent,
        VilleDeleteDialogComponent,
        VillePopupComponent,
        VilleDeletePopupComponent,
    ],
    entryComponents: [
        VilleComponent,
        VilleDialogComponent,
        VillePopupComponent,
        VilleDeleteDialogComponent,
        VilleDeletePopupComponent,
    ],
    providers: [
        VilleService,
        VillePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilVilleModule {}
