import { BaseEntity } from './../../shared';

export class Localisation implements BaseEntity {
    constructor(
        public id?: string,
        public latitude?: number,
        public longitude?: number,
        public altitude?: number,
    ) {
    }
}
