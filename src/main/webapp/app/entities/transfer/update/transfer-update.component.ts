import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransfer, Transfer } from '../transfer.model';
import { TransferService } from '../service/transfer.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IWareHouse } from 'app/entities/ware-house/ware-house.model';
import { WareHouseService } from 'app/entities/ware-house/service/ware-house.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-transfer-update',
  templateUrl: './transfer-update.component.html',
})
export class TransferUpdateComponent implements OnInit {
  isSaving = false;
  statusValues = Object.keys(Status);

  securityUsersSharedCollection: ISecurityUser[] = [];
  wareHousesSharedCollection: IWareHouse[] = [];

  editForm = this.fb.group({
    id: [],
    tranferDate: [],
    comment: [],
    status: [],
    sourceWareHouse: [],
    destinationWareHouse: [],
    freeField1: [],
    lastModified: [],
    lastModifiedBy: [],
    securityUser: [],
    wareHouse: [],
  });

  constructor(
    protected transferService: TransferService,
    protected securityUserService: SecurityUserService,
    protected wareHouseService: WareHouseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transfer }) => {
      if (transfer.id === undefined) {
        const today = dayjs().startOf('day');
        transfer.tranferDate = today;
      }

      this.updateForm(transfer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transfer = this.createFromForm();
    if (transfer.id !== undefined) {
      this.subscribeToSaveResponse(this.transferService.update(transfer));
    } else {
      this.subscribeToSaveResponse(this.transferService.create(transfer));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackWareHouseById(index: number, item: IWareHouse): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransfer>>): void {
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

  protected updateForm(transfer: ITransfer): void {
    this.editForm.patchValue({
      id: transfer.id,
      tranferDate: transfer.tranferDate ? transfer.tranferDate.format(DATE_TIME_FORMAT) : null,
      comment: transfer.comment,
      status: transfer.status,
      sourceWareHouse: transfer.sourceWareHouse,
      destinationWareHouse: transfer.destinationWareHouse,
      freeField1: transfer.freeField1,
      lastModified: transfer.lastModified,
      lastModifiedBy: transfer.lastModifiedBy,
      securityUser: transfer.securityUser,
      wareHouse: transfer.wareHouse,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      transfer.securityUser
    );
    this.wareHousesSharedCollection = this.wareHouseService.addWareHouseToCollectionIfMissing(
      this.wareHousesSharedCollection,
      transfer.wareHouse
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.wareHouseService
      .query()
      .pipe(map((res: HttpResponse<IWareHouse[]>) => res.body ?? []))
      .pipe(
        map((wareHouses: IWareHouse[]) =>
          this.wareHouseService.addWareHouseToCollectionIfMissing(wareHouses, this.editForm.get('wareHouse')!.value)
        )
      )
      .subscribe((wareHouses: IWareHouse[]) => (this.wareHousesSharedCollection = wareHouses));
  }

  protected createFromForm(): ITransfer {
    return {
      ...new Transfer(),
      id: this.editForm.get(['id'])!.value,
      tranferDate: this.editForm.get(['tranferDate'])!.value
        ? dayjs(this.editForm.get(['tranferDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comment: this.editForm.get(['comment'])!.value,
      status: this.editForm.get(['status'])!.value,
      sourceWareHouse: this.editForm.get(['sourceWareHouse'])!.value,
      destinationWareHouse: this.editForm.get(['destinationWareHouse'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      wareHouse: this.editForm.get(['wareHouse'])!.value,
    };
  }
}
