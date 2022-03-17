import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransferRecieved, TransferRecieved } from '../transfer-recieved.model';
import { TransferRecievedService } from '../service/transfer-recieved.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

@Component({
  selector: 'jhi-transfer-recieved-update',
  templateUrl: './transfer-recieved-update.component.html',
})
export class TransferRecievedUpdateComponent implements OnInit {
  isSaving = false;

  securityUsersSharedCollection: ISecurityUser[] = [];
  transfersSharedCollection: ITransfer[] = [];

  editForm = this.fb.group({
    id: [],
    transferDate: [],
    qtyTransfered: [],
    qtyReceived: [],
    comment: [],
    freeField1: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
    securityUser: [],
    transfer: [],
  });

  constructor(
    protected transferRecievedService: TransferRecievedService,
    protected securityUserService: SecurityUserService,
    protected transferService: TransferService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferRecieved }) => {
      if (transferRecieved.id === undefined) {
        const today = dayjs().startOf('day');
        transferRecieved.transferDate = today;
      }

      this.updateForm(transferRecieved);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transferRecieved = this.createFromForm();
    if (transferRecieved.id !== undefined) {
      this.subscribeToSaveResponse(this.transferRecievedService.update(transferRecieved));
    } else {
      this.subscribeToSaveResponse(this.transferRecievedService.create(transferRecieved));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackTransferById(index: number, item: ITransfer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransferRecieved>>): void {
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

  protected updateForm(transferRecieved: ITransferRecieved): void {
    this.editForm.patchValue({
      id: transferRecieved.id,
      transferDate: transferRecieved.transferDate ? transferRecieved.transferDate.format(DATE_TIME_FORMAT) : null,
      qtyTransfered: transferRecieved.qtyTransfered,
      qtyReceived: transferRecieved.qtyReceived,
      comment: transferRecieved.comment,
      freeField1: transferRecieved.freeField1,
      lastModified: transferRecieved.lastModified,
      lastModifiedBy: transferRecieved.lastModifiedBy,
      isDeleted: transferRecieved.isDeleted,
      isActive: transferRecieved.isActive,
      securityUser: transferRecieved.securityUser,
      transfer: transferRecieved.transfer,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      transferRecieved.securityUser
    );
    this.transfersSharedCollection = this.transferService.addTransferToCollectionIfMissing(
      this.transfersSharedCollection,
      transferRecieved.transfer
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

    this.transferService
      .query()
      .pipe(map((res: HttpResponse<ITransfer[]>) => res.body ?? []))
      .pipe(
        map((transfers: ITransfer[]) =>
          this.transferService.addTransferToCollectionIfMissing(transfers, this.editForm.get('transfer')!.value)
        )
      )
      .subscribe((transfers: ITransfer[]) => (this.transfersSharedCollection = transfers));
  }

  protected createFromForm(): ITransferRecieved {
    return {
      ...new TransferRecieved(),
      id: this.editForm.get(['id'])!.value,
      transferDate: this.editForm.get(['transferDate'])!.value
        ? dayjs(this.editForm.get(['transferDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      qtyTransfered: this.editForm.get(['qtyTransfered'])!.value,
      qtyReceived: this.editForm.get(['qtyReceived'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      transfer: this.editForm.get(['transfer'])!.value,
    };
  }
}
