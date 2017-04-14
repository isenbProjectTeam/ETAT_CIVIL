import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EtatCivilPaysModule } from './pays/pays.module';
import { EtatCivilVilleModule } from './ville/ville.module';
import { EtatCivilAdresseModule } from './adresse/adresse.module';
import { EtatCivilPersonneModule } from './personne/personne.module';
import { EtatCivilNaissanceModule } from './naissance/naissance.module';
import { EtatCivilPieceJointeModule } from './piece-jointe/piece-jointe.module';
import { EtatCivilRegistreNaissanceModule } from './registre-naissance/registre-naissance.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EtatCivilPaysModule,
        EtatCivilVilleModule,
        EtatCivilAdresseModule,
        EtatCivilPersonneModule,
        EtatCivilNaissanceModule,
        EtatCivilPieceJointeModule,
        EtatCivilRegistreNaissanceModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtatCivilEntityModule {}
