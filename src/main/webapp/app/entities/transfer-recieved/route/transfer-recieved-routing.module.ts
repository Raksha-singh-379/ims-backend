import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransferRecievedComponent } from '../list/transfer-recieved.component';
import { TransferRecievedDetailComponent } from '../detail/transfer-recieved-detail.component';
import { TransferRecievedUpdateComponent } from '../update/transfer-recieved-update.component';
import { TransferRecievedRoutingResolveService } from './transfer-recieved-routing-resolve.service';

const transferRecievedRoute: Routes = [
  {
    path: '',
    component: TransferRecievedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferRecievedDetailComponent,
    resolve: {
      transferRecieved: TransferRecievedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferRecievedUpdateComponent,
    resolve: {
      transferRecieved: TransferRecievedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferRecievedUpdateComponent,
    resolve: {
      transferRecieved: TransferRecievedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transferRecievedRoute)],
  exports: [RouterModule],
})
export class TransferRecievedRoutingModule {}
