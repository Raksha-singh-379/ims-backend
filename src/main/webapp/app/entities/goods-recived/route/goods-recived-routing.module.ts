import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GoodsRecivedComponent } from '../list/goods-recived.component';
import { GoodsRecivedDetailComponent } from '../detail/goods-recived-detail.component';
import { GoodsRecivedUpdateComponent } from '../update/goods-recived-update.component';
import { GoodsRecivedRoutingResolveService } from './goods-recived-routing-resolve.service';

const goodsRecivedRoute: Routes = [
  {
    path: '',
    component: GoodsRecivedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoodsRecivedDetailComponent,
    resolve: {
      goodsRecived: GoodsRecivedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoodsRecivedUpdateComponent,
    resolve: {
      goodsRecived: GoodsRecivedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoodsRecivedUpdateComponent,
    resolve: {
      goodsRecived: GoodsRecivedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(goodsRecivedRoute)],
  exports: [RouterModule],
})
export class GoodsRecivedRoutingModule {}
