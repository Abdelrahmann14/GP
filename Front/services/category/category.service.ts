import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Category} from '../../model/category';
import {HttpClient} from '@angular/common/http';
import {map, catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CategoryService{

  baseurl = 'http://localhost:9091/Category'; // URL OF API
  // MUST IMPORT IT ON APP.MODULES
  constructor(private http: HttpClient) { }    // زي ال DEPENDENCY INJECTION

  // Mock categories with beautiful icons
  private mockCategories: Category[] = [
    {
      id: 0,
      name: 'ALL',
      logo: 'fas fa-utensils',
      flag: '🍽️ ALL'
    },
    {
      id: 1,
      name: 'PIZZA',
      logo: 'fas fa-pizza-slice',
      flag: '🍕 PIZZA'
    },
    {
      id: 2,
      name: 'BURGER',
      logo: 'fas fa-hamburger',
      flag: '🍔 BURGER'
    },
    {
      id: 3,
      name: 'CHICKEN',
      logo: 'fas fa-drumstick-bite',
      flag: '🍗 CHICKEN'
    },
    {
      id: 4,
      name: 'SALAD',
      logo: 'fas fa-leaf',
      flag: '🥗 SALAD'
    },
    {
      id: 5,
      name: 'DRINKS',
      logo: 'fas fa-wine-glass-alt',
      flag: '🥤 DRINKS'
    },
    {
      id: 6,
      name: 'DESSERT',
      logo: 'fas fa-ice-cream',
      flag: '🍰 DESSERT'
    }
  ];

  getCategories(): Observable<Category[]> {   // FUNCTION RETURN CLASS {OBSERVABLE}
                                              // tslint:disable-next-line:max-line-length
    return this.http.get<Category[]>(this.baseurl + '/getAllCategory').pipe(  // RETURN FROM URL ARRAY OF CATEGORY AND MAP THIS COLLECTION
      map(
        response => response  // MAKE RESPONSE OF URL = CATEGORY[]
      ),
      catchError(error => {
        console.log('Error fetching categories, using mock data:', error);
        return of(this.mockCategories);
      })
    );
  }
}
