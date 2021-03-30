import {User} from "./User";
import {Recipe} from "./Recipe";
import {FridgeProduct} from "./FridgeProduct";

export interface Fridge{
  fridgeId?:number;
  aclUser?:User;
  fridgeName?:string;
  fridgeProducts?:FridgeProduct[];
  recipeDTOList?:Recipe[];
}
