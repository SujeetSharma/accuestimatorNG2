import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Project } from './project.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ProjectService {

    private resourceUrl: string =  'api/projects';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(project: Project): Observable<Project> {
        let copy: Project = Object.assign({}, project);
        copy.createdon = this.dateUtils.toDate(project.createdon);
        copy.modifiedon = this.dateUtils.toDate(project.modifiedon);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(project: Project): Observable<Project> {
        let copy: Project = Object.assign({}, project);

        copy.createdon = this.dateUtils.toDate(project.createdon);

        copy.modifiedon = this.dateUtils.toDate(project.modifiedon);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Project> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();

            jsonResponse.createdon = this.dateUtils.convertDateTimeFromServer(jsonResponse.createdon);
            jsonResponse.modifiedon = this.dateUtils.convertDateTimeFromServer(jsonResponse.modifiedon);
            return jsonResponse;
        });
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
