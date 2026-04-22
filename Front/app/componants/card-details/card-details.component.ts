import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../services/order/order.service';
import { Order } from '../../../model/order';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit {
  orders: Order[] = [];
  totalPrice = 0;
  totalSize = 0;
  showInvoice = false;
  showCheckoutForm = false;
  invoiceNumber = '';
  invoiceDate = '';

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(): void {
    this.orders = this.orderService.orders;
    
    this.orderService.totalPrice.subscribe(
      value => this.totalPrice = value
    );

    this.orderService.totalSize.subscribe(
      value => this.totalSize = value
    );
  }

  incrementQuantity(order: Order): void {
    this.orderService.addProductToOrder(order);
  }

  decrementQuantity(order: Order): void {
    this.orderService.decrementOrder(order);
  }

  removeOrder(order: Order): void {
    this.orderService.removeOrder(order);
  }

  proceedToCheckout(): void {
    this.generateInvoice();
    this.showInvoice = true;
  }

  generateInvoice(): void {
    // Generate invoice number
    this.invoiceNumber = 'INV-' + Date.now().toString().slice(-6);
    
    // Set current date
    const now = new Date();
    this.invoiceDate = now.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  printInvoice(): void {
    window.print();
  }

  closeInvoice(): void {
    this.showInvoice = false;
  }

  completeOrder(): void {
    // Clear the cart
    this.orderService.clearCart();
    this.showInvoice = false;
    alert('تم إتمام الطلب بنجاح! شكراً لك.');
  }

  toggleCheckoutForm(): void {
    this.showCheckoutForm = !this.showCheckoutForm;
  }
}
