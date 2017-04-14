import { Personne } from '../personne';
import { User } from '../../shared';
import { Ville } from '../ville';
export class Naissance {
    constructor(
        public id?: number,
        public numeroRegistre?: string,
        public mentionMarginale?: any,
        public dateDeclaration?: any,
        public pere?: Personne,
        public mere?: Personne,
        public enfant?: Personne,
        public agentDeclarant?: User,
        public lieuDeclaration?: Ville,
    ) {
    }
}
