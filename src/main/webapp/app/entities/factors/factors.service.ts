import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Factors } from './factors.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class FactorsService {

    private resourceUrl: string =  'api/factors';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(factors: Factors): Observable<Factors> {
        let copy: Factors = Object.assign({}, factors);
        copy.createdon = this.dateUtils.toDate(factors.createdon);
        copy.modifiedon = this.dateUtils.toDate(factors.modifiedon);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(factors: Factors): Observable<Factors> {
        let copy: Factors = Object.assign({}, factors);

        copy.createdon = this.dateUtils.toDate(factors.createdon);

        copy.modifiedon = this.dateUtils.toDate(factors.modifiedon);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Factors> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();

            jsonResponse.createdon = this.dateUtils.convertDateTimeFromServer(jsonResponse.createdon);
            jsonResponse.modifiedon = this.dateUtils.convertDateTimeFromServer(jsonResponse.modifiedon);
            return jsonResponse;
        });
    }

    findByCat(id: number | any): Observable<Factors[]> {
        return this.http.get(`${this.resourceUrl}/ByCat/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            for (let i = 0; i < jsonResponse.length; i++) {
                jsonResponse[i].createdon = this.dateUtils.convertDateTimeFromServer(jsonResponse[i].createdon);
                jsonResponse[i].modifiedon = this.dateUtils.convertDateTimeFromServer(jsonResponse[i].modifiedon);
            }   
        return jsonResponse;
        });
        //return res;
    }

    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].createdon = this.dateUtils.convertDateTimeFromServer(jsonResponse[i].createdon);
            jsonResponse[i].modifiedon = this.dateUtils.convertDateTimeFromServer(jsonResponse[i].modifiedon);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        // TODO Use Response class from @angular/http when the body field will be accessible directly
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => { return this.convertResponse(res); })
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

}
