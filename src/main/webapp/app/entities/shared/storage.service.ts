import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ReplaySubject } from 'rxjs/ReplaySubject';
 
@Injectable()
export class StorageService {
    private fSubject = new ReplaySubject<any>();
    private tSubject = new ReplaySubject<any>();
    private taskSubject = new ReplaySubject<any>();
    private factorSubject = new ReplaySubject<any>();
    private fTMappingSubject = new ReplaySubject<any>();
 
    getFTMappingDetails(): Observable<any> {
        return this.fTMappingSubject.asObservable();
    }

    setFTMappingDetails(f: string, t: string ) {
        this.fTMappingSubject.next({ factorCat:f, taskCat:t });
        //this.fSubject.complete();
    }
    
    setFactorDetails(id: string ) {
        this.factorSubject.next({ factor:id });
        //this.fSubject.complete();
    }
     setTaskDetails(id: string ) {
        this.taskSubject.next({ task:id });
        //this.fSubject.complete();
    }
    getFactorDetails(): Observable<any> {
        return this.factorSubject.asObservable();
    }

    getTaskDetails(): Observable<any> {
        return this.taskSubject.asObservable();
    }

    setFCatDetails(catname: string, catid: string ) {
        this.fSubject.next({ name: catname, id:catid });
        //this.fSubject.complete();
    }
 
    /*clearFCatDetails() {
        this.fSubject.next();
    }*/
 
    getFCatDetails(): Observable<any> {
        return this.fSubject.asObservable();
    }

    setTCatDetails(catname: string, catid: string ) {
        this.tSubject.next({ name: catname, id:catid });
        this.tSubject.complete();
    }
 
   /* clearTCatDetails() {
        this.tSubject.next();
    }*/
 
    getTCatDetails(): Observable<any> {
        return this.tSubject.asObservable();
         
    }
}