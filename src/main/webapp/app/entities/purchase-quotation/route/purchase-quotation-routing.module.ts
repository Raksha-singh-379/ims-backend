import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PurchaseQuotationComponent } from '../list/purchase-quotation.component';
import { PurchaseQuotationDetailComponent } from '../detail/purchase-quotation-detail.component';
import { PurchaseQuotationUpdateComponent } from '../update/purchase-quotation-update.component';
import { PurchaseQuotationRoutingResolveService } from './purchase-quotation-routing-resolve.service';

const purchaseQuotationRoute: Routes = [
  {
    path: '',
    component: PurchaseQuotationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PurchaseQuotationDetailComponent,
    resolve: {
      purchaseQuotation: PurchaseQuotationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PurchaseQuotationUpdateComponent,
    resolve: {
      purchaseQuotation: PurchaseQuotationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PurchaseQuotationUpdateComponent,
    resolve: {
      purchaseQuotation: PurchaseQuotationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(purchaseQuotationRoute)],
  exports: [RouterModule],
})
export class PurchaseQuotationRoutingModule {}
