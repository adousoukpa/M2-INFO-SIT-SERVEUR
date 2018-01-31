import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Localisation } from './localisation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LocalisationService {

    private resourceUrl =  SERVER_API_URL + 'api/localisations';

    constructor(private http: Http) { }

    create(localisation: Localisation): Observable<Localisation> {
        const copy = this.convert(localisation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(localisation: Localisation): Observable<Localisation> {
        const copy = this.convert(localisation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Localisation> {
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
     * Convert a returned JSON object to Localisation.
     */
    private convertItemFromServer(json: any): Localisation {
        const entity: Localisation = Object.assign(new Localisation(), json);
        return entity;
    }

    /**
     * Convert a Localisation to a JSON which can be sent to the server.
     */
    private convert(localisation: Localisation): Localisation {
        const copy: Localisation = Object.assign({}, localisation);
        return copy;
    }
}
