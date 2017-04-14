import { Pays } from '../pays';
export class Ville {
    constructor(
        public id?: number,
        public nom?: string,
        public codePostal?: number,
        public pays?: Pays,
    ) {
    }
}
