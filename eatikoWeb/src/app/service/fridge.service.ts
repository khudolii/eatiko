import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Fridge} from "../models/Fridge";
import {Observable} from "rxjs";
import {FridgeProduct} from "../models/FridgeProduct";

const FRIDGE_API = "http://localhost:8081/fridge";

@Injectable({
  providedIn: 'root'
})
export class FridgeService {

  constructor(private http: HttpClient) {
  }

  createFridge(name: string): Observable<any> {
    return this.http.post(FRIDGE_API + "/addFridge", {fridgeName: name});
  }

  getAllFridges(): Observable<any> {
    return this.http.get(FRIDGE_API + "/getFridges");
  }

  getFridgeById(id: number): Observable<any> {
    return this.http.get(FRIDGE_API + "/" + id + "/getFridgeById");
  }

  addProductToFridge(fridgeProduct: FridgeProduct): Observable<any> {
    return this.http.post(FRIDGE_API + "/" + fridgeProduct.fridgeId + "/addProduct", fridgeProduct)
  }

  getRecipesByFridgeProducts(fridgeId: number): Observable<any> {
    return this.http.get(FRIDGE_API + "/" + fridgeId + "/getRecipesByFridgeProducts");
  }
}
