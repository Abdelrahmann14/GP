import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../services/security/auth.service';
import { TranslationService } from '../../../services/translation.service';
import { ProductService } from '../../../services/product/product.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  searchQuery: string = '';

  constructor(
    private router: Router, 
    private authService: AuthService,
    public translationService: TranslationService,
    private productService: ProductService
  ) {
  }

  ngOnInit() {
  }

  ngOnDestroy() {
  }

  // tslint:disable-next-line:typedef
  public searchProduct(key: string) {
    this.router.navigateByUrl('search/' + key).then();
  }

  // tslint:disable-next-line:typedef
  onSearch() {
    if (this.searchQuery.trim()) {
      this.searchProducts(this.searchQuery.trim());
    }
  }

  // tslint:disable-next-line:typedef
  searchProducts(searchTerm: string) {
    // البحث في الصفحة الأولى مع 10 منتجات
    this.productService.search(searchTerm, 0, 10).subscribe(
      (products) => {
        console.log('Search results:', products);
        // يمكنك هنا إضافة منطق لعرض النتائج أو التنقل إلى صفحة النتائج
        this.router.navigate(['/products'], { 
          queryParams: { search: searchTerm, results: JSON.stringify(products) }
        });
      },
      (error) => {
        console.error('Search error:', error);
        // في حالة الخطأ، يمكن التنقل إلى صفحة البحث العادية
        this.searchProduct(searchTerm);
      }
    );
  }

  // tslint:disable-next-line:typedef
  isUserLoggedIn() {
    return this.authService.isUserLogin();
  }

  // tslint:disable-next-line:typedef
  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }




  
  // tslint:disable-next-line:typedef
  isUserAdmin() {
    return this.authService.isUserAdmin();
  }
}
