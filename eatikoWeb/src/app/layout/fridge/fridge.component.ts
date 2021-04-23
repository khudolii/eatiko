import {ChangeDetectorRef, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {FridgeService} from "../../service/fridge.service";
import {Fridge} from "../../models/Fridge";
import {TokenService} from "../../service/token.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Product} from "../../models/Product";
import {ProductService} from "../../service/product.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {Observable} from "rxjs";
import {FridgeProduct} from "../../models/FridgeProduct";

export interface DialogData {
  test: string;
}

@Component({
  selector: 'app-fridge',
  templateUrl: './fridge.component.html',
  styleUrls: ['./fridge.component.css']
})
export class FridgeComponent implements OnInit {
  isLoggedIn: boolean = false;
  isDataLoaded: boolean = false;
  id!: number;
  fridge!: Fridge;
  allProducts!: Product[];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  allProductsDataSource: MatTableDataSource<Product> | any;
  fridgeProductsDataSource: MatTableDataSource<FridgeProduct> | any;
  displayedColumns: string[] = ['name', 'type', 'date', 'action'/*, 'date', 'type', 'action'*/];
  allProductsData: Observable<any> | any;
  fridgeProductsData: Observable<any> | any;

  constructor(private route: ActivatedRoute,
              private fridgeService: FridgeService,
              private tokenService: TokenService,
              public dialog: MatDialog,
              public productService: ProductService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken();
    console.log(this.isLoggedIn);
    if (this.isLoggedIn) {
      this.route.paramMap
        .pipe(switchMap(params => params.getAll("id")))
        .subscribe(data => this.id = +data);
      console.log('FridgeId=' + this.id);

      this.fridgeService.getFridgeById(this.id)
        .subscribe(data => {
          console.log(data);
          this.fridge = data;
          this.fridgeProductsDataSource = new MatTableDataSource(this.fridge.fridgeProducts);
        });

      this.productService.getAllProducts().subscribe(data => {
        console.log(data);
        this.allProducts = data;
        this.allProductsDataSource = new MatTableDataSource(this.allProducts);
        this.isDataLoaded = true;
      });
    }
  }

  ngAfterViewInit() {
    this.allProductsDataSource.paginator = this.paginator;
    this.allProductsData = this.allProductsDataSource.connect();
    this.fridgeProductsData = this.fridgeProductsDataSource.connect();
  }

  applyFilterAllProducts(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.allProductsDataSource.filter = filterValue.trim().toLowerCase();
  }
  applyFilterFridgeProducts(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.fridgeProductsDataSource.filter = filterValue.trim().toLowerCase();
  }
}
