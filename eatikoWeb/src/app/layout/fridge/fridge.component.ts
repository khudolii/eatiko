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
  dataSource: MatTableDataSource<Product> | any;
  displayedColumns: string[] = ['name', 'img', 'btn'];
  obs: Observable<any> | any;

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
        });

      this.productService.getAllProducts().subscribe(data => {
        console.log(data);
        this.allProducts = data;
        this.dataSource = new MatTableDataSource(this.allProducts);
        this.isDataLoaded = true;
      });
    }
  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.obs = this.dataSource.connect();
  }
}
