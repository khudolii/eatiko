import {Product} from "./Product";

export interface FridgeProduct{
  fridgeProductId?:number;
  productId:number;
  fridgeId:number;
  product: Product,
  weight?:number;
}
