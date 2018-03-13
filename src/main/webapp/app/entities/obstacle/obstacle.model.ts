import { BaseEntity } from './../../shared';

export class Obstacle implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
    ) {
    }
}
