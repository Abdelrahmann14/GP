import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../../../services/category/category.service';
import {Category} from '../../../model/category';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent  implements OnInit{  // INTERFACE RUN ONCE URL DONE

  categories: Category[] = []; // DEFINE ARRAY OF CATEGORIES
  constructor(private categoryService: CategoryService) {  // INJECT CATEGORY SERVICE
  }

  ngOnInit(): void {  // DO IN THIS FUNCTION WHAT I NEED LIKE SEND MESSAGE HELLO
    this.getAllCategories(/*WRITE HERE*/);
  }

  // tslint:disable-next-line:typedef
  getAllCategories(){
    // tslint:disable-next-line:max-line-length
    this.categoryService.getCategories().subscribe( /*SUBSCRIBE THE DATA IN YRL ON SERVICE CLASS AND SOLVE TO CATEGORY ARRAY TO DRAW IT IN HTML*/
      response => this.categories = response
    );
  }


}
