import {ProductType} from "./ProductType";

export interface Product{
  productId:number;
  name:string;
  imgUrl:string;
  type?:string;
//  productType?:ProductType;
}
