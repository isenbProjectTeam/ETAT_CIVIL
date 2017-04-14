import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtatCivilSharedModule } from '../../shared';
import { EtatCivilAdminModule } from '../../admin/admin.module';
import {
    NaissanceService,
    NaissancePopupService,
    NaissanceComponent,
    NaissanceDetailComponent,
    NaissanceDialogComponent,
    NaissancePopupComponent,
    NaissanceDeletePopupComponent,
    NaissanceDeleteDialogComponent,
    naissanceRoute,
    naissancePopupRoute,
} from './';

const ENTITY_STATES = [
    ...naissanceRoute,
    ...naissancePopupRoute,
];

@NgModule({
    imports: [
        EtatCivilSharedModule,
        EtatCivilAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NaissanceComponent,
        NaissanceDetailComponent,
        NaissanceDialogComponent,
        NaissanceDeleteDialogComponent,
        NaissancePopupComponent,
        NaissanceDeletePopupComponent,
    ],
    entryComponents: [
        NaissanceComponent,
        NaissanceDialogComponent,
        NaissancePopupComponent,
        NaissanceDeleteDialogComponent,
        NaissanceDeletePopupComponent,
    ],
    providers: [
        NaissanceService,
        NaissancePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilNaissanceModule {}
