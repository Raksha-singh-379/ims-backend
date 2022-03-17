import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductInventory } from '../product-inventory.model';
import { ProductInventoryService } from '../service/product-inventory.service';

@Component({
  templateUrl: './product-inventory-delete-dialog.component.html',
})
export class ProductInventoryDeleteDialogComponent {
  productInventory?: IProductInventory;

  constructor(protected productInventoryService: ProductInventoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productInventoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
