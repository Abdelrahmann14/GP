import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../../services/order/order.service';
@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})

export class CardComponent implements OnInit {

  totalSize = 0;
  totalPrice = 0;

  constructor(private cardService: OrderService) {
  }

  // tslint:disable-next-line:typedef
  getCardOrderDetails() {
    this.cardService.totalSize.subscribe(
      value => this.totalSize = value
    );

    this.cardService.totalPrice.subscribe(
      value => this.totalPrice = value
    );
  }

  ngOnInit(): void {
    this.getCardOrderDetails();
  }
}
