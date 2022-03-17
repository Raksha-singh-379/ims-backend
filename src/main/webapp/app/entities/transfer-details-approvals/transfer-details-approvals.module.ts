import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransferDetailsApprovalsComponent } from './list/transfer-details-approvals.component';
import { TransferDetailsApprovalsDetailComponent } from './detail/transfer-details-approvals-detail.component';
import { TransferDetailsApprovalsUpdateComponent } from './update/transfer-details-approvals-update.component';
import { TransferDetailsApprovalsDeleteDialogComponent } from './delete/transfer-details-approvals-delete-dialog.component';
import { TransferDetailsApprovalsRoutingModule } from './route/transfer-details-approvals-routing.module';

@NgModule({
  imports: [SharedModule, TransferDetailsApprovalsRoutingModule],
  declarations: [
    TransferDetailsApprovalsComponent,
    TransferDetailsApprovalsDetailComponent,
    TransferDetailsApprovalsUpdateComponent,
    TransferDetailsApprovalsDeleteDialogComponent,
  ],
  entryComponents: [TransferDetailsApprovalsDeleteDialogComponent],
})
export class TransferDetailsApprovalsModule {}
