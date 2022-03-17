import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WareHouseComponent } from './list/ware-house.component';
import { WareHouseDetailComponent } from './detail/ware-house-detail.component';
import { WareHouseUpdateComponent } from './update/ware-house-update.component';
import { WareHouseDeleteDialogComponent } from './delete/ware-house-delete-dialog.component';
import { WareHouseRoutingModule } from './route/ware-house-routing.module';

@NgModule({
  imports: [SharedModule, WareHouseRoutingModule],
  declarations: [WareHouseComponent, WareHouseDetailComponent, WareHouseUpdateComponent, WareHouseDeleteDialogComponent],
  entryComponents: [WareHouseDeleteDialogComponent],
})
export class WareHouseModule {}
