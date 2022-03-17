import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransferDetailsApprovalsComponent } from '../list/transfer-details-approvals.component';
import { TransferDetailsApprovalsDetailComponent } from '../detail/transfer-details-approvals-detail.component';
import { TransferDetailsApprovalsUpdateComponent } from '../update/transfer-details-approvals-update.component';
import { TransferDetailsApprovalsRoutingResolveService } from './transfer-details-approvals-routing-resolve.service';

const transferDetailsApprovalsRoute: Routes = [
  {
    path: '',
    component: TransferDetailsApprovalsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferDetailsApprovalsDetailComponent,
    resolve: {
      transferDetailsApprovals: TransferDetailsApprovalsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferDetailsApprovalsUpdateComponent,
    resolve: {
      transferDetailsApprovals: TransferDetailsApprovalsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferDetailsApprovalsUpdateComponent,
    resolve: {
      transferDetailsApprovals: TransferDetailsApprovalsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transferDetailsApprovalsRoute)],
  exports: [RouterModule],
})
export class TransferDetailsApprovalsRoutingModule {}
