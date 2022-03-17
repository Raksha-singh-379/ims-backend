import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WareHouseComponent } from '../list/ware-house.component';
import { WareHouseDetailComponent } from '../detail/ware-house-detail.component';
import { WareHouseUpdateComponent } from '../update/ware-house-update.component';
import { WareHouseRoutingResolveService } from './ware-house-routing-resolve.service';

const wareHouseRoute: Routes = [
  {
    path: '',
    component: WareHouseComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WareHouseDetailComponent,
    resolve: {
      wareHouse: WareHouseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WareHouseUpdateComponent,
    resolve: {
      wareHouse: WareHouseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WareHouseUpdateComponent,
    resolve: {
      wareHouse: WareHouseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(wareHouseRoute)],
  exports: [RouterModule],
})
export class WareHouseRoutingModule {}
