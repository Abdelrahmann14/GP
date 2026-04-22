import {Component, OnInit} from '@angular/core';
import {Product} from '../../../model/product';
import {ProductService} from '../../../services/product/product.service';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../../services/security/auth.service';
import {OrderService} from '../../../services/order/order.service';
import {Order} from '../../../model/order';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  showScrollButton = false;

  products: Product[] = [];
  page = 1;  // PAGE I WILL STAND ON
  pageLength = 10;  // HOW MANY PRODUCTS THIS PAGE SHOULD HAVE
  collectionSize: number; // HOW MANY PRODUCTS ON DB
  message = '';
  Math = Math; // Make Math available in template
  // tslint:disable-next-line:max-line-length
  constructor(private cardService: OrderService, private productService: ProductService, private activatedRoute: ActivatedRoute, private authService: AuthService) {
    // ACTIVATE ROUTE IS CLASS MAKE ME CAN GET NOW URL
  }

  ngOnInit(): void {
    console.log('ngOnInit - initial page value:', this.page);
    // tslint:disable-next-line:max-line-length
    this.activatedRoute.paramMap.subscribe(  // EVERY TIME URL PARAM{ID} CHANGE, ACTIVATE ROUTE SAVE IT AND RELOAD PRODUCTS ACCORDING TO THIS
      () => {
        console.log('paramMap changed - calling getProducts with page:', this.page);
        this.getProducts(this.page);  // FIRST LOADING OF PAGE USE PAGE 1 AS DEFAULT
      }
    );
    
    // Add scroll event listener
    window.addEventListener('scroll', () => {
      this.showScrollButton = window.scrollY > 300;
    });
  }

  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  // tslint:disable-next-line:typedef
  getProducts(page){
    console.log('getProducts called with page:', page);
    const idExist = this.activatedRoute.snapshot.paramMap.has('id');
    const keyExist = this.activatedRoute.snapshot.paramMap.has('key');
    if (idExist) {
      const id = this.activatedRoute.snapshot.paramMap.get('id'); // IF AR HAVE ID ,GO TO GET PRODUCTS HAVE SAME CATEGORY
      this.getAllProductsById(id, page);
    } else if (keyExist) {
      const key = this.activatedRoute.snapshot.paramMap.get('key'); // IF AR HAVE KEY, GO TO AND GET ALL PRODUCTS HAVE THE SAME KEY
      this.search(key, page);
    } else { // ELSE GET ALL PRODUCTS
      this.getAllProducts();
    }
  }

  // tslint:disable-next-line:typedef
   getAllProducts() {
     this.productService.getProducts().subscribe(
      response => {
        // Handle different response structures
        if (response && response.products) {
          this.products = response.products;
        } else if (Array.isArray(response)) {
          this.products = response;
        } else {
          this.products = [];
        }
        this.message = '';
      //  console.log('Products loaded:', this.products.length, 'Total:', this.collectionSize);
      }, errorResponse => {
        this.products = [];
        this.collectionSize = 0;
        this.message = errorResponse.error?.bundleMessage?.[0]?.message || 'Unexpected error';
        // console.error('Error loading products:', errorResponse);
      }
    );
  }

  // tslint:disable-next-line:typedef
  private getAllProductsById(categoryId, page) {
    console.log('getAllProductsById called with categoryId:', categoryId, 'page:', page, 'pageLength:', this.pageLength);
    // تأكد من أن page هو رقم وليس قيمة نصية
    const pageNumber = Number(page);
    console.log('Converted page to number:', pageNumber);
    this.productService.getProductsByCategoryId(categoryId, pageNumber, this.pageLength).subscribe(
      response => {
        // Handle different response structures
        console.log('getAllProductsById response:', response);
        if (response && response.products) {
          this.products = response.products;
          this.collectionSize = response.totalProducts || response.totalElements || 0;
        } else if (Array.isArray(response)) {
          this.products = response;
          this.collectionSize = response.length;
        } else {
          this.products = [];
          this.collectionSize = 0;
        }
        this.message = '';
      }, errorResponse => {
        this.products = [];
        this.collectionSize = 0;
        this.message = errorResponse.error?.bundleMessage?.[0]?.message || 'Unexpected error';
        console.error('Error loading category products:', errorResponse);
      }
    );
  }

  // tslint:disable-next-line:typedef
  private search(key, page) {
    console.log('search called with key:', key, 'page:', page, 'pageLength:', this.pageLength);
    // تأكد من أن page هو رقم وليس قيمة نصية
    const pageNumber = Number(page);
    console.log('Converted page to number:', pageNumber);
    this.productService.search(key, pageNumber, this.pageLength).subscribe(
      response => {
        // Handle different response structures
        console.log('search response:', response);
        if (response && response.products) {
          this.products = response.products;
          this.collectionSize = response.totalProducts || response.totalElements || 0;
        } else if (Array.isArray(response)) {
          this.products = response;
          this.collectionSize = response.length;
        } else {
          this.products = [];
          this.collectionSize = 0;
        }
        this.message = '';
        console.log('Search results loaded:', this.products.length, 'Total:', this.collectionSize);
      }, errorResponse => {
        this.products = [];
        this.collectionSize = 0;
        this.message = errorResponse.error?.bundleMessage?.[0]?.message || 'Unexpected error';
        console.error('Error searching products:', errorResponse);
      }
    );
  }

  // tslint:disable-next-line:typedef
  // tslint:disable-next-line:typedef
  doPagination(newPage: number): void {
    console.log('doPagination called with newPage:', newPage, 'current page:', this.page);
    this.page = newPage; // تحديث رقم الصفحة بالقيمة المستلمة
    console.log('page updated to:', this.page);
    this.getProducts(this.page);  // GET PRODUCTS ON THE PAGE WHICH USER CHOOSE IT
  }

  // tslint:disable-next-line:typedef
  changePageSize(event: Event) {
    this.pageLength = +(event.target as HTMLInputElement).value; // + MEANING THAT CONVERTS TO NUMBER
    // (event.target as HTMLInputElement) --> CASTING HTML CODE TO BE VALUE FROM EVENT COME FROM PRODUCT.HTML
    this.page = 1; // Reset to first page when changing page size
    this.getProducts(this.page);
  }
  isUserAdmin() {
    return this.authService.isUserAdmin();
  }

  // Pagination helper functions
  getTotalPages(): number {
    return Math.ceil(this.collectionSize / this.pageLength);
  }

  getPageNumbers(): (number | string)[] {
    const totalPages = this.getTotalPages();
    const currentPage = this.page;
    const maxVisible = 5;
    
    if (totalPages <= maxVisible) {
      return Array.from({length: totalPages}, (_, i) => i + 1);
    }
    
    const pages: (number | string)[] = [];
    
    if (currentPage <= 3) {
      for (let i = 1; i <= 4; i++) {
        pages.push(i);
      }
      pages.push('...');
      pages.push(totalPages);
    } else if (currentPage >= totalPages - 2) {
      pages.push(1);
      pages.push('...');
      for (let i = totalPages - 3; i <= totalPages; i++) {
        pages.push(i);
      }
    } else {
      pages.push(1);
      pages.push('...');
      for (let i = currentPage - 1; i <= currentPage + 1; i++) {
        pages.push(i);
      }
      pages.push('...');
      pages.push(totalPages);
    }
    
    return pages;
  }

  // tslint:disable-next-line:typedef
  addProduct(product: Product){
    const order = new Order(product);
    this.cardService.addProductToOrder(order);
    
    // Add clicked state to button
    product.clicked = true;
    
    // Reset button state after 2 seconds
    setTimeout(() => {
      product.clicked = false;
    }, 2000);
  }
}
