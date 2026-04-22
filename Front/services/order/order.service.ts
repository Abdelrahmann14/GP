import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {Order} from '../../model/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  orders: Order[] = [];
  totalSize: Subject<number> = new BehaviorSubject<number>(0);
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);

  // case 1 : order first to add
  // case 2 : order already exist add new one
  addProductToOrder(newOrder: Order) {
    // NULL
    // undefined
    let isExist = false;
    let existedOrder: Order;

    if (this.orders.length > 0){
      existedOrder = this.orders.find(order => order.id === newOrder.id);
    }

    // existedOrder  undefined   --
    isExist = (existedOrder !== undefined); // false  true


    if (isExist) {
      existedOrder.quantity++;
    } else {
      this.orders.push(newOrder);
    }

    console.log(this.orders);

    this.calculateTotals();
    console.log(this.totalSize);
    console.log(this.totalPrice);
  }

  // tslint:disable-next-line:typedef
  calculateTotals(){
    let totalElementSize = 0;
    let totalElementPrice = 0;

    for (const temp of this.orders){
      totalElementSize += temp.quantity;
      totalElementPrice += temp.quantity * temp.price;
    }

    this.totalSize.next(totalElementSize);
    this.totalPrice.next(totalElementPrice);
  }

  decrementOrder(order: Order){ // 1    2 3 4 5 6
    order.quantity--;
    if (order.quantity <= 0){
      this.removeOrder(order);
    } else {
      this.calculateTotals();
    }
  }

  // tslint:disable-next-line:typedef
  removeOrder(removedOrder: Order){
    const index = this.orders.findIndex(order => order.id === removedOrder.id); // index 2
    if (index > -1) {
      this.orders.splice(index, 1);
    }
    this.calculateTotals();
  }

  // Clear all orders from cart
  clearCart(): void {
    this.orders = [];
    this.calculateTotals();
  }

}
