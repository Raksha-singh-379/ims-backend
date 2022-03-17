import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IClientDetails, ClientDetails } from '../client-details.model';
import { ClientDetailsService } from '../service/client-details.service';
import { ClientType } from 'app/entities/enumerations/client-type.model';

@Component({
  selector: 'jhi-client-details-update',
  templateUrl: './client-details-update.component.html',
})
export class ClientDetailsUpdateComponent implements OnInit {
  isSaving = false;
  clientTypeValues = Object.keys(ClientType);

  editForm = this.fb.group({
    id: [],
    clientName: [null, [Validators.required]],
    mobileNo: [],
    email: [],
    billingAddress: [],
    companyName: [],
    companyContactNo: [],
    website: [],
    gstinNumber: [null, []],
    description: [],
    clientType: [],
    isactivated: [],
    freeField1: [],
    lastModified: [],
    lastModifiedBy: [],
  });

  constructor(protected clientDetailsService: ClientDetailsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientDetails }) => {
      this.updateForm(clientDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clientDetails = this.createFromForm();
    if (clientDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.clientDetailsService.update(clientDetails));
    } else {
      this.subscribeToSaveResponse(this.clientDetailsService.create(clientDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientDetails>>): void {
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

  protected updateForm(clientDetails: IClientDetails): void {
    this.editForm.patchValue({
      id: clientDetails.id,
      clientName: clientDetails.clientName,
      mobileNo: clientDetails.mobileNo,
      email: clientDetails.email,
      billingAddress: clientDetails.billingAddress,
      companyName: clientDetails.companyName,
      companyContactNo: clientDetails.companyContactNo,
      website: clientDetails.website,
      gstinNumber: clientDetails.gstinNumber,
      description: clientDetails.description,
      clientType: clientDetails.clientType,
      isactivated: clientDetails.isactivated,
      freeField1: clientDetails.freeField1,
      lastModified: clientDetails.lastModified,
      lastModifiedBy: clientDetails.lastModifiedBy,
    });
  }

  protected createFromForm(): IClientDetails {
    return {
      ...new ClientDetails(),
      id: this.editForm.get(['id'])!.value,
      clientName: this.editForm.get(['clientName'])!.value,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      email: this.editForm.get(['email'])!.value,
      billingAddress: this.editForm.get(['billingAddress'])!.value,
      companyName: this.editForm.get(['companyName'])!.value,
      companyContactNo: this.editForm.get(['companyContactNo'])!.value,
      website: this.editForm.get(['website'])!.value,
      gstinNumber: this.editForm.get(['gstinNumber'])!.value,
      description: this.editForm.get(['description'])!.value,
      clientType: this.editForm.get(['clientType'])!.value,
      isactivated: this.editForm.get(['isactivated'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
