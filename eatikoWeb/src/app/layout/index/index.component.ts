import { Component, OnInit } from '@angular/core';
import {Recipe} from "../../models/Recipe";
import {TokenService} from "../../service/token.service";
import {UserService} from "../../service/user.service";
import {RecipeService} from "../../service/recipe.service";
import {Fridge} from "../../models/Fridge";
import {FridgeService} from "../../service/fridge.service";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  isLoggedIn: boolean = false;
  isDataLoaded: boolean = false;
  recipes!:Recipe[];
  fridges!:Fridge[];

  constructor(private tokenService: TokenService,
              private recipeService:RecipeService,
              private fridgeService: FridgeService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken();
    console.log(this.isLoggedIn);
    if (this.isLoggedIn) {
      this.fridgeService.getAllFridges().subscribe(data => {
        console.log(data);
        this.fridges = data;
      });
    }
      this.recipeService.getRecipesForMainPage()
        .subscribe(data => {
          console.log(data);
          this.recipes = data;
          this.isDataLoaded = true;
        });
  }

}
