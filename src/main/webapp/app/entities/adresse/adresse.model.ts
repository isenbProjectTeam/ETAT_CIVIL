import { Ville } from '../ville';
import { Pays } from '../pays';
export class Adresse {
    constructor(
        public id?: number,
        public rue?: string,
        public ville?: Ville,
        public pays?: Pays,
    ) {
    }
}
