import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {FridgeService} from "../../service/fridge.service";
import {Fridge} from "../../models/Fridge";
import {TokenService} from "../../service/token.service";

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

  constructor(private route: ActivatedRoute,
              private fridgeService: FridgeService,
              private tokenService: TokenService) {
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
          this.isDataLoaded = true;
        });
    }

  }


}
