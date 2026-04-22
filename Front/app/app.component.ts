import { Component, OnInit } from '@angular/core';
import { TranslationService } from '../services/translation.service';
import {AuthService} from '../services/security/auth.service';
import { Router, NavigationEnd, NavigationStart } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  showHeader = true;
  showFooter = true;
  isLoading = false;

  constructor(
    private translationService: TranslationService,
    private router: Router) {}

  // tslint:disable-next-line:typedef
  ngOnInit() {
    // Initialize language direction
    this.translationService.currentLang$.subscribe(lang => {
      document.documentElement.setAttribute('dir', lang === 'ar' ? 'rtl' : 'ltr');
      document.documentElement.setAttribute('lang', lang);
    });

    // Track route changes to show/hide loader and header/footer
    this.router.events.pipe(
      filter(event => event instanceof NavigationStart)
    ).subscribe(() => {
      this.isLoading = true;
    });

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      // tslint:disable-next-line:no-shadowed-variable
      const hiddenHeaderRoutes = ['/login', '/signup', '/dashboard'];
      this.showHeader = !hiddenHeaderRoutes.includes(event.url);
      
      // Show footer only on products page
      this.showFooter = event.url === '/products';
      
      // Hide loader after a short delay to show the animation
      setTimeout(() => {
        this.isLoading = false;
      }, 500);
    });

    // Check initial route
    const hiddenHeaderRoutes = ['/login', '/signup', '/dashboard'];
    this.showHeader = !hiddenHeaderRoutes.includes(this.router.url);
    this.showFooter = this.router.url === '/products';
  }
}
