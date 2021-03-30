import {Recipe} from "./Recipe";
import {Product} from "./Product";

export interface Ingredient{
  ingredientId:number;
  name:string;
  weight?:number;
  imageUrl?:string;
  recipe?:Recipe;
  product?:Product;
}
