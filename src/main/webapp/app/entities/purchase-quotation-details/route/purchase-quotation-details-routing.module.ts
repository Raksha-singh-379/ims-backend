import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PurchaseQuotationDetailsComponent } from '../list/purchase-quotation-details.component';
import { PurchaseQuotationDetailsDetailComponent } from '../detail/purchase-quotation-details-detail.component';
import { PurchaseQuotationDetailsUpdateComponent } from '../update/purchase-quotation-details-update.component';
import { PurchaseQuotationDetailsRoutingResolveService } from './purchase-quotation-details-routing-resolve.service';

const purchaseQuotationDetailsRoute: Routes = [
  {
    path: '',
    component: PurchaseQuotationDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PurchaseQuotationDetailsDetailComponent,
    resolve: {
      purchaseQuotationDetails: PurchaseQuotationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PurchaseQuotationDetailsUpdateComponent,
    resolve: {
      purchaseQuotationDetails: PurchaseQuotationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PurchaseQuotationDetailsUpdateComponent,
    resolve: {
      purchaseQuotationDetails: PurchaseQuotationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(purchaseQuotationDetailsRoute)],
  exports: [RouterModule],
})
export class PurchaseQuotationDetailsRoutingModule {}
