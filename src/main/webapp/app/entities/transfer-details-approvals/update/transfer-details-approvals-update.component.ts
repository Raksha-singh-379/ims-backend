import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransferDetailsApprovals, TransferDetailsApprovals } from '../transfer-details-approvals.model';
import { TransferDetailsApprovalsService } from '../service/transfer-details-approvals.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

@Component({
  selector: 'jhi-transfer-details-approvals-update',
  templateUrl: './transfer-details-approvals-update.component.html',
})
export class TransferDetailsApprovalsUpdateComponent implements OnInit {
  isSaving = false;

  securityUsersSharedCollection: ISecurityUser[] = [];
  transfersSharedCollection: ITransfer[] = [];

  editForm = this.fb.group({
    id: [],
    approvalDate: [],
    qtyRequested: [],
    qtyApproved: [],
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
    protected transferDetailsApprovalsService: TransferDetailsApprovalsService,
    protected securityUserService: SecurityUserService,
    protected transferService: TransferService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferDetailsApprovals }) => {
      if (transferDetailsApprovals.id === undefined) {
        const today = dayjs().startOf('day');
        transferDetailsApprovals.approvalDate = today;
      }

      this.updateForm(transferDetailsApprovals);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transferDetailsApprovals = this.createFromForm();
    if (transferDetailsApprovals.id !== undefined) {
      this.subscribeToSaveResponse(this.transferDetailsApprovalsService.update(transferDetailsApprovals));
    } else {
      this.subscribeToSaveResponse(this.transferDetailsApprovalsService.create(transferDetailsApprovals));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackTransferById(index: number, item: ITransfer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransferDetailsApprovals>>): void {
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

  protected updateForm(transferDetailsApprovals: ITransferDetailsApprovals): void {
    this.editForm.patchValue({
      id: transferDetailsApprovals.id,
      approvalDate: transferDetailsApprovals.approvalDate ? transferDetailsApprovals.approvalDate.format(DATE_TIME_FORMAT) : null,
      qtyRequested: transferDetailsApprovals.qtyRequested,
      qtyApproved: transferDetailsApprovals.qtyApproved,
      comment: transferDetailsApprovals.comment,
      freeField1: transferDetailsApprovals.freeField1,
      lastModified: transferDetailsApprovals.lastModified,
      lastModifiedBy: transferDetailsApprovals.lastModifiedBy,
      isDeleted: transferDetailsApprovals.isDeleted,
      isActive: transferDetailsApprovals.isActive,
      securityUser: transferDetailsApprovals.securityUser,
      transfer: transferDetailsApprovals.transfer,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      transferDetailsApprovals.securityUser
    );
    this.transfersSharedCollection = this.transferService.addTransferToCollectionIfMissing(
      this.transfersSharedCollection,
      transferDetailsApprovals.transfer
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

  protected createFromForm(): ITransferDetailsApprovals {
    return {
      ...new TransferDetailsApprovals(),
      id: this.editForm.get(['id'])!.value,
      approvalDate: this.editForm.get(['approvalDate'])!.value
        ? dayjs(this.editForm.get(['approvalDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      qtyRequested: this.editForm.get(['qtyRequested'])!.value,
      qtyApproved: this.editForm.get(['qtyApproved'])!.value,
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
