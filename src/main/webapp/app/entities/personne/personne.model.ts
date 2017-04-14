
const enum Genre {
    'Masculin',
    'Feminin'

};
import { Adresse } from '../adresse';
import { Ville } from '../ville';
export class Personne {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public genre?: Genre,
        public fonction?: string,
        public dateNaissance?: any,
        public adresse?: Adresse,
        public lieuNaissance?: Ville,
        public pere?: Personne,
        public mere?: Personne,
    ) {
    }
}
