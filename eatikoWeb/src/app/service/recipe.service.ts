import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const RECIPE_API = "http://localhost:8081/recipe/";


@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) {
  }

  getRecipesForMainPage() : Observable<any>{
    return this.http.get(RECIPE_API + "getRecipeForMainPage");
  }
}
