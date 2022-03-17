import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IWareHouse, WareHouse } from '../ware-house.model';
import { WareHouseService } from '../service/ware-house.service';

@Component({
  selector: 'jhi-ware-house-update',
  templateUrl: './ware-house-update.component.html',
})
export class WareHouseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    whName: [],
    address: [],
    pincode: [],
    city: [],
    state: [],
    country: [],
    gSTDetails: [],
    managerName: [],
    managerEmail: [],
    managerContact: [],
    contact: [],
    isDeleted: [],
    isActive: [],
    wareHouseId: [],
    lastModified: [],
    lastModifiedBy: [],
  });

  constructor(protected wareHouseService: WareHouseService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wareHouse }) => {
      this.updateForm(wareHouse);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wareHouse = this.createFromForm();
    if (wareHouse.id !== undefined) {
      this.subscribeToSaveResponse(this.wareHouseService.update(wareHouse));
    } else {
      this.subscribeToSaveResponse(this.wareHouseService.create(wareHouse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWareHouse>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(wareHouse: IWareHouse): void {
    this.editForm.patchValue({
      id: wareHouse.id,
      whName: wareHouse.whName,
      address: wareHouse.address,
      pincode: wareHouse.pincode,
      city: wareHouse.city,
      state: wareHouse.state,
      country: wareHouse.country,
      gSTDetails: wareHouse.gSTDetails,
      managerName: wareHouse.managerName,
      managerEmail: wareHouse.managerEmail,
      managerContact: wareHouse.managerContact,
      contact: wareHouse.contact,
      isDeleted: wareHouse.isDeleted,
      isActive: wareHouse.isActive,
      wareHouseId: wareHouse.wareHouseId,
      lastModified: wareHouse.lastModified,
      lastModifiedBy: wareHouse.lastModifiedBy,
    });
  }

  protected createFromForm(): IWareHouse {
    return {
      ...new WareHouse(),
      id: this.editForm.get(['id'])!.value,
      whName: this.editForm.get(['whName'])!.value,
      address: this.editForm.get(['address'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      city: this.editForm.get(['city'])!.value,
      state: this.editForm.get(['state'])!.value,
      country: this.editForm.get(['country'])!.value,
      gSTDetails: this.editForm.get(['gSTDetails'])!.value,
      managerName: this.editForm.get(['managerName'])!.value,
      managerEmail: this.editForm.get(['managerEmail'])!.value,
      managerContact: this.editForm.get(['managerContact'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      wareHouseId: this.editForm.get(['wareHouseId'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
