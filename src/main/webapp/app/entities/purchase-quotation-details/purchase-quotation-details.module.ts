import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PurchaseQuotationDetailsComponent } from './list/purchase-quotation-details.component';
import { PurchaseQuotationDetailsDetailComponent } from './detail/purchase-quotation-details-detail.component';
import { PurchaseQuotationDetailsUpdateComponent } from './update/purchase-quotation-details-update.component';
import { PurchaseQuotationDetailsDeleteDialogComponent } from './delete/purchase-quotation-details-delete-dialog.component';
import { PurchaseQuotationDetailsRoutingModule } from './route/purchase-quotation-details-routing.module';

@NgModule({
  imports: [SharedModule, PurchaseQuotationDetailsRoutingModule],
  declarations: [
    PurchaseQuotationDetailsComponent,
    PurchaseQuotationDetailsDetailComponent,
    PurchaseQuotationDetailsUpdateComponent,
    PurchaseQuotationDetailsDeleteDialogComponent,
  ],
  entryComponents: [PurchaseQuotationDetailsDeleteDialogComponent],
})
export class PurchaseQuotationDetailsModule {}
