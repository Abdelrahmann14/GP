import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Chefs} from '../../model/chefs';

@Injectable({
  providedIn: 'root'
})
export class ChefsService {
  url = 'http://localhost:9091/chef/getAllChefs';
  constructor(private http: HttpClient) {}
    getChefs(): Observable<Chefs[]> {   // FUNCTION RETURN CLASS {OBSERVABLE}
      // tslint:disable-next-line:max-line-length
      return this.http.get<Chefs[]>(this.url ).pipe(  // RETURN FROM URL ARRAY OF CATEGORY AND MAP THIS COLLECTION
        map(
          response => response  // MAKE RESPONSE OF URL = CATEGORY[]
        )
      );
    }
  }
