
const enum Genre {
    'Masculin',
    'Feminin'

};
export class Personne {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public genre?: Genre,
        public fonction?: string,
    ) {
    }
}
