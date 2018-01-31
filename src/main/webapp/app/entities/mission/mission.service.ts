import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Mission } from './mission.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MissionService {

    private resourceUrl =  SERVER_API_URL + 'api/missions';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(mission: Mission): Observable<Mission> {
        const copy = this.convert(mission);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mission: Mission): Observable<Mission> {
        const copy = this.convert(mission);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Mission> {
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
     * Convert a returned JSON object to Mission.
     */
    private convertItemFromServer(json: any): Mission {
        const entity: Mission = Object.assign(new Mission(), json);
        entity.dateDebut = this.dateUtils
            .convertLocalDateFromServer(json.dateDebut);
        entity.dateFin = this.dateUtils
            .convertLocalDateFromServer(json.dateFin);
        return entity;
    }

    /**
     * Convert a Mission to a JSON which can be sent to the server.
     */
    private convert(mission: Mission): Mission {
        const copy: Mission = Object.assign({}, mission);
        copy.dateDebut = this.dateUtils
            .convertLocalDateToServer(mission.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateToServer(mission.dateFin);
        return copy;
    }
}
