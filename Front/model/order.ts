import {Product} from './product';

export class Order {
  id: number;
  name: string;
  description: string;
  price: number;
  imagePath: string;
  categoryName: string;
  issueDate: string;
  expireDate: string;
  quantity: number;

  constructor(product: Product) {
    this.id = product.id;
    this.name = product.name;
    this.description = product.description;
    this.price = product.price;
    this.imagePath = product.imagePath;
    this.quantity = 1;
    this.categoryName = product.categoryName;
    this.issueDate = product.issueDate;
    this.expireDate = product.expireDate;
  }
}
