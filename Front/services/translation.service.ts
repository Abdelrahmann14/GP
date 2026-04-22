import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private currentLang = new BehaviorSubject<string>('en');
  public currentLang$ = this.currentLang.asObservable();

  private translations = {
    en: {
      // Dashboard
      dashboard: 'Dashboard',
      dashboard_title: 'Dashboard',
      lectures: 'Lectures',
      search_placeholder: 'Search products...',
      search_products: 'Search products...',
      users: 'Users',
      products: 'Products',
      posts: 'Posts',
      revenue: 'Revenue',
      add: 'Add',
      add_product: 'Add Product',
      categories: 'Categories',
      clear_filters: 'Clear Filters',
      total_records: 'Total: {{count}} records',
      total_products: 'Total Products',
      orders: 'Orders',
      customers: 'Customers',
      product_management: 'Product Management',
      
      // Table Headers
      id: 'ID',
      lecture_name: 'Lecture Name',
      product_name: 'Product Name',
      price: 'Price',
      category: 'Category',
      description: 'Description',
      issue_date: 'Issue Date',
      expire_date: 'Expire Date',
      status: 'Status',
      video_duration: 'Video Duration',
      content_count: 'Content Count',
      views_count: 'Views Count',
      actions: 'Actions',
      stock: 'Stock',
      
      // Status
      active: 'Active',
      inactive: 'Inactive',
      
      // Actions
      delete: 'Delete',
      edit: 'Edit',
      view: 'View',
      copy: 'Copy',
      filter: 'Filter',
      
      // Navigation
      products_nav: 'PRODUCTS',
      team: 'TEAM',
      contact: 'CONTACT',
      dashboard_nav: 'DASHBOARD',
      login: 'LOGIN',
      logout: 'LOGOUT',
      actions_nav: 'ACTIONS',
      

      
      // Restaurant Name (Always English)
      restaurant_name: 'NEST'
    },
    ar: {
      // Dashboard
      dashboard: 'لوحة التحكم',
      dashboard_title: 'لوحة التحكم',
      lectures: 'المحاضرات',
      search_placeholder: 'البحث في المنتجات...',
      search_products: 'البحث في المنتجات...',
      users: 'المستخدمين',
      products: 'المنتجات',
      posts: 'المنشورات',
      revenue: 'الإيرادات',
      add: 'إضافة',
      add_product: 'إضافة منتج',
      categories: 'تصنيفات',
      clear_filters: 'إلغاء التصفيات',
      total_records: 'المجموع: {{count}} صف',
      total_products: 'إجمالي المنتجات',
      orders: 'الطلبات',
      customers: 'العملاء',
      product_management: 'إدارة المنتجات',
      
      // Table Headers
      id: 'الرقم',
      lecture_name: 'اسم المحاضرة',
      product_name: 'اسم المنتج',
      price: 'السعر',
      category: 'الفئة',
      description: 'الوصف',
      issue_date: 'تاريخ الإصدار',
      expire_date: 'تاريخ الانتهاء',
      status: 'الحالة',
      video_duration: 'مدة الفيديوهات',
      content_count: 'عدد محتويات المحاضرة',
      views_count: 'عدد المشاهدات',
      actions: 'الأفعال',
      stock: 'المخزون',
      
      // Status
      active: 'مفعل',
      inactive: 'غير مفعل',
      
      // Actions
      delete: 'حذف',
      edit: 'تعديل',
      view: 'عرض',
      copy: 'نسخ',
      filter: 'تصفية',
      
      // Navigation
      products_nav: 'المنتجات',
      team: 'الفريق',
      contact: 'اتصل بنا',
      dashboard_nav: 'لوحة التحكم',
      login: 'تسجيل الدخول',
      logout: 'تسجيل الخروج',
      actions_nav: 'الإجراءات',
      

      
      // Restaurant Name (Always English)
      restaurant_name: 'NEST'
    }
  };

  constructor() {
    // Load language from localStorage
    const savedLang = localStorage.getItem('lang') || 'en';
    this.currentLang.next(savedLang);
    this.applyDirection(savedLang);
  }

  changeLanguage(lang: string): void {
    this.currentLang.next(lang);
    localStorage.setItem('lang', lang);
    this.applyDirection(lang);
  }

  private applyDirection(lang: string): void {
    document.documentElement.dir = lang === 'ar' ? 'rtl' : 'ltr';
    document.documentElement.lang = lang;
  }

  translate(key: string, params?: any): string {
    const lang = this.currentLang.value;
    const langTranslations = this.translations[lang as 'en' | 'ar'];
    let translation = langTranslations?.[key as keyof typeof langTranslations] || key;
    
    // Replace parameters
    if (params) {
      Object.keys(params).forEach(param => {
        translation = translation.replace(`{{${param}}}`, params[param]);
      });
    }
    
    return translation;
  }

  getCurrentLanguage(): string {
    return this.currentLang.value;
  }

  isRTL(): boolean {
    return this.currentLang.value === 'ar';
  }
}