import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransferDetails } from '../transfer-details.model';
import { TransferDetailsService } from '../service/transfer-details.service';

@Component({
  templateUrl: './transfer-details-delete-dialog.component.html',
})
export class TransferDetailsDeleteDialogComponent {
  transferDetails?: ITransferDetails;

  constructor(protected transferDetailsService: TransferDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transferDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
