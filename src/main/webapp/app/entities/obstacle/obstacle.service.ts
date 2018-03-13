import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Obstacle } from './obstacle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ObstacleService {

    private resourceUrl =  SERVER_API_URL + 'api/obstacles';

    constructor(private http: Http) { }

    create(obstacle: Obstacle): Observable<Obstacle> {
        const copy = this.convert(obstacle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(obstacle: Obstacle): Observable<Obstacle> {
        const copy = this.convert(obstacle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Obstacle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Obstacle.
     */
    private convertItemFromServer(json: any): Obstacle {
        const entity: Obstacle = Object.assign(new Obstacle(), json);
        return entity;
    }

    /**
     * Convert a Obstacle to a JSON which can be sent to the server.
     */
    private convert(obstacle: Obstacle): Obstacle {
        const copy: Obstacle = Object.assign({}, obstacle);
        return copy;
    }
}
