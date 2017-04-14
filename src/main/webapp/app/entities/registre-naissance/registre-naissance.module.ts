import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtatCivilSharedModule } from '../../shared';
import {
    RegistreNaissanceService,
    RegistreNaissancePopupService,
    RegistreNaissanceComponent,
    RegistreNaissanceDetailComponent,
    RegistreNaissanceDialogComponent,
    RegistreNaissancePopupComponent,
    RegistreNaissanceDeletePopupComponent,
    RegistreNaissanceDeleteDialogComponent,
    registreNaissanceRoute,
    registreNaissancePopupRoute,
} from './';

const ENTITY_STATES = [
    ...registreNaissanceRoute,
    ...registreNaissancePopupRoute,
];

@NgModule({
    imports: [
        EtatCivilSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RegistreNaissanceComponent,
        RegistreNaissanceDetailComponent,
        RegistreNaissanceDialogComponent,
        RegistreNaissanceDeleteDialogComponent,
        RegistreNaissancePopupComponent,
        RegistreNaissanceDeletePopupComponent,
    ],
    entryComponents: [
        RegistreNaissanceComponent,
        RegistreNaissanceDialogComponent,
        RegistreNaissancePopupComponent,
        RegistreNaissanceDeleteDialogComponent,
        RegistreNaissanceDeletePopupComponent,
    ],
    providers: [
        RegistreNaissanceService,
        RegistreNaissancePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilRegistreNaissanceModule {}
