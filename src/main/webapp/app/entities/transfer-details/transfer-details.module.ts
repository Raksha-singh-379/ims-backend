import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransferDetailsComponent } from './list/transfer-details.component';
import { TransferDetailsDetailComponent } from './detail/transfer-details-detail.component';
import { TransferDetailsUpdateComponent } from './update/transfer-details-update.component';
import { TransferDetailsDeleteDialogComponent } from './delete/transfer-details-delete-dialog.component';
import { TransferDetailsRoutingModule } from './route/transfer-details-routing.module';

@NgModule({
  imports: [SharedModule, TransferDetailsRoutingModule],
  declarations: [
    TransferDetailsComponent,
    TransferDetailsDetailComponent,
    TransferDetailsUpdateComponent,
    TransferDetailsDeleteDialogComponent,
  ],
  entryComponents: [TransferDetailsDeleteDialogComponent],
})
export class TransferDetailsModule {}
