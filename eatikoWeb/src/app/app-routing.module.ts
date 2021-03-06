import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./auth/login/login.component";
import {RegisterComponent} from "./auth/register/register.component";
import {IndexComponent} from "./layout/index/index.component";
import {FridgeComponent} from "./layout/fridge/fridge.component";

const routes: Routes = [
  {path:"", component: IndexComponent},
  {path: "login", component: LoginComponent},
  {path: "registration", component : RegisterComponent},
  {path: "fridge/:id", component: FridgeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
