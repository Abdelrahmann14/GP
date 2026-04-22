export class Product {
  id: number;
  name: string;
  description: string;
  price: number;
  imagePath: string;
  categoryName: string;
  issueDate: string;
  expireDate: string;
  clicked?: boolean; // Optional property for button state
}
