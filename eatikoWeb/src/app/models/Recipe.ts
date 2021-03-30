import {Ingredient} from "./Ingredient";

export interface Recipe{
  recipeId:number;
  recipeName:number;
  imageUrl?:string;
  source?:string;
  sourceUrl?:string;
  calories?:number;
  totalWeight?:number;
  ingredients?:Ingredient[];
}
