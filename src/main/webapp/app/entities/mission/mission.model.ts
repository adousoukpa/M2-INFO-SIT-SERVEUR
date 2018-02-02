import { BaseEntity } from './../../shared';

export class Mission implements BaseEntity {
    constructor(
        public id?: string,
        public titre?: string,
        public dateDebut?: any,
        public dateFin?: any,
    ) {
    }
}
