import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ProductsComponent} from '../products/products.component';
import {Product} from '../../../model/product';
import {ProductService} from '../../../services/product/product.service';
import { TranslationService } from '../../../services/translation.service';
import {CategoryService} from '../../../services/category/category.service';
import {Category} from '../../../model/category';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // Products data
  allProducts: Product[] = [];
  filteredProducts: Product[] = [];
  paginatedProducts: Product[] = [];
  message = '';

  // Categories data
  categories: Category[] = [];

  // Navigation
  activeSection: string = 'products';

  // Search and filtering
  searchTerm: string = '';
  selectedCategory: string = '';
  sortBy: string = 'name';
  isSearching: boolean = false;

  // Pagination
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 1;
  collectionSize: number = 0;

  // Helper property for template
  Math = Math;

  // Sidebar properties
  sidebarCollapsed: boolean = false;

  // Add Product Modal properties
  showAddProductModal: boolean = false;
  isSubmitting: boolean = false;
  
  // New product form data
  newProduct = {
    name: '',
    category: '',
    price: 0,
    description: '',
    image: ''
  };

  // Add Category Modal properties
  showAddCategoryModal: boolean = false;
  isSubmittingCategory: boolean = false;
  
  // New category form data
  newCategory = {
    name: '',
    logo: 'fas fa-utensils',
    flag: ''
  };

  // Available icons for category selection
  availableIcons = [
    { name: 'Utensils', class: 'fas fa-utensils' },
    { name: 'Pizza', class: 'fas fa-pizza-slice' },
    { name: 'Burger', class: 'fas fa-hamburger' },
    { name: 'Coffee', class: 'fas fa-coffee' },
    { name: 'Wine', class: 'fas fa-wine-glass-alt' },
    { name: 'Ice Cream', class: 'fas fa-ice-cream' },
    { name: 'Apple', class: 'fas fa-apple-alt' },
    { name: 'Fish', class: 'fas fa-fish' },
    { name: 'Drumstick', class: 'fas fa-drumstick-bite' },
    { name: 'Bread', class: 'fas fa-bread-slice' },
    { name: 'Cheese', class: 'fas fa-cheese' },
    { name: 'Carrot', class: 'fas fa-carrot' }
  ];

  constructor(
    private productService: ProductService,
    public translationService: TranslationService,
    private categoryService: CategoryService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getAllProducts();
    this.getAllCategories();
  }



  // Get all products from service
  getAllProducts() {
    this.productService.getProducts().subscribe(
      response => {
        if (response && response.products) {
          this.allProducts = response.products;
          this.collectionSize = response.totalElements || response.products.length;
        } else if (Array.isArray(response)) {
          this.allProducts = response;
          this.collectionSize = response.length;
        } else {
          this.allProducts = [];
          this.collectionSize = 0;
        }
        this.filteredProducts = [...this.allProducts];
        this.updatePagination();
      },
      errorResponse => {
        this.allProducts = [];
        this.filteredProducts = [];
        this.collectionSize = 0;
        this.message = errorResponse.error?.bundleMessage?.[0]?.message || 'Unexpected error';
        this.updatePagination();
      }
    );
  }

  // Get all categories from service
  getAllCategories() {
    this.categoryService.getCategories().subscribe(
      response => {
        this.categories = response;
      },
      error => {
        console.log('Error fetching categories:', error);
        // Fallback categories if API fails
        this.categories = [
          { id: 0, name: 'ALL', logo: 'fas fa-utensils', flag: '🍽️ ALL' },
          { id: 1, name: 'FOODS', logo: 'fas fa-utensils', flag: '🍽️ FOODS' },
          { id: 2, name: 'DRINKS', logo: 'fas fa-wine-glass-alt', flag: '🥤 DRINKS' },
          { id: 3, name: 'SWEETS', logo: 'fas fa-ice-cream', flag: '🍰 SWEETS' }
        ];
      }
    );
  }

  // Navigation methods
  setActiveSection(section: string): void {
    this.activeSection = section;
    // Update active state in sidebar
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => link.classList.remove('active'));
    
    // Add active class to clicked link
    setTimeout(() => {
      const activeLink = document.querySelector(`[onclick*="${section}"]`);
      if (activeLink) {
        activeLink.classList.add('active');
      }
    }, 0);
  }

  getPageTitle(): string {
    switch(this.activeSection) {
      case 'products': return 'Products';
      case 'categories': return 'Categories';
      case 'offers': return 'Offers';
      case 'members': return 'Members';
      default: return 'Products';
    }
  }

  // Search and filter methods (using same logic as header search and ProductService)
  filterProducts(): void {
    if (this.searchTerm.trim() === '') {
      // If search is empty, filter by category only
      this.isSearching = false;
      this.applyFilters();
    } else {
      // Use the same search API as header and products component
      this.isSearching = true;
      this.searchProducts(this.searchTerm.trim());
    }
  }

  // Apply category and other filters to all products
  applyFilters(): void {
    this.filteredProducts = [...this.allProducts];
    
    // Apply category filter
    if (this.selectedCategory && this.selectedCategory !== '' && this.selectedCategory !== 'ALL') {
      this.filteredProducts = this.filteredProducts.filter(product => 
        product.categoryName && product.categoryName.toLowerCase().includes(this.selectedCategory.toLowerCase())
      );
    }
    
    this.sortProducts();
    this.currentPage = 1; // Reset to first page when filtering
    this.updatePagination();
  }

  // Search using ProductService API (same as ProductsComponent)
  searchProducts(searchKey: string): void {
    this.productService.search(searchKey, this.currentPage, this.itemsPerPage).subscribe(
      response => {
        if (response && response.products) {
          this.filteredProducts = response.products;
          this.collectionSize = response.totalElements || response.products.length;
        } else if (Array.isArray(response)) {
          this.filteredProducts = response;
          this.collectionSize = response.length;
        } else {
          this.filteredProducts = [];
          this.collectionSize = 0;
        }
        
        // Apply category filter if selected
        if (this.selectedCategory && this.selectedCategory !== '' && this.selectedCategory !== 'ALL') {
          this.filteredProducts = this.filteredProducts.filter(product => 
            product.categoryName && product.categoryName.toLowerCase().includes(this.selectedCategory.toLowerCase())
          );
        }
        
        this.sortProducts();
        this.updatePagination();
        this.isSearching = false; // Reset loading state
      },
      errorResponse => {
        this.filteredProducts = [];
        this.collectionSize = 0;
        this.message = errorResponse.error?.bundleMessage?.[0]?.message || 'No products found';
        this.updatePagination();
        this.isSearching = false; // Reset loading state
      }
    );
  }

  sortProducts(): void {
    this.filteredProducts.sort((a, b) => {
      switch(this.sortBy) {
        case 'name':
          return a.name.localeCompare(b.name);
        case 'price':
          return a.price - b.price;
        case 'category':
          return a.categoryName.localeCompare(b.categoryName);
        default:
          return 0;
      }
    });
    this.updatePagination();
  }

  // Pagination methods
  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredProducts.length / this.itemsPerPage);
    this.updatePaginatedProducts();
  }

  updatePaginatedProducts(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedProducts = this.filteredProducts.slice(startIndex, endIndex);
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages && page !== this.currentPage && !this.isSearching) {
      this.currentPage = page;
      
      // Immediately update paginated products to prevent UI jumping
      this.updatePaginatedProducts();
      
      // If searching, call search API with new page after UI update
      if (this.searchTerm.trim()) {
        this.isSearching = true;
        this.searchProducts(this.searchTerm.trim());
      }
    }
  }

  private _visiblePages: number[] = [];
  private _lastCurrentPage: number = 0;
  private _lastTotalPages: number = 0;

  getVisiblePages(): number[] {
    // Only recalculate if current page or total pages changed
    if (this.currentPage !== this._lastCurrentPage || this.totalPages !== this._lastTotalPages) {
      const pages: number[] = [];
      const maxVisible = 5;
      let startPage = Math.max(1, this.currentPage - Math.floor(maxVisible / 2));
      let endPage = Math.min(this.totalPages, startPage + maxVisible - 1);

      if (endPage - startPage + 1 < maxVisible) {
        startPage = Math.max(1, endPage - maxVisible + 1);
      }

      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }

      this._visiblePages = pages;
      this._lastCurrentPage = this.currentPage;
      this._lastTotalPages = this.totalPages;
    }

    return this._visiblePages;
  }

  // Add Product Modal functionality
  openAddProductModal(): void {
    this.showAddProductModal = true;
    this.resetForm();
  }

  closeAddProductModal(): void {
    this.showAddProductModal = false;
    this.resetForm();
  }

  resetForm(): void {
    this.newProduct = {
      name: '',
      category: '',
      price: 0,
      description: '',
      image: ''
    };
    this.isSubmitting = false;
  }

  onSubmitProduct(): void {
    if (!this.newProduct.name || !this.newProduct.category || !this.newProduct.price || !this.newProduct.description) {
      alert('Please fill in all required fields!');
      return;
    }

    if (this.newProduct.price <= 0) {
      alert('Price must be greater than 0!');
      return;
    }

    this.isSubmitting = true;
    
    // TODO: Implement actual product addition logic
    console.log('Adding new product:', this.newProduct);
    
    // Simulate API call
    setTimeout(() => {
      this.isSubmitting = false;
      this.closeAddProductModal();
      alert('Product added successfully!');
      // Optionally refresh the products list
      this.getAllProducts();
    }, 2000);
  }

  // Sidebar functionality
  toggleSidebar(): void {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }

  // Navigate to products page
  navigateToProducts(): void {
    this.router.navigate(['/products']);
  }

  // Track by function for pagination to prevent unnecessary re-renders
  trackByPage(index: number, page: number): number {
    return page;
  }

  // Track by function for products table
  trackByProduct(index: number, product: any): any {
    return product.id || product.name || index;
  }

  // Handle category filter change
  onCategoryChange(): void {
    this.currentPage = 1; // Reset to first page
    this.filterProducts();
  }

  // Get product count for a specific category
  getProductCountByCategory(categoryName: string): number {
    if (!categoryName || categoryName === 'ALL') {
      return this.allProducts.length;
    }
    return this.allProducts.filter(product => 
      product.categoryName && product.categoryName.toLowerCase().includes(categoryName.toLowerCase())
    ).length;
  }

  // Filter products by category and switch to products view
  filterByCategory(categoryName: string): void {
    this.selectedCategory = categoryName === 'ALL' ? '' : categoryName;
    this.searchTerm = '';
    this.currentPage = 1;
    this.setActiveSection('products');
    this.filterProducts();
  }

  // Track by function for categories
  trackByCategory(index: number, category: Category): any {
    return category.id || category.name || index;
  }

  // Add Category Modal methods
  openAddCategoryModal(): void {
    this.showAddCategoryModal = true;
    this.resetCategoryForm();
  }

  closeAddCategoryModal(): void {
    this.showAddCategoryModal = false;
    this.resetCategoryForm();
  }

  resetCategoryForm(): void {
    this.newCategory = {
      name: '',
      logo: 'fas fa-utensils',
      flag: ''
    };
    this.isSubmittingCategory = false;
  }

  selectIcon(icon: any): void {
    this.newCategory.logo = icon.class;
  }

  trackByIcon(index: number, icon: any): any {
    return icon.class;
  }

  onSubmitCategory(): void {
    if (!this.newCategory.name.trim()) {
      alert('Please enter a category name!');
      return;
    }

    // Auto-generate flag if not provided
    if (!this.newCategory.flag.trim()) {
      this.newCategory.flag = `🍽️ ${this.newCategory.name.toUpperCase()}`;
    }

    this.isSubmittingCategory = true;
    
    // TODO: Implement actual category addition logic with CategoryService
    console.log('Adding new category:', this.newCategory);
    
    // Simulate API call
    setTimeout(() => {
      // Add to local categories array for immediate UI update
      const newCategoryData: Category = {
        id: this.categories.length + 1,
        name: this.newCategory.name.toUpperCase(),
        logo: this.newCategory.logo,
        flag: this.newCategory.flag
      };
      
      this.categories.push(newCategoryData);
      
      this.isSubmittingCategory = false;
      this.closeAddCategoryModal();
      alert('Category added successfully!');
      
      // Optionally refresh categories from API
      // this.getAllCategories();
    }, 1500);
  }

  // Handle image loading errors
  handleImageError(event: any): void {
    const img = event.target;
    img.classList.add('error');
    img.classList.remove('loaded');
    img.src = '../../../assets/img/default-product.jpg';
  }

  // Handle successful image loading
  handleImageLoad(event: any): void {
    const img = event.target;
    img.classList.add('loaded');
    img.classList.remove('error');
  }
}
