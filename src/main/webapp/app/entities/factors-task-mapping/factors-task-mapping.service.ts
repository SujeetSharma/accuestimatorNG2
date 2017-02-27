import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FactorsTaskMapping } from './factors-task-mapping.model';
import { FTEstimates } from './estimates.model';
import { FTEstimate } from './estimate.model';
import { Values } from './values.model';
import { DateUtils } from 'ng-jhipster';
import {Factors } from '../factors/factors.model';
import {Tasks } from '../tasks/tasks.model';
import { CustomerModel, RegionModel } from './customer.model';

 export const CUSTOMERS: CustomerModel[] = [
    {customerId: 11, name: 'Mr. Nice' , currentPeriod: 10, previousPeriod: 81},
    {customerId: 12, name: 'Narco' , currentPeriod: 11, previousPeriod: 82},
    {customerId: 13, name: 'Bombasto' , currentPeriod: 12, previousPeriod: 83},
    {customerId: 14, name: 'Celeritas' , currentPeriod: 13, previousPeriod: 84},
    {customerId: 15, name: 'Magneta' , currentPeriod: 14, previousPeriod: 85},
    {customerId: 16, name: 'RubberMan' , currentPeriod: 15, previousPeriod: 86},
    {customerId: 17, name: 'Dynama' , currentPeriod: 16, previousPeriod: 87},
    {customerId: 18, name: 'Dr IQ' , currentPeriod: 7, previousPeriod: 88},
    {customerId: 19, name: 'Magma' , currentPeriod: 8, previousPeriod: 89},
    {customerId: 20, name: 'Tornado' , currentPeriod: 9, previousPeriod: 90}
  ];

@Injectable()
export class FactorsTaskMappingService {

    private resourceUrl: string =  'api/factors-task-mappings';

    constructor(private http: Http, private dateUtils: DateUtils) { }

 getCustomerByRegion() : Observable<RegionModel> {
  
    let region = new RegionModel();
    region.name = "Asguard Province";
    region.customers = CUSTOMERS;
   
    return Observable.of(region);
  }

  create(factorsTaskMapping: FactorsTaskMapping): Observable<FactorsTaskMapping> {
        let copy: FactorsTaskMapping = Object.assign({}, factorsTaskMapping);
        copy.createdon = this.dateUtils.toDate(factorsTaskMapping.createdon);
        copy.modifiedon = this.dateUtils.toDate(factorsTaskMapping.modifiedon);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(factorsTaskMapping: FactorsTaskMapping): Observable<FactorsTaskMapping> {
        let copy: FactorsTaskMapping = Object.assign({}, factorsTaskMapping);

        copy.createdon = this.dateUtils.toDate(factorsTaskMapping.createdon);

        copy.modifiedon = this.dateUtils.toDate(factorsTaskMapping.modifiedon);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FactorsTaskMapping> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();

            jsonResponse.createdon = this.dateUtils.convertDateTimeFromServer(jsonResponse.createdon);
            jsonResponse.modifiedon = this.dateUtils.convertDateTimeFromServer(jsonResponse.modifiedon);
            return jsonResponse;
        });
    }

    getEstimates(factors: Factors[],tasks: Tasks[]): Observable<FTEstimates> {
       var estimates : FTEstimates = { "estimates" : []};
            for(var i=0; i < tasks.length; i++){
              estimates.estimates[i] = { taskid : tasks[i].id, taskname : tasks[i].name, values : []};
                for(var j=0; j < factors.length; j++){
                    estimates.estimates[i].values[j] = { 
                        factorid : factors[j].id, 
                        factorname : factors[j].name,
                        high : "0",
                        low : "0",
                        medium : "0",
                        points : "0"
                    }
                }
            }
            return Observable.of(estimates);
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
