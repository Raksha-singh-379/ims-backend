import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransferRecievedComponent } from './list/transfer-recieved.component';
import { TransferRecievedDetailComponent } from './detail/transfer-recieved-detail.component';
import { TransferRecievedUpdateComponent } from './update/transfer-recieved-update.component';
import { TransferRecievedDeleteDialogComponent } from './delete/transfer-recieved-delete-dialog.component';
import { TransferRecievedRoutingModule } from './route/transfer-recieved-routing.module';

@NgModule({
  imports: [SharedModule, TransferRecievedRoutingModule],
  declarations: [
    TransferRecievedComponent,
    TransferRecievedDetailComponent,
    TransferRecievedUpdateComponent,
    TransferRecievedDeleteDialogComponent,
  ],
  entryComponents: [TransferRecievedDeleteDialogComponent],
})
export class TransferRecievedModule {}
