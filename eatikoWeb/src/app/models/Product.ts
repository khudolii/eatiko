import {ProductType} from "./ProductType";

export interface Product{
  productId:number;
  productName:string;
  productType?:ProductType;
}
