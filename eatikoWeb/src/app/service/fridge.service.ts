import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Fridge} from "../models/Fridge";
import {Observable} from "rxjs";
import {AppModule} from "../app.module";
import {FridgeProduct} from "../models/FridgeProduct";

const FRIDGE_API = AppModule.API + "/fridge";

@Injectable({
  providedIn: 'root'
})
export class FridgeService {

  constructor(private http: HttpClient) {
  }

  createFridge(fridge: Fridge): Observable<any> {
    return this.http.post(FRIDGE_API + "/addFridge", fridge);
  }

  getAllFridges(): Observable<any> {
    return this.http.get(FRIDGE_API + "/getFridges");
  }

  addProductToFridge(fridgeId: number, fridgeProduct: FridgeProduct): Observable<any> {
    return this.http.post(FRIDGE_API + "/" + fridgeId + "/addProduct", fridgeProduct)
  }

  getRecipesByFridgeProducts(fridgeId: number): Observable<any> {
    return this.http.get(FRIDGE_API + "/" + fridgeId + "/getRecipesByFridgeProducts");
  }
}
