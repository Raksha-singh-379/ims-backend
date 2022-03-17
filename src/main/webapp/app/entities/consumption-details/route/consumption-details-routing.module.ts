import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConsumptionDetailsComponent } from '../list/consumption-details.component';
import { ConsumptionDetailsDetailComponent } from '../detail/consumption-details-detail.component';
import { ConsumptionDetailsUpdateComponent } from '../update/consumption-details-update.component';
import { ConsumptionDetailsRoutingResolveService } from './consumption-details-routing-resolve.service';

const consumptionDetailsRoute: Routes = [
  {
    path: '',
    component: ConsumptionDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConsumptionDetailsDetailComponent,
    resolve: {
      consumptionDetails: ConsumptionDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConsumptionDetailsUpdateComponent,
    resolve: {
      consumptionDetails: ConsumptionDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConsumptionDetailsUpdateComponent,
    resolve: {
      consumptionDetails: ConsumptionDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(consumptionDetailsRoute)],
  exports: [RouterModule],
})
export class ConsumptionDetailsRoutingModule {}
