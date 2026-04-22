import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Product} from '../../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  baseUrl = 'http://localhost:9091/Product';  // COMMON URL BETWEEN MORE THAN URL TO AVOID REPETITION

  constructor(private http: HttpClient) { }

  getProducts(): Observable<any> {
    return this.http.get<Response>(this.baseUrl + '/getAllProducts').pipe(
      map(
        response => {
          return response;
        }
      )
    );
  }

  getProductsByCategoryId(categoryId, pageNo, pageSize): Observable<any> {
    console.log('ProductService.getProductsByCategoryId called with categoryId:', categoryId, 'pageNo:', pageNo, 'pageSize:', pageSize);
    // تحويل pageNo إلى رَقَم للتأكد من أنه ليس قيمة نصية
    const pageNumber = Number(pageNo);
    console.log('Converted pageNo to number:', pageNumber);
    // tslint:disable-next-line:max-line-length
    return this.http.get<Product[]>(this.baseUrl + '/getAllProductsByCategoryId/' + categoryId + '/page/' + pageNumber + '/size/' + pageSize).pipe(
      map(
        response => {
          console.log('ProductService.getProductsByCategoryId response:', response);
          return response;
        }
      )
    );
  }

  search(name, pageNo, pageSize): Observable<any> {
    console.log('ProductService.search called with name:', name, 'pageNo:', pageNo, 'pageSize:', pageSize);
    // تحويل pageNo إلى رقم للتأكد من أنه ليس قيمة نصية
    const pageNumber = Number(pageNo);
    console.log('Converted pageNo to number:', pageNumber);
    return this.http.get<Product[]>(this.baseUrl + '/getAllProductsByName/' + name + '/page/' + pageNumber + '/size/' + pageSize).pipe(
      map(
        response => {
          console.log('ProductService.search response:', response);
          return response;
        }
      )
    );
  }

}
