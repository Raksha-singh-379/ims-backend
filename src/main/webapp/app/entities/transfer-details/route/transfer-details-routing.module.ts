import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransferDetailsComponent } from '../list/transfer-details.component';
import { TransferDetailsDetailComponent } from '../detail/transfer-details-detail.component';
import { TransferDetailsUpdateComponent } from '../update/transfer-details-update.component';
import { TransferDetailsRoutingResolveService } from './transfer-details-routing-resolve.service';

const transferDetailsRoute: Routes = [
  {
    path: '',
    component: TransferDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferDetailsDetailComponent,
    resolve: {
      transferDetails: TransferDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferDetailsUpdateComponent,
    resolve: {
      transferDetails: TransferDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferDetailsUpdateComponent,
    resolve: {
      transferDetails: TransferDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transferDetailsRoute)],
  exports: [RouterModule],
})
export class TransferDetailsRoutingModule {}
