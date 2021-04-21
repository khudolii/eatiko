import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {FridgeService} from "../../service/fridge.service";
import {Fridge} from "../../models/Fridge";
import {TokenService} from "../../service/token.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Product} from "../../models/Product";
import {ProductService} from "../../service/product.service";

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
  allProducts!:Product[];

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
      });
      this.isDataLoaded = true;
    }
  }

  /*  openAddFridgeDialog(): void {
      const dialogRef = this.dialog.open(AddFridgeDialog, {
        width: '250px',
        data: {test: this.test}
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log("Dialog close");
        this.test = result;
      });
    }*/
}

/*
@Component({
selector: 'add-fridge-dialog',
templateUrl: 'add-fridge-dialog.html'
})
export class AddFridgeDialog {
constructor(
  public dialogRef: MatDialogRef<AddFridgeDialog>,
  @Inject(MAT_DIALOG_DATA) public data: DialogData
) {}
onNoClick():void{
  this.dialogRef.close();
}
}*/
