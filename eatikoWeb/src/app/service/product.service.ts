import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../models/Product";

const PRODUCT_API = "http://localhost:8081/products";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient){ }

  createProduct(product: Product):Observable<any>{
    return this.http.post(PRODUCT_API + "/addProduct", product);
  }

  getAllProducts(): Observable<any>{
    return this.http.get(PRODUCT_API + "/getAllProducts");
  }

  getAllProductsByFilter(type: string): Observable<any>{
  
    return this.http.get(PRODUCT_API + "/getAllProductsByFilter");
  }
}
