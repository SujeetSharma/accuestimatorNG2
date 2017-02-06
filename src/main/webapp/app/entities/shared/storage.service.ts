import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ReplaySubject } from 'rxjs/ReplaySubject';
 
@Injectable()
export class StorageService {
    private fSubject = new ReplaySubject<any>();
    private tSubject = new ReplaySubject<any>();
 
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