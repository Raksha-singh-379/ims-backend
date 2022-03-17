import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPurchaseQuotationDetails } from '../purchase-quotation-details.model';
import { PurchaseQuotationDetailsService } from '../service/purchase-quotation-details.service';

@Component({
  templateUrl: './purchase-quotation-details-delete-dialog.component.html',
})
export class PurchaseQuotationDetailsDeleteDialogComponent {
  purchaseQuotationDetails?: IPurchaseQuotationDetails;

  constructor(protected purchaseQuotationDetailsService: PurchaseQuotationDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.purchaseQuotationDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
