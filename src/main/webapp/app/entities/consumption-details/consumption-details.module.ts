import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConsumptionDetailsComponent } from './list/consumption-details.component';
import { ConsumptionDetailsDetailComponent } from './detail/consumption-details-detail.component';
import { ConsumptionDetailsUpdateComponent } from './update/consumption-details-update.component';
import { ConsumptionDetailsDeleteDialogComponent } from './delete/consumption-details-delete-dialog.component';
import { ConsumptionDetailsRoutingModule } from './route/consumption-details-routing.module';

@NgModule({
  imports: [SharedModule, ConsumptionDetailsRoutingModule],
  declarations: [
    ConsumptionDetailsComponent,
    ConsumptionDetailsDetailComponent,
    ConsumptionDetailsUpdateComponent,
    ConsumptionDetailsDeleteDialogComponent,
  ],
  entryComponents: [ConsumptionDetailsDeleteDialogComponent],
})
export class ConsumptionDetailsModule {}
