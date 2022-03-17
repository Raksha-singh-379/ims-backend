import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductTransactionComponent } from './list/product-transaction.component';
import { ProductTransactionDetailComponent } from './detail/product-transaction-detail.component';
import { ProductTransactionUpdateComponent } from './update/product-transaction-update.component';
import { ProductTransactionDeleteDialogComponent } from './delete/product-transaction-delete-dialog.component';
import { ProductTransactionRoutingModule } from './route/product-transaction-routing.module';

@NgModule({
  imports: [SharedModule, ProductTransactionRoutingModule],
  declarations: [
    ProductTransactionComponent,
    ProductTransactionDetailComponent,
    ProductTransactionUpdateComponent,
    ProductTransactionDeleteDialogComponent,
  ],
  entryComponents: [ProductTransactionDeleteDialogComponent],
})
export class ProductTransactionModule {}
