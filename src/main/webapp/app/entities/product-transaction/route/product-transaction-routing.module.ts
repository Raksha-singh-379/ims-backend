import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductTransactionComponent } from '../list/product-transaction.component';
import { ProductTransactionDetailComponent } from '../detail/product-transaction-detail.component';
import { ProductTransactionUpdateComponent } from '../update/product-transaction-update.component';
import { ProductTransactionRoutingResolveService } from './product-transaction-routing-resolve.service';

const productTransactionRoute: Routes = [
  {
    path: '',
    component: ProductTransactionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductTransactionDetailComponent,
    resolve: {
      productTransaction: ProductTransactionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductTransactionUpdateComponent,
    resolve: {
      productTransaction: ProductTransactionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductTransactionUpdateComponent,
    resolve: {
      productTransaction: ProductTransactionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productTransactionRoute)],
  exports: [RouterModule],
})
export class ProductTransactionRoutingModule {}
