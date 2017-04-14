import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtatCivilSharedModule } from '../../shared';
import {
    AdresseService,
    AdressePopupService,
    AdresseComponent,
    AdresseDetailComponent,
    AdresseDialogComponent,
    AdressePopupComponent,
    AdresseDeletePopupComponent,
    AdresseDeleteDialogComponent,
    adresseRoute,
    adressePopupRoute,
} from './';

const ENTITY_STATES = [
    ...adresseRoute,
    ...adressePopupRoute,
];

@NgModule({
    imports: [
        EtatCivilSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AdresseComponent,
        AdresseDetailComponent,
        AdresseDialogComponent,
        AdresseDeleteDialogComponent,
        AdressePopupComponent,
        AdresseDeletePopupComponent,
    ],
    entryComponents: [
        AdresseComponent,
        AdresseDialogComponent,
        AdressePopupComponent,
        AdresseDeleteDialogComponent,
        AdresseDeletePopupComponent,
    ],
    providers: [
        AdresseService,
        AdressePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilAdresseModule {}
