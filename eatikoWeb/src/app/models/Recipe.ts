import {Ingredient} from "./Ingredient";

export interface Recipe{
  recipeId:number;
  label:string;
  image?:string;
  source?:string;
  url?:string;
  calories?:number;
  totalWeight?:number;
  ingredients?:Ingredient[];
}
