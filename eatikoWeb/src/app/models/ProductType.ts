import {Product} from "./Product";

export interface ProductType{
  productTypeId?:number;
  products?:Product[];
  typeName:string;
}
