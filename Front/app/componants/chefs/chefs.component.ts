import { Component, OnInit } from '@angular/core';
import {Category} from '../../../model/category';
import {CategoryService} from '../../../services/category/category.service';
import {ChefsService} from '../../../services/chefs/chefs.service';
import {Chefs} from '../../../model/chefs';

@Component({
  selector: 'app-chefs',
  templateUrl: './chefs.component.html',
  styleUrls: ['./chefs.component.css']
})
export class ChefsComponent implements OnInit {

  chefs: Chefs[] = []; // DEFINE ARRAY OF CATEGORIES
  constructor(private chefsService: ChefsService) {  // INJECT CATEGORY SERVICE
  }
  ngOnInit(): void {
    this.getAllChefs();
  }

  // tslint:disable-next-line:typedef
  getAllChefs(){
    // tslint:disable-next-line:max-line-length
    this.chefsService.getChefs().subscribe( /*SUBSCRIBE THE DATA IN YRL ON SERVICE CLASS AND SOLVE TO CATEGORY ARRAY TO DRAW IT IN HTML*/
      response => this.chefs = response
    );
  }
}
