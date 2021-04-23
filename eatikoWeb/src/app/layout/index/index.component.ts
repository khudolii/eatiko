import {Component, Inject, OnInit} from '@angular/core';
import {Recipe} from "../../models/Recipe";
import {TokenService} from "../../service/token.service";
import {RecipeService} from "../../service/recipe.service";
import {Fridge} from "../../models/Fridge";
import {FridgeService} from "../../service/fridge.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotificationsService} from "../../service/notifications.service";

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
              public fridgeService: FridgeService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken();
    console.log(this.isLoggedIn);
    if (this.isLoggedIn) {
      this.fridgeService.getAllFridges().subscribe(data => {
        console.log(data);
        this.fridges = data;
      });
      this.recipeService.getRecipesForMainPage()
        .subscribe(data => {
          console.log(data);
          this.recipes = data;
          this.isDataLoaded = true;
        });
    }
  }

  openAddFridgeDialog():void{
    const dialogRef = this.dialog.open(AddFridgeDialog, {
      width: '250px',
    });
   }
}

@Component({
  selector: 'add-fridge-dialog',
  templateUrl: 'add-fridge-dialog.html'
})
export class AddFridgeDialog{
  public form!: FormGroup;
  constructor(
    public dialogRef: MatDialogRef<AddFridgeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Fridge,
    public fridgeService: FridgeService,
    private formBuilder: FormBuilder,
    private notificationService: NotificationsService) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      fridgeName: ['', Validators.compose([Validators.required])]
    });
  }
  onNoClick(): void{
    this.dialogRef.close();
  }

  saveFridge():void{
    this.fridgeService.createFridge(this.form.value.fridgeName).subscribe(data => {
      this.onNoClick();
      let msg = data.msg;
      window.location.reload();
      this.notificationService.showSnackBar(msg);
    }, error => {
      let msg = error.msg;
      this.onNoClick();
      this.notificationService.showSnackBar(msg);
    })
  }
}
