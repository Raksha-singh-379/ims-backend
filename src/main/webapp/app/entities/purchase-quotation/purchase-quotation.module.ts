import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PurchaseQuotationComponent } from './list/purchase-quotation.component';
import { PurchaseQuotationDetailComponent } from './detail/purchase-quotation-detail.component';
import { PurchaseQuotationUpdateComponent } from './update/purchase-quotation-update.component';
import { PurchaseQuotationDeleteDialogComponent } from './delete/purchase-quotation-delete-dialog.component';
import { PurchaseQuotationRoutingModule } from './route/purchase-quotation-routing.module';

@NgModule({
  imports: [SharedModule, PurchaseQuotationRoutingModule],
  declarations: [
    PurchaseQuotationComponent,
    PurchaseQuotationDetailComponent,
    PurchaseQuotationUpdateComponent,
    PurchaseQuotationDeleteDialogComponent,
  ],
  entryComponents: [PurchaseQuotationDeleteDialogComponent],
})
export class PurchaseQuotationModule {}
