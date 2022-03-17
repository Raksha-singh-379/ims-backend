import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransferDetailsApprovals } from '../transfer-details-approvals.model';
import { TransferDetailsApprovalsService } from '../service/transfer-details-approvals.service';

@Component({
  templateUrl: './transfer-details-approvals-delete-dialog.component.html',
})
export class TransferDetailsApprovalsDeleteDialogComponent {
  transferDetailsApprovals?: ITransferDetailsApprovals;

  constructor(protected transferDetailsApprovalsService: TransferDetailsApprovalsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transferDetailsApprovalsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
