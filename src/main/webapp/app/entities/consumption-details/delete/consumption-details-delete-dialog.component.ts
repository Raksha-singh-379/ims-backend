import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConsumptionDetails } from '../consumption-details.model';
import { ConsumptionDetailsService } from '../service/consumption-details.service';

@Component({
  templateUrl: './consumption-details-delete-dialog.component.html',
})
export class ConsumptionDetailsDeleteDialogComponent {
  consumptionDetails?: IConsumptionDetails;

  constructor(protected consumptionDetailsService: ConsumptionDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.consumptionDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
