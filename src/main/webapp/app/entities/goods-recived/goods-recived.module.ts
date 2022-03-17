import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GoodsRecivedComponent } from './list/goods-recived.component';
import { GoodsRecivedDetailComponent } from './detail/goods-recived-detail.component';
import { GoodsRecivedUpdateComponent } from './update/goods-recived-update.component';
import { GoodsRecivedDeleteDialogComponent } from './delete/goods-recived-delete-dialog.component';
import { GoodsRecivedRoutingModule } from './route/goods-recived-routing.module';

@NgModule({
  imports: [SharedModule, GoodsRecivedRoutingModule],
  declarations: [GoodsRecivedComponent, GoodsRecivedDetailComponent, GoodsRecivedUpdateComponent, GoodsRecivedDeleteDialogComponent],
  entryComponents: [GoodsRecivedDeleteDialogComponent],
})
export class GoodsRecivedModule {}
